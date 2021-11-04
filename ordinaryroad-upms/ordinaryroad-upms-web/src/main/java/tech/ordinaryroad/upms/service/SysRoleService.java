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

import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.lang.Argument;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysRoleDAO;
import tech.ordinaryroad.upms.entity.SysRoleDO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;
import java.util.Optional;

/**
 * @author mjz
 * @date 2021/11/3
 */
@Service
public class SysRoleService extends BaseService<SysRoleDAO, SysRoleDO> {

    public Optional<SysRoleDO> findByRoleName(String roleName) {
        Example example = Example.builder(SysRoleDO.class)
                .where(Sqls.custom().andEqualTo("roleName", roleName))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public Optional<SysRoleDO> findByRoleCode(String roleCode) {
        Example example = Example.builder(SysRoleDO.class)
                .where(Sqls.custom().andEqualTo("roleCode", roleCode))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<SysRoleDO> findAll(SysRoleDO sysRoleDO) {
        Sqls sqls = Sqls.custom();

        String roleName = sysRoleDO.getRoleName();
        if (Argument.isNotBlank(roleName)) {
            sqls.andLike("roleName", "%" + roleName + "%");
        }
        String roleCode = sysRoleDO.getRoleCode();
        if (Argument.isNotBlank(roleCode)) {
            sqls.andLike("roleCode", "%" + roleCode + "%");
        }

        return super.dao.selectByExample(Example.builder(SysRoleDO.class).where(sqls).build());
    }

}