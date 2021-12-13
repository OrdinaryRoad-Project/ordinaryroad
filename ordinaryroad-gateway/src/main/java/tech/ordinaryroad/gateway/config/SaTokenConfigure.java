package tech.ordinaryroad.gateway.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.exception.BaseException;
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

                // 开放地址
                .addExclude("/webjars/**", "/favicon.ico", "/error")
                //                .addExclude("*.html", "*.css", "*.js", "*.ico")

                // 开放地址 Knife
                // https://sa-token.dev33.cn/doc/index.html#/more/common-questions?id=%e6%88%91%e4%bd%bf%e7%94%a8%e8%bf%87%e6%bb%a4%e5%99%a8%e9%89%b4%e6%9d%83-or-%e5%85%a8%e5%b1%80%e6%8b%a6%e6%88%aa%e5%99%a8%e9%89%b4%e6%9d%83%ef%bc%8c%e7%bb%93%e6%9e%9c-swagger-%e4%b8%8d%e8%83%bd%e8%ae%bf%e9%97%ae%e4%ba%86%ef%bc%8c%e6%88%91%e5%ba%94%e8%af%a5%e6%8e%92%e9%99%a4%e5%93%aa%e4%ba%9b%e5%9c%b0%e5%9d%80%ef%bc%9f
                .addExclude("/swagger-resources/**", "/**/v2/**", "/doc.html/**")

                // 开放地址 登录 登出 登录回调
                .addExclude("/login", "/logout", "/authorized")
                // 开放地址 注册
                .addExclude("/upms/user/register")

                // 开放地址 验证码
                .addExclude("/captcha/**")

                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
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
                        Result<SysPermissionDTO> byRequestPath = null;
                        try {
                            byRequestPath = resultFuture.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
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
