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

package tech.ordinaryroad.im.web.constant;

/**
 * @author mjz
 * @date 2022/2/13
 */
public class MimcConstant {

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

}
