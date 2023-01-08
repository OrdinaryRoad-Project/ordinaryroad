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
package tech.ordinaryroad.commons.core.constant;

import javax.validation.constraints.NotNull;

/**
 * 缓存常量
 * <p>
 * key命名规则（https://www.cnblogs.com/joshua317/p/11995197.html）：
 * 业务模块名:业务逻辑含义:其他:value类型
 *
 * @author mjz
 * @date 2021/11/27
 */
public interface CacheConstants {

    /**
     * 默认验证码的key
     */
    String CAPTCHA_DEFAULT_KEY = "gateway:captcha.default:%s:string";

    /**
     * 登录验证码的key，captchaId
     */
    String CAPTCHA_LOGIN_KEY = "gateway:captcha.login:%s:string";

    /**
     * 注册验证码的key，captchaId
     */
    String CAPTCHA_REGISTER_KEY = "gateway:captcha.register:%s:string";

    /**
     * 重置密码验证码的key，captchaId
     */
    String CAPTCHA_FORGOT_PASSWORD_KEY = "gateway:captcha.forgot.password:%s:string";

    // region Cache注解缓存相关
    /**
     * 根据用户Id查询用户拥有的角色
     */
    String CACHEABLE_CACHE_NAME_ROLES_BY_USER_UUID = "SysRoleService.findAllByUserUuid";
    /**
     * 根据用户邮箱查询用户
     */
    String CACHEABLE_CACHE_NAME_USER_BY_EMAIL = "SysUserService.findByEmail";
    /**
     * 根据用户用户名查询用户
     */
    String CACHEABLE_CACHE_NAME_USER_BY_USERNAME = "SysUserService.findByUsername";
    /**
     * 根据用户OR账号查询用户
     */
    String CACHEABLE_CACHE_NAME_USER_BY_OR_NUMBER = "SysUserService.findByOrNumber";
    /**
     * 根据用户Id查询用户拥有的权限
     */
    String CACHEABLE_CACHE_NAME_PERMISSIONS_BY_USER_UUID = "SysPermissionService.findAllByUserUuid";
    /**
     * 根据角色Id查询角色拥有的权限
     */
    String CACHEABLE_CACHE_NAME_PERMISSIONS_BY_ROLE_UUID = "SysPermissionService.findAllByRoleUuid";
    /**
     * 根据请求路径查询权限
     */
    String CACHEABLE_CACHE_NAME_PERMISSION_BY_REQUEST_PATH = "SysPermissionService.findByRequestPath";
    /**
     * 根据请求路径Id查询权限
     */
    String CACHEABLE_CACHE_NAME_PERMISSION_BY_REQUEST_PATH_UUID = "SysPermissionService.findByRequestPathUuid";
    /**
     * 用户拥有的角色
     */
    String CACHEABLE_KEY_USER_ROLES = "upms:user.roles:";
    /**
     * 用户
     */
    String CACHEABLE_KEY_USER = "upms:user:";
    /**
     * 用户拥有的权限
     */
    String CACHEABLE_KEY_USER_PERMISSIONS = "upms:user.permissions:";
    /**
     * 角色拥有的权限
     */
    String CACHEABLE_KEY_ROLE_PERMISSIONS = "upms:role.permissions:";
    /**
     * 请求路径权限
     */
    String CACHEABLE_KEY_REQUEST_PATH_PERMISSION = "upms:request.path.permission:";
    // endregion

    /**
     * 生成登录验证码缓存key
     *
     * @param captchaId uuid
     * @return KEY
     * @see CacheConstants#CAPTCHA_LOGIN_KEY
     */
    static String generateLoginCaptchaKey(@NotNull String captchaId) {
        return String.format(CacheConstants.CAPTCHA_LOGIN_KEY, captchaId);
    }

    /**
     * 生成注册验证码缓存key
     *
     * @param email 邮箱
     * @return KEY
     * @see CacheConstants#CAPTCHA_REGISTER_KEY
     */
    static String generateRegisterCaptchaKey(@NotNull String email) {
        return String.format(CacheConstants.CAPTCHA_REGISTER_KEY, email);
    }

    /**
     * 生成重置密码验证码缓存key
     *
     * @param email 邮箱
     * @return KEY
     * @see CacheConstants#CAPTCHA_FORGOT_PASSWORD_KEY
     */
    static String generateForgotPasswordCaptchaKey(String email) {
        return String.format(CacheConstants.CAPTCHA_FORGOT_PASSWORD_KEY, email);
    }
}
