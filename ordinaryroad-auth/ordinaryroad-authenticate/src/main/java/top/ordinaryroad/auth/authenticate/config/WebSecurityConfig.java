package top.ordinaryroad.auth.authenticate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import top.ordinaryroad.auth.authenticate.handler.login.CustomizeAuthenticationFailureHandler;
import top.ordinaryroad.auth.authenticate.handler.login.CustomizeAuthenticationSuccessHandler;
import top.ordinaryroad.auth.authenticate.handler.CustomizeLogoutSuccessHandler;

/**
 * @author qq1962247851
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig
//        extends WebSecurityConfigurerAdapter
{

    /**
     * 登录成功处理逻辑
     */
    @Autowired
    private CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;
    /**
     * 登录失败处理逻辑
     */
    @Autowired
    private CustomizeAuthenticationFailureHandler authenticationFailureHandler;
    /**
     * 登出成功处理逻辑
     */
    @Autowired
    private CustomizeLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests.antMatchers("/oauth2/default/v1/keys").permitAll();
                    authorizeRequests.anyRequest().permitAll();
//                    authorizeRequests.anyRequest().authenticated();
                })
//                .formLogin(Customizer.withDefaults());
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer.loginProcessingUrl("/oauth2/login").permitAll()
                            .successHandler(authenticationSuccessHandler)
                            .failureHandler(authenticationFailureHandler);
                });
//                // 登入，允许所有用户
//                .formLogin().loginProcessingUrl("/oauth2/login").permitAll()
//                // 登录成功处理逻辑
//                .successHandler(authenticationSuccessHandler)
//                // 登录失败处理逻辑
//                .failureHandler(authenticationFailureHandler);
//                .logout(httpSecurityLogoutConfigurer -> {
        // 登出，允许所有用户
//                    httpSecurityLogoutConfigurer.logoutUrl("/logout").permitAll()
//                            // 登出成功处理逻辑
//                            .logoutSuccessHandler(logoutSuccessHandler)
//                            // 登出之后删除cookie
//                            .deleteCookies("JSESSIONID");
//                });
        return http.build();
    }


//    /**
//     * 权限拒绝处理逻辑
//     */
//    @Resource
//    CustomizeAccessDeniedHandler accessDeniedHandler;
//    /**
//     * 匿名用户访问无权限资源时的异常
//     */
//    @Resource
//    CustomizeAuthenticationEntryPoint authenticationEntryPoint;
//    /**
//     * 会话失效(账号被挤下线)处理逻辑
//     */
//    @Resource
//    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;
//    /**
//     * 会话过期处理逻辑
//     */
//    @Resource
//    CustomizeInvalidSessionStrategy invalidSessionStrategy;

//    /**
//     * 访问决策管理器
//     */
//    @Resource
//    CustomizeAccessDecisionManager accessDecisionManager;
//    /**
//     * 实现权限拦截
//     */
//    @Resource
//    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;
//
//    @Resource
//    private CustomizeAbstractSecurityInterceptor securityInterceptor;
//

//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authenticationProvider);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.headers().frameOptions().disable();
//        http.cors().and().csrf().disable();
//        http.authorizeRequests()
//                //TODO 增加规则
//                //antMatchers("/getUser").hasAuthority("query_user").
//                //antMatchers("/**").fullyAuthenticated().
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                        // 决策管理器
//                        o.setAccessDecisionManager(accessDecisionManager);
//                        // 安全元数据源
//                        o.setSecurityMetadataSource(securityMetadataSource);
//                        return o;
//                    }
//                })
//
//                // 登入，允许所有用户
//                .and().formLogin()
//                .loginProcessingUrl("/login").permitAll()
//                // 登录成功处理逻辑
//                .successHandler(authenticationSuccessHandler)
//                // 登录失败处理逻辑
//                .failureHandler(authenticationFailureHandler)
//                // 身份认证detail
//                .authenticationDetailsSource(authenticationDetailsSource)
//
//                // 登出，允许所有用户
//                .and().logout()
//                .logoutUrl("/logout").permitAll()
//                // 登出成功处理逻辑
//                .logoutSuccessHandler(logoutSuccessHandler)
//                // 登出之后删除cookie
//                .deleteCookies("JSESSIONID")
//
//                // 异常处理(权限拒绝、登录失效等)
//                .and().exceptionHandling()
//                // 权限拒绝处理逻辑
//                .accessDeniedHandler(accessDeniedHandler)
//                // 匿名用户访问无权限资源时的异常处理
//                .authenticationEntryPoint(authenticationEntryPoint)
//
//                // 会话管理
//                .and().sessionManagement()
//                // 回话过期异常处理
//                .invalidSessionStrategy(invalidSessionStrategy)
//                // 同一账号同时登录最大用户数
//                .maximumSessions(1)
//                // 会话失效(账号被挤下线)处理逻辑
//                .expiredSessionStrategy(sessionInformationExpiredStrategy);
//        http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);
//    }

}
