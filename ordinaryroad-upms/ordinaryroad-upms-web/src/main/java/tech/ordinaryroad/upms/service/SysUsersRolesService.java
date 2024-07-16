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
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysUsersRolesDAO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author mjz
 * @date 2021/11/3
 */
@Service
public class SysUsersRolesService extends BaseService<SysUsersRolesDAO, SysUsersRolesDO> {

    public List<SysUsersRolesDO> findAllByUserUuid(String userUuid) {
        if (userUuid == null) {
            return Lists.newArrayList();
        }
        return dao.wrapper()
                .eq(SysUsersRolesDO::getUserUuid, userUuid)
                .list();
    }

    public List<SysUsersRolesDO> findAllByRoleUuid(String roleUuid) {
        if (roleUuid == null) {
            return Lists.newArrayList();
        }
        return dao.wrapper()
                .eq(SysUsersRolesDO::getRoleUuid, roleUuid)
                .list();
    }

    public List<SysUsersRolesDO> findAllByRoleUuids(List<String> roleUuids) {
        if (CollUtil.isEmpty(roleUuids)) {
            return Collections.emptyList();
        }
        return dao.wrapper()
                .in(SysUsersRolesDO::getRoleUuid, roleUuids)
                .list();
    }

    public Optional<SysUsersRolesDO> findByUserUuidAndRoleUuid(String userUuid, String roleUuid) {
        if (userUuid == null || roleUuid == null) {
            return Optional.empty();
        }
        return dao.wrapper()
                .eq(SysUsersRolesDO::getUserUuid, userUuid)
                .eq(SysUsersRolesDO::getRoleUuid, roleUuid)
                .one();
    }

    public List<SysUsersRolesDO> findAll(SysUsersRolesDO sysUsersRolesDO, BaseQueryRequest baseQueryRequest) {
        ExampleWrapper<SysUsersRolesDO, String> wrapper = dao.wrapper();

        String userUuid = sysUsersRolesDO.getUserUuid();
        if (StrUtil.isNotBlank(userUuid)) {
            wrapper.eq(SysUsersRolesDO::getUserUuid, userUuid);
        }
        String roleUuid = sysUsersRolesDO.getRoleUuid();
        if (StrUtil.isNotBlank(roleUuid)) {
            wrapper.eq(SysUsersRolesDO::getRoleUuid, roleUuid);
        }

        return super.findAll(baseQueryRequest, wrapper);
    }

}
