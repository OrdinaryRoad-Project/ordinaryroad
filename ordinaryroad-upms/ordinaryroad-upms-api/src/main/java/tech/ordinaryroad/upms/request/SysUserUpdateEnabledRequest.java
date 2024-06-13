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
package tech.ordinaryroad.upms.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.core.base.request.BaseRequest;

import java.io.Serial;

/**
 * @author mjz
 * @date 2021/12/7
 */
@Getter
@Setter
@Schema
public class SysUserUpdateEnabledRequest extends BaseRequest {

    @Serial
    private static final long serialVersionUID = 5282199712444997943L;

    @Schema(title = "主键uuid", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "主键uuid不能为空")
    @Size(max = 32, message = "主键uuid长度不能超过32")
    private String uuid;

    @Schema(title = "账号是否可用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "账号是否可用不能为空")
    private Boolean enabled;

    @Schema(title = "停用时间，单位秒，-1永久封禁，当前为永久封禁", accessMode = Schema.AccessMode.READ_ONLY)
    private Long disableTime = -1L;

}
