package top.ordinaryroad.system.service;

import org.springframework.lang.Nullable;
import top.ordinaryroad.commons.core.base.service.IBaseLogicDeleteService;
import top.ordinaryroad.system.entity.SysUser;

import javax.validation.constraints.NotNull;

/**
 * SysUserService
 *
 * @author mjz
 * @date 2021/9/6
 */
public interface ISysUserService extends IBaseLogicDeleteService<SysUser> {

    /**
     * 根据用户名更新登录信息
     *
     * @param username 用户名
     * @return 实体
     */
    @Nullable
    SysUser updateLoginInfo(@NotNull String username);

    /**
     * 根据用户名找到用户
     *
     * @param username 用户名
     * @return 实体
     */
    @Nullable
    SysUser findByUsername(@NotNull String username);

    /**
     * 根据邮箱找到用户
     *
     * @param email 邮箱
     * @return 实体
     */
    @Nullable
    SysUser findByEmail(@NotNull String email);

}
