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
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysUsersRolesDAO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
import tk.mybatis.mapper.weekend.WeekendSqls;

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
        Example example = Example.builder(SysUsersRolesDO.class)
                .where(Sqls.custom().andEqualTo("userUuid", userUuid))
                .build();
        return super.dao.selectByExample(example);
    }

    public List<SysUsersRolesDO> findAllByRoleUuid(String roleUuid) {
        Example example = Example.builder(SysUsersRolesDO.class)
                .where(Sqls.custom().andEqualTo("roleUuid", roleUuid))
                .build();
        return super.dao.selectByExample(example);
    }

    public List<SysUsersRolesDO> findAllByRoleUuids(List<String> roleUuids) {
        if (CollUtil.isEmpty(roleUuids)) {
            return Collections.emptyList();
        }
        Example example = Example.builder(SysUsersRolesDO.class)
                .where(Sqls.custom().andIn("roleUuid", roleUuids))
                .build();
        return super.dao.selectByExample(example);
    }

    public Optional<SysUsersRolesDO> findByUserUuidAndRoleUuid(String userUuid, String roleUuid) {
        Sqls sqls = Sqls.custom();
        sqls.andEqualTo("userUuid", userUuid);
        sqls.andEqualTo("roleUuid", roleUuid);
        Example example = Example.builder(SysUsersRolesDO.class).where(sqls).build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<SysUsersRolesDO> findAll(SysUsersRolesDO sysUsersRolesDO, BaseQueryRequest baseQueryRequest) {
        WeekendSqls<SysUsersRolesDO> sqls = WeekendSqls.custom();

        String userUuid = sysUsersRolesDO.getUserUuid();
        if (StrUtil.isNotBlank(userUuid)) {
            sqls.andEqualTo(SysUsersRolesDO::getUserUuid, userUuid);
        }
        String roleUuid = sysUsersRolesDO.getRoleUuid();
        if (StrUtil.isNotBlank(roleUuid)) {
            sqls.andEqualTo(SysUsersRolesDO::getRoleUuid, roleUuid);
        }

        Example.Builder exampleBuilder = Example.builder(SysUsersRolesDO.class).where(sqls);

        return super.findAll(baseQueryRequest, sqls, exampleBuilder);
    }

}
