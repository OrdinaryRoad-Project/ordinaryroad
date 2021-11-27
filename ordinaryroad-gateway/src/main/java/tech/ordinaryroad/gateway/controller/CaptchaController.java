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
package tech.ordinaryroad.gateway.controller;

import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.core.constant.CacheConstants;
import tech.ordinaryroad.push.api.IEmailApi;
import tech.ordinaryroad.push.request.EmailRegisterCaptchaRequest;

import java.time.Duration;

/**
 * 验证码Controller
 *
 * @author mjz
 * @date 2021/11/27
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("captcha")
public class CaptchaController {

    private final IEmailApi emailApi;

    private final StringRedisTemplate redisTemplate;

    @GetMapping("login")
    public Mono<Result<String>> generateLoginCaptcha(@RequestParam String orNumber) {
        // 自定义纯数字的验证码（随机6位数字，可重复）
        LineCaptcha lineCaptcha = new LineCaptcha(400, 200);

        String code = lineCaptcha.getCode();
        String imageBase64 = lineCaptcha.getImageBase64();

        String key = String.format(CacheConstants.CAPTCHA_LOGIN_KEY, orNumber);
        redisTemplate.opsForValue().set(key, code, Duration.ofMinutes(5));

        return Mono.just(Result.success(imageBase64));
    }

    @GetMapping("register")
    public Mono<Result<String>> generateRegisterCaptcha(@RequestParam String email) {
        String uuid = IdUtil.fastUUID();

        // 自定义纯数字的验证码（随机6位数字，可重复）
        String code = RandomUtil.randomString(RandomUtil.BASE_NUMBER, 6);

        String key = String.format(CacheConstants.CAPTCHA_REGISTER_KEY, uuid);
        redisTemplate.opsForValue().set(key, code, Duration.ofMinutes(5));

        EmailRegisterCaptchaRequest request = EmailRegisterCaptchaRequest.builder()
                .email(email)
                .code(code)
                .build();

        emailApi.sendRegisterCaptcha(request);

        return Mono.just(Result.success(uuid));
    }


}
