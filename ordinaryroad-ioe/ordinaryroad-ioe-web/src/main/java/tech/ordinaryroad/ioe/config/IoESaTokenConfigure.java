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

package tech.ordinaryroad.ioe.config;

import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.ordinaryroad.commons.core.utils.exception.ExceptionUtils;

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
public class IoESaTokenConfigure {

    /**
     * 注册 Sa-Token全局过滤器
     *
     * @return SaReactorFilter
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 拦截地址
                .addInclude("/**")

                // 开放地址
                .addExclude("/webjars/**", "/favicon.ico", "/error")
                //                .addExclude("*.html", "*.css", "*.js", "*.ico")

                // 开放地址 Knife
                // https://sa-token.dev33.cn/doc/index.html#/more/common-questions?id=%e6%88%91%e4%bd%bf%e7%94%a8%e8%bf%87%e6%bb%a4%e5%99%a8%e9%89%b4%e6%9d%83-or-%e5%85%a8%e5%b1%80%e6%8b%a6%e6%88%aa%e5%99%a8%e9%89%b4%e6%9d%83%ef%bc%8c%e7%bb%93%e6%9e%9c-swagger-%e4%b8%8d%e8%83%bd%e8%ae%bf%e9%97%ae%e4%ba%86%ef%bc%8c%e6%88%91%e5%ba%94%e8%af%a5%e6%8e%92%e9%99%a4%e5%93%aa%e4%ba%9b%e5%9c%b0%e5%9d%80%ef%bc%9f
                .addExclude("/swagger-resources/**", "/**/v2/**", "/doc.html/**")

                // 开放地址 登录回调
                .addExclude("/authorized")

                // 开放地址 免登录接口
                .addExclude("/no_auth/**")

                // 鉴权方法：每次访问进入
                .setAuth(obj -> {

                    // Client校验和登录校验 -- 拦截所有路由
                    SaRouter.match("/**").check((r) -> {
                        // 校验是否登录
                        StpUtil.checkLogin();

                        String orNumber = StpUtil.getLoginIdAsString();

                        log.info("Sa-Token Filter, current user: orNumber:{}", orNumber);
                    });
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(ExceptionUtils::getResult);
    }

}
