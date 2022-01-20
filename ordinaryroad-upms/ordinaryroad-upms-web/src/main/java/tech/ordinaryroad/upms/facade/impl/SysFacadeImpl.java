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

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.dto.SysRoleDTO;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.dto.SysUserInfoDTO;
import tech.ordinaryroad.upms.entity.SysPermissionDO;
import tech.ordinaryroad.upms.entity.SysRequestPathDO;
import tech.ordinaryroad.upms.entity.SysRoleDO;
import tech.ordinaryroad.upms.entity.SysUserDO;
import tech.ordinaryroad.upms.facade.ISysFacade;
import tech.ordinaryroad.upms.mapstruct.SysRoleMapStruct;
import tech.ordinaryroad.upms.mapstruct.SysUserMapStruct;
import tech.ordinaryroad.upms.request.SysUserInfoRequest;
import tech.ordinaryroad.upms.service.SysPermissionService;
import tech.ordinaryroad.upms.service.SysRequestPathService;
import tech.ordinaryroad.upms.service.SysRoleService;
import tech.ordinaryroad.upms.service.SysUserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/12/9
 */
@RequiredArgsConstructor
@Component
public class SysFacadeImpl implements ISysFacade {

    private final SysUserService sysUserService;
    private final SysUserMapStruct sysUserMapStruct;
    private final SysRoleService sysRoleService;
    private final SysRoleMapStruct sysRoleMapStruct;
    private final SysPermissionService sysPermissionService;
    private final SysRequestPathService sysRequestPathService;

    @Override
    public Result<SysUserInfoDTO> userInfo(SysUserInfoRequest request) {
        SysUserInfoDTO userInfoDTO = new SysUserInfoDTO();

        // 优先根据传过来的orNumber查询
        String orNumber = request.getOrNumber();
        if (StrUtil.isBlank(orNumber)) {
            // 优先获取已经登录系统的tokenValue
            String tokenValue = StpUtil.getTokenValue();
            if (StrUtil.isBlank(tokenValue)) {
                tokenValue = request.getSaToken();
            }
            orNumber = (String) StpUtil.getLoginIdByToken(tokenValue);
        }

        // 获取User
        Optional<SysUserDO> optionalSysUserDO = sysUserService.findByOrNumber(orNumber);
        if (!optionalSysUserDO.isPresent()) {
            return Result.fail(StatusCode.USER_ACCOUNT_NOT_EXIST);
        }
        SysUserDO sysUserDO = optionalSysUserDO.get();
        SysUserDTO sysUserDTO = sysUserMapStruct.transfer(sysUserDO);
        userInfoDTO.setUser(sysUserDTO);

        // 获取Roles
        List<SysRoleDO> roleDos = sysRoleService.findAllByUserUuid(sysUserDO.getUuid());
        List<SysRoleDTO> roleDtos = roleDos.stream().map(sysRoleMapStruct::transfer).collect(Collectors.toList());
        userInfoDTO.setRoles(roleDtos);

        // 获取Permission
        List<SysPermissionDO> permissionDos = sysPermissionService.findAllByUserUuid(sysUserDO.getUuid());
        List<String> permissionCodes = permissionDos.stream().map(SysPermissionDO::getPermissionCode).collect(Collectors.toList());
        userInfoDTO.setPermissions(permissionCodes);

        // 获取RequestPath
        List<String> permissionUuids = permissionDos.stream().map(SysPermissionDO::getUuid).collect(Collectors.toList());
        List<SysRequestPathDO> requestPathDos = sysRequestPathService.findAllByPermissionUuids(permissionUuids);
        List<String> requestPaths = requestPathDos.stream().map(SysRequestPathDO::getPath).collect(Collectors.toList());
        userInfoDTO.setRequestPaths(requestPaths);

        return Result.success(userInfoDTO);
    }

}
