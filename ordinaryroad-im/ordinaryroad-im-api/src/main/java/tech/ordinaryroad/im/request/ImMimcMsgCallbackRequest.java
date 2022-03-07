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
package tech.ordinaryroad.im.request;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <a href="https://admin.mimc.chat.xiaomi.net/docs/09-callback.html">消息回调</a>
 *
 * @author mjz
 * @date 2022/2/13
 */
@Getter
@Setter
@ApiModel
public class ImMimcMsgCallbackRequest {

    private static final long serialVersionUID = -6141643144862972594L;

    /**
     * OFFLINE_MSG：                 单聊离线消息
     * NORMAL_MSG：                  单聊即时消息
     * NORMAL_TOPIC_MSG：            群聊即时消息
     * OFFLINE_TOPIC_MSG：           群聊离线消息
     * UCTOPIC_MSG：                 超级大群即时消息
     */
    private String msgType;
    private String fromAppId;
    private String fromAccount;
    private String fromResource;
    private String toAppId;
    private String toAccount;
    /**
     * 群组内所有用户（群聊即时消息）
     * 群组内未收到消息的用户（群聊离线消息）
     */
    private List<String> toAccounts;
    private String toResource;
    /**
     * 消息体Base64编码后数据
     */
    private String payload;
    private String timestamp;
    /**
     * 客户端生成的消息ID
     */
    private String packetId;
    /**
     * 服务器为消息分配的递增ID，可用于去重/排序
     */
    private String sequence;
    /**
     * 可用于表示消息类型扩展字段
     */
    private String bizType;
    /**
     * value是用AES算法对timestamp_appId加密的,加密key基于appsecurity的md5值;
     */
    private String authentication;
    /**
     * 所有离线设备（单聊离线消息）
     */
    private List<String> offlineResources;
    /**
     * 所有离线设备（群聊离线消息）
     * <pre>
     *     {
     *      "toAccount":$toAccount1,
     *      "offlineResources":[$Resource1,$Resource2,...,$ResourceN], //账户($toAccount1)的离线设备
     *    }
     * <pre/>
     */
    private List<JSONObject> offlineAccounts;
}