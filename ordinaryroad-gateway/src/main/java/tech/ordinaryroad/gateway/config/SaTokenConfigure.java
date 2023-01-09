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

package tech.ordinaryroad.gateway.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.base.exception.BaseException;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.core.utils.exception.ExceptionUtils;
import tech.ordinaryroad.gateway.properties.OrGatewayProperties;
import tech.ordinaryroad.upms.api.ISysPermissionApi;
import tech.ordinaryroad.upms.dto.SysPermissionDTO;
import tech.ordinaryroad.upms.request.SysPermissionQueryRequest;

import java.util.List;
import java.util.concurrent.*;

/**
 * [Sa-Token 权限认证] 配置类
 * 必须
 * https://sa-token.dev33.cn/doc/index.html#/start/webflux-example
 *
 * @author kong
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class SaTokenConfigure {

    private final OrGatewayProperties properties;
    private final ISysPermissionApi sysPermissionApi;

    private final ExecutorService executorService = new ThreadPoolExecutor(
            4, 8, 24L,
            TimeUnit.HOURS, new ArrayBlockingQueue<>(8), r -> {
        Thread thread = new Thread(r);
        thread.setName("gateway sa-token拦截器");
        return thread;
    });
    private static final String DEMO_MODE_NOT_ALLOWED_PATH_PATTERN = "^.*(create|update|delete|reset).*$";
    private static final String REGISTER_PATH = "/upms/user/register";
    private static final String FILE_UPLOAD_PATH = "/upms/file/upload";
    private static final String RESET_PASSWORD_BY_CODE_PATH = "/upms/user/reset/password_by_code";
    private static final String IM_MIMC_CALLBACK_PATH = "/im/mimc/callback";

    /**
     * 注册 Sa-Token全局过滤器
     *
     * @return SaReactorFilter
     */
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")

                // 开放地址 认证服务器OAuth2.0
                .addExclude("/auth/oauth2/*")

                // 开放地址
                .addExclude("/webjars/**", "/favicon.ico", "/error")
                //                .addExclude("*.html", "*.css", "*.js", "*.ico")

                // 开放地址 Knife
                // https://sa-token.dev33.cn/doc/index.html#/more/common-questions?id=%e6%88%91%e4%bd%bf%e7%94%a8%e8%bf%87%e6%bb%a4%e5%99%a8%e9%89%b4%e6%9d%83-or-%e5%85%a8%e5%b1%80%e6%8b%a6%e6%88%aa%e5%99%a8%e9%89%b4%e6%9d%83%ef%bc%8c%e7%bb%93%e6%9e%9c-swagger-%e4%b8%8d%e8%83%bd%e8%ae%bf%e9%97%ae%e4%ba%86%ef%bc%8c%e6%88%91%e5%ba%94%e8%af%a5%e6%8e%92%e9%99%a4%e5%93%aa%e4%ba%9b%e5%9c%b0%e5%9d%80%ef%bc%9f
                .addExclude("/swagger-resources/**", "/**/v2/**", "/doc.html/**")

                // 开放地址 登录 登出 登录回调
                .addExclude("/login", "/logout", "/authorized")

                // 开放地址 验证码
                .addExclude("/captcha/**")

                // 开放地址 图片下载
                .addExclude("/upms/file/download/**")

                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 文件上传校验：演示模式、client_id和client_secret
                    SaRouter.match(FILE_UPLOAD_PATH).check((r) -> {
                        // 1. 演示模式校验
                        if (properties.getDemoMode()) {
                            throw new BaseException(StatusCode.DEMO_MODE_FAIL);
                        }
                        // 2. 校验client_id和client_secret
                        // 因为请求体是 multipart-form-data，SaHolder.getRequest().getParam()获取不到
                        // 所以需要在controller中校验
                        // 进入controller
                        r.stop();
                    });

                    // 注册校验：演示模式
                    SaRouter.match(REGISTER_PATH).check((r) -> {
                        // 演示模式不允许注册
                        if (properties.getDemoMode()) {
                            throw new BaseException(StatusCode.DEMO_MODE_FAIL);
                        } else {
                            // 非演示模式注册不需要校验权限
                            r.stop();
                        }
                    });

                    // 即时消息回调校验：演示模式
                    SaRouter.match(IM_MIMC_CALLBACK_PATH).check((r) -> {
                        // 演示模式不允许即时消息回调
                        if (properties.getDemoMode()) {
                            throw new BaseException(StatusCode.DEMO_MODE_FAIL);
                        } else {
                            // 非演示模式即时消息回调不需要校验权限
                            r.stop();
                        }
                    });

                    // 重置密码校验：演示模式
                    SaRouter.match(RESET_PASSWORD_BY_CODE_PATH).check((r) -> {
                        // 演示模式不允许重置密码
                        if (properties.getDemoMode()) {
                            throw new BaseException(StatusCode.DEMO_MODE_FAIL);
                        } else {
                            // 非演示模式重置密码不需要校验权限
                            r.stop();
                        }
                    });

                    // Client校验和登录校验 -- 拦截所有路由
                    SaRouter.match("/**").check((r) -> {
                        // 0. 校验是否登录
                        StpUtil.checkLogin();

                        String orNumber = StpUtil.getLoginIdAsString();
                        List<String> roleList = StpUtil.getRoleList();
                        List<String> permissionList = StpUtil.getPermissionList();

                        log.info("Sa-Token Filter, current user: orNumber:{},\nroles:{},\npermissions:{}", orNumber, roleList, permissionList);

                        // 1. 获取当前路径
                        String requestPath = SaHolder.getRequest().getRequestPath();

                        log.info("Sa-Token Filter, path:{}", requestPath);

                        // 演示模式，并且不是允许的操作，拦截掉
                        if (properties.getDemoMode() && requestPath.matches(DEMO_MODE_NOT_ALLOWED_PATH_PATTERN)) {
                            throw new BaseException(StatusCode.DEMO_MODE_FAIL);
                        }

                        // 2. 获取路径所需权限
                        SysPermissionQueryRequest sysPermissionQueryRequest = new SysPermissionQueryRequest();
                        sysPermissionQueryRequest.setRequestPath(requestPath);
                        Future<Result<SysPermissionDTO>> resultFuture = executorService.submit(() -> sysPermissionApi.findByForeignColumn(sysPermissionQueryRequest));
                        Result<SysPermissionDTO> byRequestPath;
                        try {
                            byRequestPath = resultFuture.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                        if (byRequestPath == null || !byRequestPath.getSuccess()) {
                            // 2.1 未查到，不需要权限
                            return;
                        }

                        // 2.2 获取Permission Code
                        SysPermissionDTO sysPermissionDTO = byRequestPath.getData();
                        String permissionCode = sysPermissionDTO.getPermissionCode();

                        log.info("Sa-Token Filter, path required permission code:{}", permissionCode);

                        // 3. 权限校验
                        StpUtil.checkPermission(permissionCode);

                        log.info("Sa-Token Filter, path permission matched.");
                    });
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(ExceptionUtils::getResult);
    }

}
