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
package tech.ordinaryroad.upms.api;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.dto.SysRoleDTO;
import tech.ordinaryroad.upms.request.SysRolePermissionsSaveRequest;
import tech.ordinaryroad.upms.request.SysRoleQueryRequest;
import tech.ordinaryroad.upms.request.SysRoleSaveRequest;
import tech.ordinaryroad.upms.request.SysRoleUsersSaveRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2021/11/4
 */
@Tag(name = "角色API")
@HttpExchange("http://ordinaryroad-upms")
public interface ISysRoleApi {

    @PostExchange(value = "/role/create")
    Result<SysRoleDTO> create(@Validated @RequestBody SysRoleSaveRequest request);

    @PostExchange(value = "/role/delete")
    Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request);

    @PostExchange(value = "/role/update")
    Result<SysRoleDTO> update(@Validated @RequestBody SysRoleSaveRequest request);

    @PostExchange(value = "/role/find/id")
    Result<SysRoleDTO> findById(@RequestBody SysRoleQueryRequest request);

    @PostExchange(value = "/role/find/unique")
    Result<SysRoleDTO> findByUniqueColumn(@RequestBody SysRoleQueryRequest request);

    @PostExchange(value = "/role/find_all/user_uuid")
    Result<List<SysRoleDTO>> findAllByUserUuid(@RequestBody SysRoleQueryRequest request);

    @PostExchange(value = "/role/find_all/ids")
    Result<List<SysRoleDTO>> findAllByIds(@RequestBody BaseQueryRequest request);

    @PostExchange(value = "/role/find_all")
    Result<List<SysRoleDTO>> findAll(@RequestBody SysRoleQueryRequest request);

    @PostExchange(value = "/role/list")
    Result<PageInfo<SysRoleDTO>> list(@RequestBody SysRoleQueryRequest request);

    @PostExchange(value = "/role/update/role_users")
    Result<Boolean> updateRoleUsers(@Validated @RequestBody SysRoleUsersSaveRequest request);

    @PostExchange(value = "/role/update/role_permissions")
    Result<Boolean> updateRolePermissions(@Validated @RequestBody SysRolePermissionsSaveRequest request);

}
