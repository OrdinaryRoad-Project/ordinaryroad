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
package tech.ordinaryroad.push.request;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.push.request.base.BasePushRequest;

import javax.validation.constraints.NotBlank;

/**
 * 安卓客户端推送请求（极光推送）
 *
 * @author mjz
 * @date 2022/3/13
 */
@Getter
@Setter
@ApiModel
public class AndroidPushRequest extends BasePushRequest {

    private static final long serialVersionUID = 5021285821421460917L;

    @ApiModelProperty(value = "接收方OR账号", required = true)
    @NotBlank(message = "接收方OR账号不能为空")
    private String toOrNumber;

    @ApiModelProperty(value = "Channel Id")
    private String channel = "default";

    @ApiModelProperty(value = "intent")
    private JSONObject intent = new JSONObject();

    @ApiModelProperty(value = "extras")
    private JSONObject extras = new JSONObject();

}
