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
package tech.ordinaryroad.upms.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tech.ordinaryroad.commons.base.dto.BaseDTO;

import java.io.Serial;
import java.util.List;

/**
 * 用户相关信息DTO：User, Role, Permission, RequestPath
 *
 * @author mjz
 * @date 2021/12/9
 */
@Data
@Schema
public class SysUserInfoDTO extends BaseDTO {

    @Serial
    private static final long serialVersionUID = 4600270856295682637L;

    @Schema(title = "用户信息")
    private SysUserDTO user;

    @Schema(title = "拥有的所有角色列表")
    private List<SysRoleDTO> roles;

    @Schema(title = "拥有的所有权限code列表")
    private List<String> permissions;

    @Schema(title = "可以访问的所有请求路径列表")
    private List<String> requestPaths;

}
