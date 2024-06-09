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
package tech.ordinaryroad.gateway.service;

import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.core.constant.CacheConstants;
import tech.ordinaryroad.gateway.dto.CaptchaLoginDTO;

import jakarta.validation.constraints.NotNull;

/**
 * @author mjz
 * @date 2021/11/27
 */
public interface ICaptchaService {

    void checkValid(@NotNull String key, @NotNull String code);

    /**
     * 生成登录base64图片验证码
     *
     * @return DTO
     */
    Result<CaptchaLoginDTO> generateLoginCaptcha();

    /**
     * 校验登录验证码
     *
     * @param captchaId key的一部分 {@link CacheConstants#CAPTCHA_LOGIN_KEY}
     * @param code      用户输入的验证码
     */
    void checkLoginCaptcha(@NotNull String captchaId, @NotNull String code);

    /**
     * 生成注册纯文字验证码
     *
     * @param email 邮箱，key的一部分 {@link CacheConstants#CAPTCHA_REGISTER_KEY}
     * @return code 生成的验证码
     */
    String generateRegisterCaptcha(@NotNull String email);

    /**
     * 校验注册验证码
     *
     * @param email 邮箱，key的一部分 {@link CacheConstants#CAPTCHA_REGISTER_KEY}
     * @param code  用户输入的验证码
     */
    void checkRegisterCaptcha(@NotNull String email, @NotNull String code);

    /**
     * 发送注册验证码
     *
     * @param email 需要发送的邮箱
     * @param code  生成的注册验证码
     * @return Result
     */
    Result<?> sendRegisterCaptcha(@NotNull String email, @NotNull String code);

    /**
     * 生成重置密码纯文字验证码
     *
     * @param email 邮箱，key的一部分 {@link CacheConstants#CAPTCHA_FORGOT_PASSWORD_KEY}
     * @return code 生成的验证码
     */
    String generateForgotPasswordCaptcha(@NotNull String email);

    /**
     * 校验重置密码验证码
     *
     * @param email 邮箱，key的一部分 {@link CacheConstants#CAPTCHA_FORGOT_PASSWORD_KEY}
     * @param code  用户输入的验证码
     */
    void checkForgotPasswordCaptcha(@NotNull String email, @NotNull String code);

    /**
     * 发送重置密码验证码
     *
     * @param email 需要发送的邮箱
     * @param code  生成的重置密码验证码
     * @return Result
     */
    Result<?> sendForgotPasswordCaptcha(@NotNull String email, @NotNull String code);
}
