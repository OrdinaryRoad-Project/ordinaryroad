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
package tech.ordinaryroad.upms.web.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.dto.SysUsersRolesDTO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;
import tech.ordinaryroad.upms.mapstruct.SysUsersRolesMapStruct;
import tech.ordinaryroad.upms.request.SysUsersRolesQueryRequest;
import tech.ordinaryroad.upms.request.SysUsersRolesSaveRequest;
import tech.ordinaryroad.upms.service.SysRoleService;
import tech.ordinaryroad.upms.service.SysUserService;
import tech.ordinaryroad.upms.service.SysUsersRolesService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/11/4
 */
@RequiredArgsConstructor
@RestController
public class SysUsersRolesController {

    private final SysUsersRolesService sysUsersRolesService;
    private final SysUsersRolesMapStruct objMapStruct;
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;

    @PostMapping(value = "/users_roles/create")
    public Result<SysUsersRolesDTO> create(@Validated @RequestBody SysUsersRolesSaveRequest request) {

        String userUuid = request.getUserUuid();
        if (Objects.isNull(sysUserService.findById(userUuid))) {
            return Result.fail(StatusCode.USER_NOT_EXIST);
        }
        String roleUuid = request.getRoleUuid();
        if (Objects.isNull(sysRoleService.findById(roleUuid))) {
            return Result.fail(StatusCode.ROLE_NOT_EXIST);
        }

        // 唯一性校验
        Optional<SysUsersRolesDO> byUserUuidAndRoleUuid = sysUsersRolesService.findByUserUuidAndRoleUuid(userUuid, roleUuid);
        if (byUserUuidAndRoleUuid.isPresent()) {
            return Result.fail(StatusCode.DATA_ALREADY_EXIST);
        }

        SysUsersRolesDO sysRoleDO = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysUsersRolesService.createSelective(sysRoleDO)));
    }

    @PostMapping(value = "/users_roles/delete")
    public Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request) {
        return Result.success(sysUsersRolesService.delete(request.getUuid()));
    }

    @PostMapping(value = "/users_roles/find/id")
    public Result<SysUsersRolesDTO> findById(@RequestBody SysUsersRolesQueryRequest request) {
        SysUsersRolesDO sysRoleDO = objMapStruct.transfer(request);
        SysUsersRolesDO byId = sysUsersRolesService.findById(sysRoleDO);
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @PostMapping(value = "/users_roles/find_all")
    public Result<List<SysUsersRolesDTO>> findAll(@RequestBody SysUsersRolesQueryRequest request) {
        SysUsersRolesDO sysRoleDO = objMapStruct.transfer(request);

        List<SysUsersRolesDO> all = sysUsersRolesService.findAll(sysRoleDO, request);
        List<SysUsersRolesDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @PostMapping(value = "/users_roles/list")
    public Result<PageInfo<SysUsersRolesDTO>> list(@RequestBody SysUsersRolesQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysUsersRolesDO sysRoleDO = objMapStruct.transfer(request);
        Page<SysUsersRolesDO> all = (Page<SysUsersRolesDO>) sysUsersRolesService.findAll(sysRoleDO, request);

        PageInfo<SysUsersRolesDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }
}
