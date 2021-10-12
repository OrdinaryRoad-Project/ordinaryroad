package top.ordinaryroad.system.service.impl;

import org.springframework.stereotype.Service;
import top.ordinaryroad.commons.core.biz.service.BaseLogicDeleteService;
import top.ordinaryroad.system.dao.SysUserDao;
import top.ordinaryroad.system.entity.SysUser;
import top.ordinaryroad.system.service.ISysUserService;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author mjz
 * @date 2021/9/6
 */
@Service
public class SysUserServiceImpl extends BaseLogicDeleteService<SysUser, SysUserDao>
        implements ISysUserService {

    @Override
    public SysUser update(@NotNull SysUser entity) {
        // update方法不允许更新密码、用户名
        entity.setPassword(null);
        entity.setUsername(null);
        return super.update(entity);
    }

    @Override
    public SysUser updateLoginInfo(@NotNull String username) {
        SysUser byUsername = findByUsername(username);
        assert byUsername != null;
        SysUser sysUser = new SysUser();
        sysUser.setId(byUsername.getId());
        sysUser.setLastLoginTime(LocalDateTime.now());
        return this.update(sysUser);
    }

    @Override
    public SysUser findByUsername(@NotNull String username) {
        return dao.findByUsername(username).orElse(null);
    }

    @Override
    public SysUser findByEmail(@NotNull String email) {
        return dao.findByEmail(email).orElse(null);
    }

}
