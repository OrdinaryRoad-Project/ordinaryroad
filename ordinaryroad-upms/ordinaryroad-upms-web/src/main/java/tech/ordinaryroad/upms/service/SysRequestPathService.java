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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysRequestPathDAO;
import tech.ordinaryroad.upms.entity.SysRequestPathDO;
import tech.ordinaryroad.upms.entity.SysRolesPermissionsDO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/11/7
 */
@Service
public class SysRequestPathService extends BaseService<SysRequestPathDAO, SysRequestPathDO> {

    @Autowired
    private SysUsersRolesService sysUsersRolesService;
    @Autowired
    private SysRolesPermissionsService sysRolesPermissionsService;

    public Optional<SysRequestPathDO> findByPath(String path) {
        return dao.wrapper()
                .eq(SysRequestPathDO::getPath, path)
                .one();
    }

    public Optional<SysRequestPathDO> findByPathName(String pathName) {
        return dao.wrapper()
                .eq(SysRequestPathDO::getPathName, pathName)
                .one();
    }

    public List<SysRequestPathDO> findAll(SysRequestPathDO sysRequestPathDO, BaseQueryRequest baseQueryRequest) {
        ExampleWrapper<SysRequestPathDO, String> wrapper = dao.wrapper();

        String path = sysRequestPathDO.getPath();
        if (StrUtil.isNotBlank(path)) {
            wrapper.like(SysRequestPathDO::getPath, "%" + path + "%");
        }
        String pathName = sysRequestPathDO.getPathName();
        if (StrUtil.isNotBlank(pathName)) {
            wrapper.like(SysRequestPathDO::getPathName, "%" + pathName + "%");
        }

        return super.findAll(baseQueryRequest, wrapper);
    }

    public List<SysRequestPathDO> findAllByPermissionUuids(List<String> permissionUuids) {
        if (CollUtil.isEmpty(permissionUuids)) {
            return Collections.emptyList();
        }
        return dao.wrapper()
                .in(SysRequestPathDO::getPermissionUuid, permissionUuids)
                .list();
    }

    public List<SysRequestPathDO> findAllByUserUuid(String userUuid) {
        // 根据用户uuid查询所有角色uuid
        List<SysUsersRolesDO> allByUserUuid = sysUsersRolesService.findAllByUserUuid(userUuid);
        // 根据角色uuid查询角色
        List<String> roleUuidList = allByUserUuid.stream().map(SysUsersRolesDO::getRoleUuid).collect(Collectors.toList());
        List<SysRolesPermissionsDO> allByRoleUuids = sysRolesPermissionsService.findAllByRoleUuids(roleUuidList);
        List<String> permissionUuids = allByRoleUuids.stream().map(SysRolesPermissionsDO::getPermissionUuid).collect(Collectors.toList());
        // 根据权限uuid查询所有请求路径
        return this.findAllByPermissionUuids(permissionUuids);
    }
}
