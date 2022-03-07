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
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Base64Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.core.base.exception.BaseException;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.im.api.IImMimcApi;
import tech.ordinaryroad.im.dto.ImMsgDTO;
import tech.ordinaryroad.im.facade.IImMsgFacade;
import tech.ordinaryroad.im.properties.OrImProperties;
import tech.ordinaryroad.im.request.ImMimcMsgCallbackRequest;
import tech.ordinaryroad.im.request.ImMsgReadRequest;
import tech.ordinaryroad.im.request.ImMsgRecallRequest;
import tech.ordinaryroad.im.request.ImMsgSaveRequest;
import tech.ordinaryroad.im.web.constant.MimcConstant;

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
    private final IImMsgFacade tthMsgFacade;

    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public void callback(@Validated @RequestBody ImMimcMsgCallbackRequest request) {
        // TODO 校验 request.getAuthentication() https://admin.mimc.chat.xiaomi.net/docs/09-callback.html
        log.debug("im callback request {}", JSON.toJSONString(request));
        String encodedPayload = new String(Base64Utils.decode(request.getPayload().getBytes(StandardCharsets.UTF_8)));
        ImMsgDTO imMsgDTO = JSON.parseObject(encodedPayload, ImMsgDTO.class);

        if (imMsgDTO == null) {
            throw new BaseException("parse payload failed :" + encodedPayload);
        }

        log.debug("im callback tthMsgSaveRequest {}", JSON.toJSONString(imMsgDTO));
        switch (imMsgDTO.getBizType()) {
            case MimcConstant.BIZ_TYPE_TEXT:
            case MimcConstant.BIZ_TYPE_REPLY:
            case MimcConstant.BIZ_TYPE_PIC_FILE:
            case MimcConstant.BIZ_TYPE_VIDEO_FILE:
            case MimcConstant.BIZ_TYPE_AUDIO_FILE:
            case MimcConstant.BIZ_TYPE_DOUBLE_CLICK_AVATAR:
                // 文字、回复、图片、视频、音频、双击头像直接插入
                ImMsgSaveRequest imMsgSaveRequest = new ImMsgSaveRequest();
                imMsgSaveRequest.setVersion(imMsgDTO.getVersion());
                imMsgSaveRequest.setUuid(imMsgDTO.getUuid());
                imMsgSaveRequest.setMsgId(imMsgDTO.getMsgId());
                imMsgSaveRequest.setPayload(imMsgDTO.getPayload());
                imMsgSaveRequest.setBizType(imMsgDTO.getBizType());
                imMsgSaveRequest.setCreateBy(imMsgDTO.getCreateBy());
                tthMsgFacade.create(imMsgSaveRequest);
                break;
            case MimcConstant.BIZ_TYPE_TEXT_READ:
                // 消息已读，更新数据库
                ImMsgReadRequest imMsgReadRequest = new ImMsgReadRequest();
                imMsgReadRequest.setMsgId(imMsgDTO.getPayload());
                imMsgReadRequest.setUid(request.getFromAccount());
                tthMsgFacade.read(imMsgReadRequest);
                break;
            case MimcConstant.BIZ_TYPE_RECALL:
                // 撤回消息，更新数据库
                ImMsgRecallRequest imMsgRecallRequest = new ImMsgRecallRequest();
                imMsgRecallRequest.setMsgId(imMsgDTO.getPayload());
                imMsgRecallRequest.setUid(request.getFromAccount());
                tthMsgFacade.recall(imMsgRecallRequest);
                break;
            default:
                // 未知类型
                log.warn("Unknown BIZ_TYPE: {}", imMsgDTO.getBizType());
        }
    }

    @Override
    public Result<String> fetchToken() {
        String orNumber = StpUtil.getLoginIdAsString();

        OrImProperties.MimcProperties mimc = imProperties.getMimc();
        String appId = mimc.getAppId();
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
