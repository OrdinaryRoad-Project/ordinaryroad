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

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysPermissionDAO;
import tech.ordinaryroad.upms.entity.SysPermissionDO;
import tech.ordinaryroad.upms.entity.SysRequestPathDO;
import tech.ordinaryroad.upms.entity.SysRolesPermissionsDO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
    @Autowired
    private SysRequestPathService sysRequestPathService;
    @Autowired
    private SysPermissionService sysPermissionService;

    public Optional<SysPermissionDO> findByPermissionCode(String permissionCode) {
        Example example = Example.builder(SysPermissionDO.class)
                .where(Sqls.custom().andEqualTo("permissionCode", permissionCode))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<SysPermissionDO> findAll(SysPermissionDO sysPermissionDO, BaseQueryRequest baseQueryRequest) {
        WeekendSqls<SysPermissionDO> sqls = WeekendSqls.custom();

        String permissionCode = sysPermissionDO.getPermissionCode();
        if (StrUtil.isNotBlank(permissionCode)) {
            sqls.andLike(SysPermissionDO::getPermissionCode, "%" + permissionCode + "%");
        }
        String description = sysPermissionDO.getDescription();
        if (StrUtil.isNotBlank(description)) {
            sqls.andLike(SysPermissionDO::getDescription, "%" + description + "%");
        }

        Example.Builder exampleBuilder = Example.builder(SysPermissionDO.class).where(sqls);

        return super.findAll(baseQueryRequest, sqls, exampleBuilder);
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

    public Optional<SysPermissionDO> findByRequestPath(String path) {
        Optional<SysRequestPathDO> optional = sysRequestPathService.findByPath(path);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        SysRequestPathDO sysRequestPathDO = optional.get();
        String permissionUuid = sysRequestPathDO.getPermissionUuid();
        return Optional.ofNullable(sysPermissionService.findById(permissionUuid));
    }

    public Optional<SysPermissionDO> findByRequestPathUuid(String requestPathUuid) {
        SysRequestPathDO sysRequestPathDO = sysRequestPathService.findById(requestPathUuid);
        if (Objects.isNull(sysRequestPathDO)) {
            return Optional.empty();
        }
        String permissionUuid = sysRequestPathDO.getPermissionUuid();
        return Optional.ofNullable(sysPermissionService.findById(permissionUuid));
    }

}
