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
package tech.ordinaryroad.auth.server.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tech.ordinaryroad.commons.base.dto.BaseDTO;

import java.io.Serial;

/**
 * 用户相关信息DTO：User
 *
 * @author mjz
 * @date 2021/12/1
 */
@Data
@Schema
public class OAuth2UserInfoDTO extends BaseDTO {

    @Serial
    private static final long serialVersionUID = 2264708854063291438L;

    @Schema(title = "or帐号")
    private String orNumber;

    @Schema(title = "头像地址")
    private String avatar;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "用户名")
    private String username;

}
