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

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.gateway.dto.CaptchaLoginDTO;
import tech.ordinaryroad.gateway.service.ICaptchaService;
import tech.ordinaryroad.push.api.IEmailApi;
import tech.ordinaryroad.push.request.EmailRegisterCaptchaRequest;
import tech.ordinaryroad.upms.api.ISysUserApi;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;

import java.util.concurrent.*;

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

    private final ISysUserApi sysUserApi;
    private final IEmailApi emailApi;
    private final ICaptchaService captchaService;
    private final ExecutorService executorService = new ThreadPoolExecutor(10, 20, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new ThreadFactory() {
        @Override
        public Thread newThread(@NotNull Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("API调用线程");
            return thread;
        }
    });

    @GetMapping("login")
    public Mono<Result<CaptchaLoginDTO>> generateLoginCaptcha() {
        return Mono.just(captchaService.generateLoginCaptcha());
    }

    @GetMapping("register")
    public Mono<Result<?>> generateRegisterCaptcha(@RequestParam String email) {
        Future<Result<SysUserDTO>> byEmail = executorService.submit(() -> {
            SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
            sysUserQueryRequest.setEmail(email);
            return sysUserApi.findByUniqueColumn(sysUserQueryRequest);
        });
        try {
            if (byEmail.get().getSuccess()) {
                return Mono.just(Result.fail(StatusCode.EMAIL_ALREADY_EXIST));
            }
        } catch (InterruptedException | ExecutionException e) {
            return Mono.just(Result.fail(e.getMessage()));
        }
        String code = captchaService.generateRegisterCaptcha(email);
        EmailRegisterCaptchaRequest request = new EmailRegisterCaptchaRequest();
        request.setEmail(email);
        request.setCode(code);
        Future<? extends Result<?>> future = executorService.submit(() -> emailApi.sendRegisterCaptcha(request));
        try {
            return Mono.just(future.get());
        } catch (InterruptedException | ExecutionException e) {
            return Mono.just(Result.fail(e.getMessage()));
        }
    }

}
