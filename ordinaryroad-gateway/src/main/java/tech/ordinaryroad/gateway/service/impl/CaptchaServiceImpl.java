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
package tech.ordinaryroad.gateway.service.impl;

import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.exception.CaptchaException;
import tech.ordinaryroad.commons.core.constant.CacheConstants;
import tech.ordinaryroad.commons.core.service.RedisService;
import tech.ordinaryroad.gateway.service.ICaptchaService;

/**
 * @author mjz
 * @date 2021/11/27
 */
@RequiredArgsConstructor
@Service
public class CaptchaServiceImpl implements ICaptchaService {

    private final RedisService redisService;

    @Override
    public void checkValid(String key, String code) {
        if (StrUtil.isBlank(code)) {
            throw new CaptchaException("验证码不能为空");
        }

        String captcha = redisService.getCacheObject(key);
        redisService.deleteObjectIfExists(key);

        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException("验证码错误");
        }
    }

    @Override
    public String generateLoginCaptcha(String orNumber) {
        // 自定义纯数字的验证码（随机6位数字，可重复）
        LineCaptcha lineCaptcha = new LineCaptcha(400, 200);

        String code = lineCaptcha.getCode();
        String imageBase64 = lineCaptcha.getImageBase64();

        redisService.setCacheObject(String.format(CacheConstants.CAPTCHA_LOGIN_KEY, orNumber), code);
        return imageBase64;
    }

    @Override
    public void checkLoginCaptcha(String orNumber, String code) {
        this.checkValid(String.format(CacheConstants.CAPTCHA_LOGIN_KEY, orNumber), code);
    }

    @Override
    public String generateRegisterCaptcha(String email) {
        // 自定义纯数字的验证码（随机6位数字，可重复）
        String code = RandomUtil.randomString(RandomUtil.BASE_NUMBER, 6);

        String key = String.format(CacheConstants.CAPTCHA_REGISTER_KEY, email);
        redisService.setCacheObject(key, code);
        return code;
    }

    @Override
    public void checkRegisterCaptcha(String uuid, String code) {
        this.checkValid(String.format(CacheConstants.CAPTCHA_REGISTER_KEY, uuid), code);
    }

}
