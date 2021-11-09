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

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.constants.ServiceNameCons;
import tech.ordinaryroad.upms.dto.SysPermissionDTO;
import tech.ordinaryroad.upms.request.SysPermissionQueryRequest;
import tech.ordinaryroad.upms.request.SysPermissionSaveRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2021/11/8
 */
@Api(value = "权限API")
@FeignClient(name = ServiceNameCons.SERVICE_NAME, contextId = "iSysPermissionApi")
public interface ISysPermissionApi {

    @PostMapping(value = "/permission/create")
    Result<SysPermissionDTO> create(@Validated @RequestBody SysPermissionSaveRequest request);

    @PostMapping(value = "/permission/delete")
    Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request);

    @PostMapping(value = "/permission/update")
    Result<SysPermissionDTO> update(@Validated @RequestBody SysPermissionSaveRequest request);

    @PostMapping(value = "/permission/find_id")
    Result<SysPermissionDTO> findById(@RequestBody SysPermissionQueryRequest request);

    @PostMapping(value = "/permission/find_all/user_uuid")
    Result<List<SysPermissionDTO>> findAllByUserUuid(@RequestBody SysPermissionQueryRequest request);

    @PostMapping(value = "/permission/find_all/role_uuid")
    Result<List<SysPermissionDTO>> findAllByRoleUuid(@RequestBody SysPermissionQueryRequest request);

    @PostMapping(value = "/permission/find_all/ids")
    Result<List<SysPermissionDTO>> findAllByIds(@RequestBody BaseQueryRequest request);

    @PostMapping(value = "/permission/find_all")
    Result<List<SysPermissionDTO>> findAll(@RequestBody SysPermissionQueryRequest request);

    @PostMapping(value = "/permission/list")
    Result<PageInfo<SysPermissionDTO>> list(@RequestBody SysPermissionQueryRequest request);

}
