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

package tech.ordinaryroad.im.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.im.constant.MimcConstant;
import tech.ordinaryroad.im.dao.ImMsgDAO;
import tech.ordinaryroad.im.dto.ImMsgDTO;
import tech.ordinaryroad.im.entity.ImMsgDO;
import tech.ordinaryroad.im.request.ImMimcMsgCallbackRequest;
import tech.ordinaryroad.upms.api.ISysDictItemApi;
import tech.ordinaryroad.upms.api.ISysUserApi;
import tech.ordinaryroad.upms.dto.SysDictItemDTO;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.SysDictItemQueryRequest;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 单聊即时消息服务类
 *
 * @author mjz
 * @date 2022/1/21
 */
@RequiredArgsConstructor
@Service
public class ImMsgService extends BaseService<ImMsgDAO, ImMsgDO> {

    private final ISysDictItemApi sysDictItemApi;
    private final ISysUserApi sysUserApi;

    public Optional<ImMsgDO> findByMsgId(String msgId) {
        if (StrUtil.isBlank(msgId)) {
            return Optional.empty();
        }

        WeekendSqls<ImMsgDO> sqls = WeekendSqls.custom();

        Example example = Example.builder(ImMsgDO.class)
                .where(sqls.andEqualTo(ImMsgDO::getMsgId, msgId))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<ImMsgDO> history(ImMsgDO imMsgDO, String orNumber, String chatPartnerOrNumber) {
        imMsgDO.setCreateBy(orNumber);
        imMsgDO.setToOrNumber(chatPartnerOrNumber);
        return super.dao.history(imMsgDO);
    }

    public List<ImMsgDO> findAll(ImMsgDO imMsgDO, BaseQueryRequest baseQueryRequest) {
        WeekendSqls<ImMsgDO> sqls = WeekendSqls.custom();
        String payload = imMsgDO.getPayload();
        if (StrUtil.isNotBlank(payload)) {
            sqls.andLike(ImMsgDO::getPayload, "%" + payload + "%");
        }
        String bizType = imMsgDO.getBizType();
        if (StrUtil.isNotBlank(bizType)) {
            sqls.andEqualTo(ImMsgDO::getBizType, bizType);
        }
        Boolean read = imMsgDO.getRead();
        if (Objects.nonNull(read)) {
            sqls.andEqualTo(ImMsgDO::getRead, read);
        }
        String createBy = imMsgDO.getCreateBy();
        if (StrUtil.isNotBlank(createBy)) {
            sqls.andLike(ImMsgDO::getCreateBy, "%" + createBy + "%");
        }
        String toOrNumber = imMsgDO.getToOrNumber();
        if (StrUtil.isNotBlank(toOrNumber)) {
            sqls.andLike(ImMsgDO::getToOrNumber, "%" + toOrNumber + "%");
        }

        Example.Builder exampleBuilder = Example.builder(ImMsgDO.class).where(sqls);

        return super.findAll(baseQueryRequest, sqls, exampleBuilder);
    }

    /**
     * 获取字符串化后的消息
     *
     * @param request  ImMimcMsgCallbackRequest
     * @param imMsgDTO ImMsgDTO
     * @param bizType  bizType
     * @param payload  payload
     * @return 消息内容
     */
    public String getStringedPayload(ImMimcMsgCallbackRequest request, ImMsgDTO imMsgDTO, String bizType, String payload) {
        String stringedPayload;
        switch (bizType) {
            case MimcConstant.BIZ_TYPE_REPLY:
                MimcConstant.ReplyMsgPayload replyMsgPayload = JSON.parseObject(payload, MimcConstant.ReplyMsgPayload.class);
                stringedPayload = getStringedPayload(request, imMsgDTO, replyMsgPayload.getBizType(), replyMsgPayload.getPayload());
                break;
            case MimcConstant.BIZ_TYPE_TEXT:
                stringedPayload = payload;
                break;
            case MimcConstant.BIZ_TYPE_PIC_FILE:
                stringedPayload = "[图片]";
                break;
            case MimcConstant.BIZ_TYPE_VIDEO_FILE:
                stringedPayload = "[视频]";
                break;
            case MimcConstant.BIZ_TYPE_AUDIO_FILE:
                stringedPayload = "[音频]";
                break;
            case MimcConstant.BIZ_TYPE_DOUBLE_CLICK_AVATAR:
                final MimcConstant.DoubleClickAvatarPayload doubleClickAvatarPayload = JSON.parseObject(payload, MimcConstant.DoubleClickAvatarPayload.class);

                final SysDictItemQueryRequest sysDictItemQueryRequest = new SysDictItemQueryRequest();
                sysDictItemQueryRequest.setDictCode(MimcConstant.DICT_CODE_IM_MSG_DOUBLE_CLICK_AVATAR_PAYLOAD);
                sysDictItemQueryRequest.setValue(doubleClickAvatarPayload.getActionDictItem());
                final Result<SysDictItemDTO> dictItemResult = sysDictItemApi.detail(sysDictItemQueryRequest);
                SysDictItemDTO actionDictItem = null;
                if (dictItemResult.getSuccess()) {
                    actionDictItem = dictItemResult.getData();
                }

                // 查询消息发送方用户信息
                SysUserDTO fromUser = new SysUserDTO();
                Result<SysUserDTO> sysUserResult;
                final SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
                sysUserQueryRequest.setOrNumber(request.getFromAccount());
                sysUserResult = sysUserApi.findByUniqueColumn(sysUserQueryRequest);
                if (sysUserResult.getSuccess()) {
                    fromUser = sysUserResult.getData();
                }

                final List<String> strings = MimcConstant.DoubleClickAvatarPayload.getStrings(fromUser, doubleClickAvatarPayload, actionDictItem);

                stringedPayload = strings.get(0) + strings.get(1) + strings.get(2) + strings.get(3);
                break;
            case MimcConstant.BIZ_TYPE_TEXT_READ:
                stringedPayload = "[查看了未读消息]";
                break;
            case MimcConstant.BIZ_TYPE_RECALL:
                stringedPayload = "[撤回了一条消息]";
                break;
            default:
                stringedPayload = "[暂不支持的消息类型]";
        }
        return stringedPayload;
    }

}
