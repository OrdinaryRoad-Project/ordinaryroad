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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysRolesPermissionsDAO;
import tech.ordinaryroad.upms.entity.SysRolesPermissionsDO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author mjz
 * @date 2021/11/7
 */
@Service
public class SysRolesPermissionsService extends BaseService<SysRolesPermissionsDAO, SysRolesPermissionsDO> {

    public List<SysRolesPermissionsDO> findAllByRoleUuids(List<String> roleUuids) {
        if (CollUtil.isEmpty(roleUuids)) {
            return Collections.emptyList();
        }
        Example example = Example.builder(SysRolesPermissionsDO.class)
                .where(Sqls.custom().andIn("roleUuid", roleUuids))
                .build();
        return super.dao.selectByExample(example);
    }

    public List<SysRolesPermissionsDO> findAllByPermissionUuids(List<String> permissionUuids) {
        if (CollUtil.isEmpty(permissionUuids)) {
            return Collections.emptyList();
        }
        Example example = Example.builder(SysRolesPermissionsDO.class)
                .where(Sqls.custom().andIn("permissionUuid", permissionUuids))
                .build();
        return super.dao.selectByExample(example);
    }

    public Optional<SysRolesPermissionsDO> findByRoleUuidAndPermissionUuid(String roleUuid, String permissionUuid) {
        Sqls sqls = Sqls.custom();
        sqls.andEqualTo("roleUuid", roleUuid);
        sqls.andEqualTo("permissionUuid", permissionUuid);
        Example example = Example.builder(SysRolesPermissionsDO.class).where(sqls).build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<SysRolesPermissionsDO> findAll(SysRolesPermissionsDO sysRolesPermissionsDO) {
        Sqls sqls = Sqls.custom();

        String roleUuid = sysRolesPermissionsDO.getRoleUuid();
        if (StrUtil.isNotBlank(roleUuid)) {
            sqls.andEqualTo("roleUuid", roleUuid);
        }
        String permissionUuid = sysRolesPermissionsDO.getPermissionUuid();
        if (StrUtil.isNotBlank(permissionUuid)) {
            sqls.andEqualTo("permissionUuid", permissionUuid);
        }

        return super.dao.selectByExample(Example.builder(SysRolesPermissionsDO.class).where(sqls).build());
    }

}
