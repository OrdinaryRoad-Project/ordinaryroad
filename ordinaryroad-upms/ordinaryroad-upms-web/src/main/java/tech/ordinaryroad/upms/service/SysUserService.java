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
import tech.ordinaryroad.upms.dao.SysUserDAO;
import tech.ordinaryroad.upms.entity.SysUserDO;
import tech.ordinaryroad.upms.entity.SysUsersRolesDO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/10/27
 */
@Service
public class SysUserService extends BaseService<SysUserDAO, SysUserDO> {

    @Autowired
    private SysUsersRolesService sysUsersRolesService;

    @Cacheable(cacheNames = CacheConstants.CACHEABLE_CACHE_NAME_USER_BY_EMAIL, key = "'" + CacheConstants.CACHEABLE_KEY_USER + "' + #email")
    public Optional<SysUserDO> findByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        return dao.wrapper()
                .eq(SysUserDO::getEmail, email)
                .one();
    }

    @Cacheable(cacheNames = CacheConstants.CACHEABLE_CACHE_NAME_USER_BY_USERNAME, key = "'" + CacheConstants.CACHEABLE_KEY_USER + "' + #username")
    public Optional<SysUserDO> findByUsername(String username) {
        if (username == null) {
            return Optional.empty();
        }
        return dao.wrapper()
                .eq(SysUserDO::getUsername, username)
                .one();
    }

    @Cacheable(cacheNames = CacheConstants.CACHEABLE_CACHE_NAME_USER_BY_OR_NUMBER, key = "'" + CacheConstants.CACHEABLE_KEY_USER + "' + #orNumber")
    public Optional<SysUserDO> findByOrNumber(String orNumber) {
        if (orNumber == null) {
            return Optional.empty();
        }
        return dao.wrapper()
                .eq(SysUserDO::getOrNumber, orNumber)
                .one();
    }

    public List<SysUserDO> findAll(SysUserDO sysUserDO, BaseQueryRequest baseQueryRequest) {
        ExampleWrapper<SysUserDO, String> wrapper = dao.wrapper();

        String email = sysUserDO.getEmail();
        if (StrUtil.isNotBlank(email)) {
            wrapper.like(SysUserDO::getEmail, "%" + email + "%");
        }

        String username = sysUserDO.getUsername();
        if (StrUtil.isNotBlank(username)) {
            wrapper.like(SysUserDO::getUsername, "%" + username + "%");
        }

        String orNumber = sysUserDO.getOrNumber();
        if (StrUtil.isNotBlank(orNumber)) {
            wrapper.like(SysUserDO::getOrNumber, "%" + orNumber + "%");
        }

        return super.findAll(baseQueryRequest, wrapper);
    }

    public List<SysUserDO> findAllByRoleUuid(String roleUuid) {
        List<SysUsersRolesDO> allByRoleUuid = sysUsersRolesService.findAllByRoleUuid(roleUuid);
        List<String> userUuids = allByRoleUuid.stream().map(SysUsersRolesDO::getUserUuid).collect(Collectors.toList());
        return this.findIds(userUuids);
    }

    public long selectCount() {
        return dao.wrapper().count();
    }

}
