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
package tech.ordinaryroad.ioe.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.core.base.request.save.BaseSaveRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author mjz
 * @date 2022/3/25
 */
@Getter
@Setter
@ApiModel
public class IoEUserSaveRequest extends BaseSaveRequest {

    private static final long serialVersionUID = -3813744880497511919L;

    @ApiModelProperty(value = "OR账号", required = true)
    @NotBlank(message = "OR账号不能为空")
    @Size(max = 11, message = "OR账号长度不能超过11")
    private String orNumber;

    @ApiModelProperty(value = "openid", required = true)
    @NotBlank(message = "openid不能为空")
    @Size(max = 50, message = "openid长度不能超过50")
    private String openid;

    @ApiModelProperty(value = "ThingsBoard平台CustomerId", required = true)
    @NotBlank(message = "ThingsBoard平台CustomerId不能为空")
    @Size(min = 36, max = 36, message = "ThingsBoard平台CustomerId长度必须为36")
    private String customerId;

    @ApiModelProperty(value = "ThingsBoard平台Customer下的UserId", required = true)
    @NotBlank(message = "ThingsBoard平台Customer下的UserId不能为空")
    @Size(min = 36, max = 36, message = "ThingsBoard平台Customer下的UserId长度必须为36")
    private String userId;

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    @Size(max = 20, message = "用户名长度不能超过20")
    private String username;

}
