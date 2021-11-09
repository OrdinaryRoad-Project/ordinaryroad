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
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.dto.SysRequestPathDTO;
import tech.ordinaryroad.upms.entity.SysRequestPathDO;
import tech.ordinaryroad.upms.entity.SysRolesPermissionsDO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;
import tech.ordinaryroad.upms.facade.ISysRequestPathFacade;
import tech.ordinaryroad.upms.mapstruct.SysRequestPathMapStruct;
import tech.ordinaryroad.upms.request.SysRequestPathQueryRequest;
import tech.ordinaryroad.upms.request.SysRequestPathSaveRequest;
import tech.ordinaryroad.upms.service.SysPermissionService;
import tech.ordinaryroad.upms.service.SysRequestPathService;
import tech.ordinaryroad.upms.service.SysRolesPermissionsService;
import tech.ordinaryroad.upms.service.SysUsersRolesService;

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
public class SysRequestPathFacadeImpl implements ISysRequestPathFacade {

    private final SysRequestPathService sysRequestPathService;
    private final SysRequestPathMapStruct objMapStruct;
    private final SysPermissionService sysPermissionService;
    private final SysUsersRolesService sysUsersRolesService;
    private final SysRolesPermissionsService sysRolesPermissionsService;

    @Override
    public Result<SysRequestPathDTO> create(SysRequestPathSaveRequest request) {
        SysRequestPathDO sysRequestPathDO = objMapStruct.transfer(request);

        // 唯一性校验
        String path = request.getPath();
        Optional<SysRequestPathDO> byPath = sysRequestPathService.findByPath(path);
        if (byPath.isPresent()) {
            return Result.fail(StatusCode.PATH_ALREADY_EXIST);
        }
        String pathName = request.getPathName();
        Optional<SysRequestPathDO> byPathName = sysRequestPathService.findByPathName(pathName);
        if (byPathName.isPresent()) {
            return Result.fail(StatusCode.NAME_ALREADY_EXIST);
        }

        return Result.success(objMapStruct.transfer(sysRequestPathService.createSelective(sysRequestPathDO)));
    }

    @Override
    public Result<Boolean> delete(BaseDeleteRequest request) {
        return Result.success(sysRequestPathService.delete(request.getUuid()));
    }

    @Override
    public Result<SysRequestPathDTO> update(SysRequestPathSaveRequest request) {
        String uuid = request.getUuid();
        if (StrUtil.isBlank(uuid)) {
            return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
        }

        SysRequestPathDO byId = sysRequestPathService.findById(uuid);
        if (Objects.isNull(byId)) {
            return Result.fail(StatusCode.DATA_NOT_EXIST);
        }

        String newPath = request.getPath();
        String path = byId.getPath();
        if (!Objects.equals(newPath, path)) {
            if (sysRequestPathService.findByPath(newPath).isPresent()) {
                return Result.fail(StatusCode.PATH_ALREADY_EXIST);
            }
        }

        SysRequestPathDO transfer = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysRequestPathService.updateSelective(transfer)));
    }

    @Override
    public Result<SysRequestPathDTO> findById(SysRequestPathQueryRequest request) {
        SysRequestPathDO sysRequestPathDO = objMapStruct.transfer(request);
        SysRequestPathDO byId = sysRequestPathService.findById(sysRequestPathDO);
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @Override
    public Result<List<SysRequestPathDTO>> findAllByIds(BaseQueryRequest request) {
        List<String> uuids = request.getUuids();
        if (CollUtil.isEmpty(uuids)) {
            return Result.success();
        }
        List<SysRequestPathDO> all = sysRequestPathService.findIds(SysRequestPathDO.class, uuids);
        List<SysRequestPathDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());
        return Result.success(list);
    }

    @Override
    public Result<List<SysRequestPathDTO>> findAll(SysRequestPathQueryRequest request) {
        SysRequestPathDO sysRequestPathDO = objMapStruct.transfer(request);

        List<SysRequestPathDO> all = sysRequestPathService.findAll(sysRequestPathDO);
        List<SysRequestPathDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<PageInfo<SysRequestPathDTO>> list(SysRequestPathQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysRequestPathDO sysRequestPathDO = objMapStruct.transfer(request);
        Page<SysRequestPathDO> all = (Page<SysRequestPathDO>) sysRequestPathService.findAll(sysRequestPathDO);

        PageInfo<SysRequestPathDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }

    @Override
    public Result<List<SysRequestPathDTO>> findAllByPermissionUuids(SysRequestPathQueryRequest request) {
        List<String> permissionUuids = request.getPermissionUuids();
        if (CollUtil.isEmpty(permissionUuids)) {
            return Result.fail(StatusCode.PARAM_IS_BLANK);
        }

        List<SysRequestPathDO> all = sysRequestPathService.findAllByPermissionUuids(permissionUuids);
        List<SysRequestPathDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<List<SysRequestPathDTO>> findAllByUserUuid(SysRequestPathQueryRequest request) {
        String userUuid = request.getUserUuid();
        if (StrUtil.isBlank(userUuid)) {
            return Result.fail(StatusCode.PARAM_IS_BLANK);
        }
        // 根据用户uuid查询所有角色uuid
        List<SysUsersRolesDO> allByUserUuid = sysUsersRolesService.findAllByUserUuid(userUuid);
        // 根据角色uuid查询角色
        List<String> roleUuidList = allByUserUuid.stream().map(SysUsersRolesDO::getRoleUuid).collect(Collectors.toList());
        List<SysRolesPermissionsDO> allByRoleUuids = sysRolesPermissionsService.findAllByRoleUuids(roleUuidList);
        List<String> permissionUuids = allByRoleUuids.stream().map(SysRolesPermissionsDO::getPermissionUuid).collect(Collectors.toList());
        // 根据权限uuid查询所有请求路径
        List<SysRequestPathDO> all = sysRequestPathService.findAllByPermissionUuids(permissionUuids);
        List<SysRequestPathDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());
        return Result.success(list);
    }
}
