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

package tech.ordinaryroad.upms.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Component
public class SysApis {

//    /**
//     * <a href="https://www.jianshu.com/p/1bfb986f4f95">https://www.jianshu.com/p/1bfb986f4f95</a>
//     *
//     * @param lbFunction ReactorLoadBalancerExchangeFilterFunction
//     * @return WebClient
//     */
//    @Bean
//    @LoadBalanced
//    public WebClient webClient(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
//        return WebClient.builder()
////                .filter(lbFunction)
//                .build();
//    }

    @Bean
    public ISysApi sysApi(HttpServiceProxyFactory factory) {
        return factory.createClient(ISysApi.class);
    }

    @Bean
    public ISysDictApi sysDictApi(HttpServiceProxyFactory factory) {
        return factory.createClient(ISysDictApi.class);
    }

    @Bean
    public ISysDictItemApi sysDictItemApi(HttpServiceProxyFactory factory) {
        return factory.createClient(ISysDictItemApi.class);
    }

    @Bean
    public ISysFileApi sysFileApi(HttpServiceProxyFactory factory) {
        return factory.createClient(ISysFileApi.class);
    }

    @Bean
    public ISysPermissionApi sysPermissionApi(HttpServiceProxyFactory factory) {
        return factory.createClient(ISysPermissionApi.class);
    }

    @Bean
    public ISysRequestPathApi sysRequestPathApi(HttpServiceProxyFactory factory) {
        return factory.createClient(ISysRequestPathApi.class);
    }

    @Bean
    public ISysRoleApi sysRoleApi(HttpServiceProxyFactory factory) {
        return factory.createClient(ISysRoleApi.class);
    }

    @Bean
    public ISysRolesPermissionsApi sysRolesPermissionsApi(HttpServiceProxyFactory factory) {
        return factory.createClient(ISysRolesPermissionsApi.class);
    }

    @Bean
    public ISysUserApi sysUserApi(HttpServiceProxyFactory factory) {
        return factory.createClient(ISysUserApi.class);
    }

    @Bean
    public ISysUsersRolesApi sysUsersRolesApi(HttpServiceProxyFactory factory) {
        return factory.createClient(ISysUsersRolesApi.class);
    }
}