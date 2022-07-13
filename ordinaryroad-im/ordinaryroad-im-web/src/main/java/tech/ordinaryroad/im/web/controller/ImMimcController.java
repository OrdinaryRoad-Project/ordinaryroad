/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.ordinaryroad.im.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Base64Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.base.exception.BaseException;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.im.api.IImMimcApi;
import tech.ordinaryroad.im.constant.MimcConstant;
import tech.ordinaryroad.im.dto.ImMsgDTO;
import tech.ordinaryroad.im.facade.IImMsgFacade;
import tech.ordinaryroad.im.mapstruct.ImMsgMapStruct;
import tech.ordinaryroad.im.properties.OrImProperties;
import tech.ordinaryroad.im.request.ImMimcMsgCallbackRequest;
import tech.ordinaryroad.im.request.ImMsgReadRequest;
import tech.ordinaryroad.im.request.ImMsgRecallRequest;
import tech.ordinaryroad.im.request.ImMsgSaveRequest;
import tech.ordinaryroad.im.service.ImMsgService;
import tech.ordinaryroad.push.api.IPushApi;
import tech.ordinaryroad.push.request.AndroidPushRequest;

import javax.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;

/**
 * 即时通信 小米即时消息云Controller
 *
 * @author mjz
 * @date 2022/2/13
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class ImMimcController implements IImMimcApi {

    private final OrImProperties imProperties;
    private final IImMsgFacade imMsgFacade;
    private final ImMsgMapStruct objMapStruct;
    private final IPushApi pushApi;
    private final ImMsgService imMsgService;

    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public void callback(@Validated @RequestBody ImMimcMsgCallbackRequest request) {
        // 认证校验
        String authentication = request.getAuthentication();
        String appId = request.getFromAppId();
        String appSecret = imProperties.getProperties(appId).getAppSecret();
        Long timestamp = Long.valueOf(request.getTimestamp());
        if (!checkMimcAuthentication(authentication, appSecret, appId, timestamp)) {
            throw new BaseException(StatusCode.PARAM_NOT_VALID);
        }
        log.debug("im callback request {}", JSON.toJSONString(request));

        String encodedPayload = new String(Base64Utils.decode(request.getPayload().getBytes(StandardCharsets.UTF_8)));
        ImMsgDTO imMsgDTO = JSON.parseObject(encodedPayload, ImMsgDTO.class);
        if (imMsgDTO == null) {
            throw new BaseException("parse payload failed :" + encodedPayload);
        }
        log.debug("im callback imMsgSaveRequest {}", JSON.toJSONString(imMsgDTO));

        final String msgType = request.getMsgType();
        switch (msgType) {
            // 单聊离线消息
            case MimcConstant.CALLBACK_MSG_TYPE_OFFLINE_MSG:
                // 发送消息推送
                handleOfflineMsg(request, imMsgDTO);
                break;
            case MimcConstant.CALLBACK_MSG_TYPE_NORMAL_MSG:
                // 消息持久化
                handleOnlineMsg(request, imMsgDTO);
                break;
            case MimcConstant.CALLBACK_MSG_TYPE_OFFLINE_TOPIC_MSG:
            case MimcConstant.CALLBACK_MSG_TYPE_NORMAL_TOPIC_MSG:
            case MimcConstant.CALLBACK_MSG_TYPE_UCTOPIC_MSG:
                // 暂未支持
                break;
            default:
        }
    }

    /**
     * 处理离线消息，只发送消息推送，不需要持久化
     *
     * @param request  ImMimcMsgCallbackRequest
     * @param imMsgDTO ImMsgDTO
     */
    private void handleOfflineMsg(ImMimcMsgCallbackRequest request, ImMsgDTO imMsgDTO) {
        final JSONObject extras = new JSONObject();
        final JSONObject intent = new JSONObject();

        final AndroidPushRequest androidPushRequest = new AndroidPushRequest();
        androidPushRequest.setTitle("新消息提醒");
        androidPushRequest.setContent(imMsgService.getStringedPayload(request, imMsgDTO, imMsgDTO.getBizType(), imMsgDTO.getPayload()));
        androidPushRequest.setExtras(extras);
        androidPushRequest.setIntent(intent);
        androidPushRequest.setChannel("msg");
        androidPushRequest.setToOrNumber(request.getToAccount());
        // 根据APP_ID查询PackageName
        androidPushRequest.setPackageName(imProperties.getProperties(request.getToAppId()).getPackageName());
        pushApi.android(androidPushRequest);
    }

    /**
     * 处理在线消息，将消息持久化
     *
     * @param request  ImMimcMsgCallbackRequest
     * @param imMsgDTO ImMsgDTO
     */
    private void handleOnlineMsg(ImMimcMsgCallbackRequest request, ImMsgDTO imMsgDTO) {
        switch (imMsgDTO.getBizType()) {
            case MimcConstant.BIZ_TYPE_TEXT:
            case MimcConstant.BIZ_TYPE_REPLY:
            case MimcConstant.BIZ_TYPE_PIC_FILE:
            case MimcConstant.BIZ_TYPE_VIDEO_FILE:
            case MimcConstant.BIZ_TYPE_AUDIO_FILE:
            case MimcConstant.BIZ_TYPE_DOUBLE_CLICK_AVATAR:
                // 文字、回复、图片、视频、音频、双击头像直接插入
                ImMsgSaveRequest imMsgSaveRequest = objMapStruct.transfer(imMsgDTO);
                imMsgFacade.create(imMsgSaveRequest);
                break;
            case MimcConstant.BIZ_TYPE_TEXT_READ:
                // 消息已读，更新数据库
                ImMsgReadRequest imMsgReadRequest = new ImMsgReadRequest();
                imMsgReadRequest.setMsgId(imMsgDTO.getPayload());
                imMsgReadRequest.setUid(request.getFromAccount());
                imMsgFacade.read(imMsgReadRequest);
                break;
            case MimcConstant.BIZ_TYPE_RECALL:
                // 撤回消息，更新数据库
                ImMsgRecallRequest imMsgRecallRequest = new ImMsgRecallRequest();
                imMsgRecallRequest.setMsgId(imMsgDTO.getPayload());
                imMsgRecallRequest.setUid(request.getFromAccount());
                imMsgFacade.recall(imMsgRecallRequest);
                break;
            default:
                // 未知类型
                log.warn("Unknown BIZ_TYPE: {}", imMsgDTO.getBizType());
        }
    }

    /**
     * MIMC认证校验
     *
     * @param authentication https://admin.mimc.chat.xiaomi.net/docs/09-callback.html
     * @param appSecret      AppSecret
     * @param appId          AppId
     * @param timestamp      回调中的时间戳
     * @return Boolean 校验是否通过
     */
    private static boolean checkMimcAuthentication(String authentication, String appSecret, String appId, Long timestamp) {
        boolean valid = Boolean.FALSE;
        try {
            byte[] bytes = StrUtil.bytes(authentication, StandardCharsets.UTF_8);
            byte[] decode = Base64Decoder.decode(bytes);

            byte[] appKeyMd5 = SecureUtil.md5().digest(appSecret);
            final int groupSize = 16;
            byte[] decrypt = new byte[decode.length];

            AES aes = new AES(Mode.ECB, Padding.ZeroPadding, appKeyMd5);
            for (int i = 0; i < decode.length; i += groupSize) {
                byte[] decodeTemp = new byte[groupSize];
                System.arraycopy(decode, i, decodeTemp, 0, groupSize);
                byte[] decryptTemp = aes.decrypt(decodeTemp);
                System.arraycopy(decryptTemp, 0, decrypt, i, decryptTemp.length);
            }

            byte[] lengthBytes = new byte[4];
            System.arraycopy(decrypt, 0, lengthBytes, 0, lengthBytes.length);

            int length = ByteUtil.bytesToInt(lengthBytes);

            byte[] realDecrypt = new byte[length];
            System.arraycopy(decrypt, 4, realDecrypt, 0, length);
            String decryptString = StrUtil.str(realDecrypt, StandardCharsets.UTF_8);
            String[] split = decryptString.split("_");
            Long decryptTimestamp = Long.valueOf(split[0]);
            String decryptAppId = split[1];
            // 小于一分钟
            valid = Math.abs(decryptTimestamp - timestamp) <= 60 * 1000 && decryptAppId.equals(appId);
        } catch (Exception e) {
            log.error("checkMimcAuthentication error", e);
        }
        return valid;
    }

    @Override
    public Result<String> fetchToken(@PathVariable @Validated @NotBlank String appId) {
        String orNumber = StpUtil.getLoginIdAsString();
        OrImProperties.MimcProperties mimc = imProperties.getProperties(appId);
        String appKey = mimc.getAppKey();
        String appSecret = mimc.getAppSecret();
        String regionKey = mimc.getRegionKey();
        String tokenUrl = mimc.getTokenUrl();

        String json = "{\"appId\":" + appId + ",\"appKey\":\"" + appKey + "\",\"appSecret\":\"" +
                appSecret + "\",\"appAccount\":\"" + orNumber + "\",\"regionKey\":\"" + regionKey + "\"}";

        String responseString;
        try {
            responseString = HttpUtil.post(tokenUrl, json);
        } catch (Exception e) {
            throw new BaseException(e.getMessage(), e.getCause());
        }
        JSONObject jsonObject = JSON.parseObject(responseString);
        Integer code = jsonObject.getInteger("code");
        if (code != HttpStatus.OK.value()) {
            return Result.fail();
        }
        return Result.success(responseString);
    }

}
