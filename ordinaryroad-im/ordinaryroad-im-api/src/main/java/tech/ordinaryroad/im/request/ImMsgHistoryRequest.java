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
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;

import javax.validation.constraints.NotBlank;


/**
 * 单聊即时消息历史记录查询请求
 *
 * @author mjz
 * @date 2022/3/11
 **/
@Getter
@Setter
@ApiModel
public class ImMsgHistoryRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 4762277376776113589L;

    @ApiModelProperty("版本号")
    private Integer version;

    @ApiModelProperty("消息内容")
    private String payload;

    @ApiModelProperty("消息类型")
    private String bizType;

    @ApiModelProperty("是否已读")
    private Boolean read;

    @ApiModelProperty("是否已撤回")
    private Boolean recalled;

    @ApiModelProperty(value = "单聊对象账号", required = true)
    @NotBlank(message = "单聊对象账号不能为空")
    private String toOrNumber;

    @ApiModelProperty(value = "MIMC APP ID", required = true)
    @NotBlank(message = "MIMC APP ID不能为空")
    private String appId;

}
