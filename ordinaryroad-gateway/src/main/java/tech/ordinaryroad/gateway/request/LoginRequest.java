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
package tech.ordinaryroad.gateway.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.base.request.IBaseRequest;

import java.io.Serial;

/**
 * 登录请求
 *
 * @author mjz
 * @date 2021/11/26
 */
@Getter
@Setter
@Schema
public class LoginRequest implements IBaseRequest {

    @Serial
    private static final long serialVersionUID = -7838016262579003724L;

    @Schema(title = "记住我")
    private Boolean rememberMe;

    @Schema(title = "验证码UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码UUID不能为空")
    @Size(max = 36, message = "验证码UUID长度不能超过36")
    private String captchaId;

    @Schema(title = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    @Size(max = 10, message = "验证码长度不能超过10")
    private String code;

    @Schema(title = "用户名")
    private String username;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "or帐号")
    private String orNumber;

    @Schema(title = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    private String password;

}
