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


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.core.base.request.save.BaseSaveRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * 单聊即时消息创建请求
 *
 * @author mjz
 * @date 2022/1/21
 **/
@Getter
@Setter
@ApiModel
public class ImMsgSaveRequest extends BaseSaveRequest {

    private static final long serialVersionUID = -6987650602001810070L;

    @ApiModelProperty(value = "版本号", required = true)
    @NotNull(message = "版本号不能为空")
    private Integer version;

    @ApiModelProperty(value = "消息id", required = true)
    @NotBlank(message = "消息id不能为空")
    private String msgId;

    @ApiModelProperty(value = "消息内容", required = true)
    @NotNull(message = "消息内容不能为空")
    private String payload;

    @ApiModelProperty(value = "消息类型", required = true)
    @NotBlank(message = "消息类型不能为空")
    private String bizType;

    @ApiModelProperty("是否已读")
    private Boolean read;

    @ApiModelProperty("是否已撤回")
    private Boolean recalled;

    @ApiModelProperty(value = "创建者", required = true)
    @NotBlank(message = "创建者不能为空")
    private String createBy;

    @ApiModelProperty(value = "接受者", required = true)
    @NotBlank(message = "接受者不能为空")
    private String toOrNumber;

    @ApiModelProperty(value = "MIMC APP ID", required = true)
    @NotBlank(message = "MIMC APP ID不能为空")
    private String appId;

    @ApiModelProperty(value = "消息实际发送时间", hidden = true)
    private LocalDateTime createdTime;

}
