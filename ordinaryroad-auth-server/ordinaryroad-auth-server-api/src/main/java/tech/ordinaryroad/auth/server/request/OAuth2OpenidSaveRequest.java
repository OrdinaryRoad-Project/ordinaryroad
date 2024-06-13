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
package tech.ordinaryroad.auth.server.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.core.base.request.save.BaseSaveRequest;

import java.io.Serial;

/**
 * @author mjz
 * @date 2022/1/14
 */
@Getter
@Setter
@Schema
public class OAuth2OpenidSaveRequest extends BaseSaveRequest {

    @Serial
    private static final long serialVersionUID = 883534349322415339L;

    @Schema(title = "OR帐号")
    @NotBlank(message = "OR帐号不能为空")
    @Size(max = 11, message = "or帐号长度不能超过11")
    private String orNumber;

    @Schema(title = "clientId")
    @NotBlank(message = "clientId不能为空")
    @Size(max = 20, message = "clientId长度不能超过20")
    private String clientId;

    @Schema(title = "openid")
    @NotBlank(message = "openid不能为空")
    @Size(max = 50, message = "openid长度不能超过50")
    private String openid;

}
