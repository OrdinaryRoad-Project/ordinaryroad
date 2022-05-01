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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysRequestPathDAO;
import tech.ordinaryroad.upms.entity.SysRequestPathDO;
import tech.ordinaryroad.upms.entity.SysRolesPermissionsDO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
import tk.mybatis.mapper.weekend.WeekendSqls;

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
        Example example = Example.builder(SysRequestPathDO.class)
                .where(Sqls.custom().andEqualTo("path", path))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public Optional<SysRequestPathDO> findByPathName(String pathName) {
        Example example = Example.builder(SysRequestPathDO.class)
                .where(Sqls.custom().andEqualTo("pathName", pathName))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<SysRequestPathDO> findAll(SysRequestPathDO sysRequestPathDO, BaseQueryRequest baseQueryRequest) {
        WeekendSqls<SysRequestPathDO> sqls = WeekendSqls.custom();

        String path = sysRequestPathDO.getPath();
        if (StrUtil.isNotBlank(path)) {
            sqls.andLike(SysRequestPathDO::getPath, "%" + path + "%");
        }
        String pathName = sysRequestPathDO.getPathName();
        if (StrUtil.isNotBlank(pathName)) {
            sqls.andLike(SysRequestPathDO::getPathName, "%" + pathName + "%");
        }

        Example.Builder exampleBuilder = Example.builder(SysRequestPathDO.class).where(sqls);

        return super.findAll(baseQueryRequest, sqls, exampleBuilder);
    }

    public List<SysRequestPathDO> findAllByPermissionUuids(List<String> permissionUuids) {
        if (CollUtil.isEmpty(permissionUuids)) {
            return Collections.emptyList();
        }
        Example example = Example.builder(SysRequestPathDO.class)
                .where(Sqls.custom().andIn("permissionUuid", permissionUuids))
                .build();
        return super.dao.selectByExample(example);
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
