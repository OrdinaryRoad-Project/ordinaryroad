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

package tech.ordinaryroad.im.constant;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import lombok.Data;
import tech.ordinaryroad.upms.dto.SysDictItemDTO;
import tech.ordinaryroad.upms.dto.SysUserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author mjz
 * @date 2022/2/13
 */
public class MimcConstant {

    /**
     * 双击头像的字典code
     */
    public static final String DICT_CODE_IM_MSG_DOUBLE_CLICK_AVATAR_PAYLOAD = "im-msg-double_click_avatar_payload";

    /**
     * 单聊即时消息
     */
    public static final String CALLBACK_MSG_TYPE_NORMAL_MSG = "NORMAL_MSG";
    /**
     * 群聊即时消息
     */
    public static final String CALLBACK_MSG_TYPE_NORMAL_TOPIC_MSG = "NORMAL_TOPIC_MSG";
    /**
     * 超级大群即时消息
     */
    public static final String CALLBACK_MSG_TYPE_UCTOPIC_MSG = "UCTOPIC_MSG";
    /**
     * 单聊离线消息
     */
    public static final String CALLBACK_MSG_TYPE_OFFLINE_MSG = "OFFLINE_MSG";
    /**
     * 群聊离线消息
     */
    public static final String CALLBACK_MSG_TYPE_OFFLINE_TOPIC_MSG = "OFFLINE_TOPIC_MSG";

    /**
     * 文本消息
     */
    public static final String BIZ_TYPE_TEXT = "TEXT";
    public static final String BIZ_TYPE_REPLY = "REPLY";
    public static final String BIZ_TYPE_PIC_FILE = "PIC_FILE";
    public static final String BIZ_TYPE_VIDEO_FILE = "VIDEO_FILE";
    public static final String BIZ_TYPE_AUDIO_FILE = "AUDIO_FILE";
    public static final String BIZ_TYPE_DOUBLE_CLICK_AVATAR = "DOUBLE_CLICK_AVATAR";
    public static final String BIZ_TYPE_CHAT_STATE_CHANGE = "CHAT_STATE_CHANGE";
    /**
     * 已读消息
     */
    public static final String BIZ_TYPE_TEXT_READ = "TEXT_READ";
    /**
     * 撤回消息，payload为撤回消息msgId
     */
    public static final String BIZ_TYPE_RECALL = "RECALL";
    public static final String BIZ_TYPE_PING = "PING";
    public static final String BIZ_TYPE_PONG = "PONG";


    /**
     * 用于回复消息的payload
     */
    @Data
    public static class ReplyMsgPayload {
        private String preMsgId;
        private String preMsgPayload;
        private String preMsgBizType;
        private String payload;
        private String bizType;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

    /**
     * 双击头像的payload
     */
    @Data
    public static class DoubleClickAvatarPayload {
        /**
         * 被拍者OR账号
         */
        private String orNumber;
        /**
         * 动作字典项
         */
        private String actionDictItem;
        /**
         * 备注
         */
        private String remark;

        /**
         * @param fromUser 发送方
         * @param payload  DoubleClickAvatarPayload
         * @return [fromUser.username] [动作label] [我/自己] [备注]
         */
        public static List<String> getStrings(SysUserDTO fromUser, DoubleClickAvatarPayload payload, SysDictItemDTO actionDictItem) {
            List<String> strings = new ArrayList<>();

            String string1 = fromUser.getUsername();
            strings.add(string1);

            String string2;
            if (Objects.isNull(actionDictItem)) {
                string2 = "null";
            } else {
                string2 = actionDictItem.getLabel();
            }
            strings.add(string2);

            String string3;
            if (!payload.getOrNumber().equals(fromUser.getOrNumber())) {
                string3 = "我";
            } else {
                string3 = "自己";
            }
            strings.add(string3);

            String string4;
            final String remark = payload.getRemark();
            if (StrUtil.isNotBlank(remark)) {
                string4 = "的" + remark;
            } else {
                string4 = "";
            }
            strings.add(string4);

            return strings;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

}
