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
import io.mybatis.mapper.example.ExampleWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.constant.CacheConstants;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysRoleDAO;
import tech.ordinaryroad.upms.entity.SysRoleDO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/11/3
 */
@Service
public class SysRoleService extends BaseService<SysRoleDAO, SysRoleDO> {

    @Autowired
    private SysUsersRolesService sysUsersRolesService;

    public Optional<SysRoleDO> findByRoleName(String roleName) {
        return dao.wrapper()
                .eq(SysRoleDO::getRoleName, roleName)
                .one();
    }

    public Optional<SysRoleDO> findByRoleCode(String roleCode) {
        return dao.wrapper()
                .eq(SysRoleDO::getRoleCode, roleCode)
                .one();
    }

    public List<SysRoleDO> findAll(SysRoleDO sysRoleDO, BaseQueryRequest baseQueryRequest) {
        ExampleWrapper<SysRoleDO, String> wrapper = dao.wrapper();

        String roleName = sysRoleDO.getRoleName();
        if (StrUtil.isNotBlank(roleName)) {
            wrapper.like(SysRoleDO::getRoleName, "%" + roleName + "%");
        }
        String roleCode = sysRoleDO.getRoleCode();
        if (StrUtil.isNotBlank(roleCode)) {
            wrapper.like(SysRoleDO::getRoleCode, "%" + roleCode + "%");
        }

        return super.findAll(baseQueryRequest, wrapper);
    }

    @Cacheable(cacheNames = CacheConstants.CACHEABLE_CACHE_NAME_ROLES_BY_USER_UUID, key = "'" + CacheConstants.CACHEABLE_KEY_USER_ROLES + "' + #userUuid")
    public List<SysRoleDO> findAllByUserUuid(String userUuid) {
        // 根据用户uuid查询所有角色uuid
        List<SysUsersRolesDO> allByUserUuid = sysUsersRolesService.findAllByUserUuid(userUuid);
        // 根据角色uuid查询角色
        List<String> roleUuidList = allByUserUuid.stream().map(SysUsersRolesDO::getRoleUuid).collect(Collectors.toList());
        return this.findIds(roleUuidList);
    }

}
