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
import io.mybatis.mapper.example.ExampleWrapper;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysRolesPermissionsDAO;
import tech.ordinaryroad.upms.entity.SysRolesPermissionsDO;

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
        return dao.wrapper()
                .in(SysRolesPermissionsDO::getRoleUuid, roleUuids)
                .list();
    }

    public List<SysRolesPermissionsDO> findAllByPermissionUuids(List<String> permissionUuids) {
        if (CollUtil.isEmpty(permissionUuids)) {
            return Collections.emptyList();
        }
        return dao.wrapper()
                .in(SysRolesPermissionsDO::getPermissionUuid, permissionUuids)
                .list();
    }

    public Optional<SysRolesPermissionsDO> findByRoleUuidAndPermissionUuid(String roleUuid, String permissionUuid) {
        return dao.wrapper()
                .eq(SysRolesPermissionsDO::getRoleUuid, roleUuid)
                .eq(SysRolesPermissionsDO::getPermissionUuid, permissionUuid)
                .one();
    }

    public List<SysRolesPermissionsDO> findAll(SysRolesPermissionsDO sysRolesPermissionsDO, BaseQueryRequest baseQueryRequest) {
        ExampleWrapper<SysRolesPermissionsDO, String> wrapper = dao.wrapper();

        String roleUuid = sysRolesPermissionsDO.getRoleUuid();
        if (StrUtil.isNotBlank(roleUuid)) {
            wrapper.eq(SysRolesPermissionsDO::getRoleUuid, roleUuid);
        }
        String permissionUuid = sysRolesPermissionsDO.getPermissionUuid();
        if (StrUtil.isNotBlank(permissionUuid)) {
            wrapper.eq(SysRolesPermissionsDO::getPermissionUuid, permissionUuid);
        }

        return super.findAll(baseQueryRequest, wrapper);
    }

}
