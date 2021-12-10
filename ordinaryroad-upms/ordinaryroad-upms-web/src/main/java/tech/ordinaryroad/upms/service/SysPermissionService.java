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
package tech.ordinaryroad.upms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.lang.Argument;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysPermissionDAO;
import tech.ordinaryroad.upms.entity.SysPermissionDO;
import tech.ordinaryroad.upms.entity.SysRolesPermissionsDO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/11/7
 */
@Service
public class SysPermissionService extends BaseService<SysPermissionDAO, SysPermissionDO> {

    @Autowired
    private SysUsersRolesService sysUsersRolesService;
    @Autowired
    private SysRolesPermissionsService sysRolesPermissionsService;

    public Optional<SysPermissionDO> findByPermissionCode(String permissionCode) {
        Example example = Example.builder(SysPermissionDO.class)
                .where(Sqls.custom().andEqualTo("permissionCode", permissionCode))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<SysPermissionDO> findAll(SysPermissionDO sysPermissionDO) {
        Sqls sqls = Sqls.custom();

        String permissionCode = sysPermissionDO.getPermissionCode();
        if (Argument.isNotBlank(permissionCode)) {
            sqls.andLike("permissionCode", "%" + permissionCode + "%");
        }
        String description = sysPermissionDO.getDescription();
        if (Argument.isNotBlank(description)) {
            sqls.andLike("description", "%" + description + "%");
        }

        return super.dao.selectByExample(Example.builder(SysPermissionDO.class).where(sqls).build());
    }

    public List<SysPermissionDO> findAllByUserUuid(String userUuid) {
        // 根据用户uuid查询所有角色uuid
        List<SysUsersRolesDO> allByUserUuid = sysUsersRolesService.findAllByUserUuid(userUuid);
        // 根据角色uuid查询角色
        List<String> roleUuidList = allByUserUuid.stream().map(SysUsersRolesDO::getRoleUuid).collect(Collectors.toList());
        List<SysRolesPermissionsDO> allByRoleUuids = sysRolesPermissionsService.findAllByRoleUuids(roleUuidList);
        List<String> permissionUuids = allByRoleUuids.stream().map(SysRolesPermissionsDO::getPermissionUuid).collect(Collectors.toList());
        // 根据权限uuid查询所有权限
        return this.findIds(SysPermissionDO.class, permissionUuids);
    }

    public List<SysPermissionDO> findAllByRoleUuid(String roleUuid) {
        // 根据角色uuid查询所有权限
        List<SysRolesPermissionsDO> allByRoleUuids = sysRolesPermissionsService.findAllByRoleUuids(Collections.singletonList(roleUuid));
        List<String> permissionUuids = allByRoleUuids.stream().map(SysRolesPermissionsDO::getPermissionUuid).collect(Collectors.toList());
        // 根据权限uuid查询所有权限
        return this.findIds(SysPermissionDO.class, permissionUuids);
    }

}
