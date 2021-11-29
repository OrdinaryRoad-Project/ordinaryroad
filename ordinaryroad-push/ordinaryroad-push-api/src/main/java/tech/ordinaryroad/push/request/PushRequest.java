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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.core.base.request.IBaseRequest;
import tech.ordinaryroad.push.constant.MessageType;

import javax.validation.constraints.NotBlank;

/**
 * @author mjz
 * @date 2021/11/27
 */
@Getter
@Setter
@ApiModel
public class PushRequest implements IBaseRequest {

    private static final long serialVersionUID = 6761331810536219450L;

    @ApiModelProperty("接收方OR帐号")
    @NotBlank(message = "接收方OR帐号不能为空")
    private String receiverOrNumber;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    @ApiModelProperty("消息类型")
    @NotBlank(message = "消息类型不能为空")
    private MessageType messageType;

}
