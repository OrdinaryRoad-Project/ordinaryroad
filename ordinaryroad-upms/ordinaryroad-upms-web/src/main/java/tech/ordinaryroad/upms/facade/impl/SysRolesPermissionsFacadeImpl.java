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
package tech.ordinaryroad.upms.facade.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.dto.SysRolesPermissionsDTO;
import tech.ordinaryroad.upms.entity.SysRolesPermissionsDO;
import tech.ordinaryroad.upms.facade.ISysRolesPermissionsFacade;
import tech.ordinaryroad.upms.mapstruct.SysRolesPermissionsMapStruct;
import tech.ordinaryroad.upms.request.SysRolesPermissionsQueryRequest;
import tech.ordinaryroad.upms.request.SysRolesPermissionsSaveRequest;
import tech.ordinaryroad.upms.service.SysPermissionService;
import tech.ordinaryroad.upms.service.SysRoleService;
import tech.ordinaryroad.upms.service.SysRolesPermissionsService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/11/8
 */
@RequiredArgsConstructor
@Component
public class SysRolesPermissionsFacadeImpl implements ISysRolesPermissionsFacade {

    private final SysRolesPermissionsService sysSysRolesPermissionsService;
    private final SysRolesPermissionsMapStruct objMapStruct;
    private final SysRoleService sysRoleService;
    private final SysPermissionService sysPermissionService;

    @Override
    public Result<SysRolesPermissionsDTO> create(SysRolesPermissionsSaveRequest request) {
        String roleUuid = request.getRoleUuid();
        if (Objects.isNull(sysRoleService.findById(roleUuid))) {
            return Result.fail(StatusCode.ROLE_NOT_EXIST);
        }
        String permissionUuid = request.getPermissionUuid();
        if (Objects.isNull(sysPermissionService.findById(permissionUuid))) {
            return Result.fail(StatusCode.PERMISSION_NOT_EXIST);
        }

        // 唯一性校验
        Optional<SysRolesPermissionsDO> byRoleUuidAndPermissionUuid = sysSysRolesPermissionsService.findByRoleUuidAndPermissionUuid(roleUuid, permissionUuid);
        if (byRoleUuidAndPermissionUuid.isPresent()) {
            return Result.fail(StatusCode.DATA_ALREADY_EXIST);
        }

        SysRolesPermissionsDO sysRolesPermissionsDO = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysSysRolesPermissionsService.createSelective(sysRolesPermissionsDO)));
    }

    @Override
    public Result<Boolean> delete(BaseDeleteRequest request) {
        return Result.success(sysSysRolesPermissionsService.delete(request.getUuid()));
    }

    @Override
    public Result<SysRolesPermissionsDTO> update(SysRolesPermissionsSaveRequest request) {
        // 不支持修改
        return Result.fail();
    }

    @Override
    public Result<SysRolesPermissionsDTO> findById(SysRolesPermissionsQueryRequest request) {
        SysRolesPermissionsDO sysSysRolesPermissionsDO = objMapStruct.transfer(request);
        SysRolesPermissionsDO byId = sysSysRolesPermissionsService.findById(sysSysRolesPermissionsDO);
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @Override
    public Result<List<SysRolesPermissionsDTO>> findAllByIds(BaseQueryRequest request) {
        List<String> uuids = request.getUuids();
        if (CollUtil.isEmpty(uuids)) {
            return Result.success(Collections.emptyList());
        }
        List<SysRolesPermissionsDO> all = sysSysRolesPermissionsService.findIds(SysRolesPermissionsDO.class, uuids);
        List<SysRolesPermissionsDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());
        return Result.success(list);
    }

    @Override
    public Result<List<SysRolesPermissionsDTO>> findAll(SysRolesPermissionsQueryRequest request) {
        SysRolesPermissionsDO sysSysRolesPermissionsDO = objMapStruct.transfer(request);

        List<SysRolesPermissionsDO> all = sysSysRolesPermissionsService.findAll(sysSysRolesPermissionsDO, request);
        List<SysRolesPermissionsDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<PageInfo<SysRolesPermissionsDTO>> list(SysRolesPermissionsQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysRolesPermissionsDO sysSysRolesPermissionsDO = objMapStruct.transfer(request);
        Page<SysRolesPermissionsDO> all = (Page<SysRolesPermissionsDO>) sysSysRolesPermissionsService.findAll(sysSysRolesPermissionsDO, request);

        PageInfo<SysRolesPermissionsDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }
}
