package tech.ordinaryroad.commons.core.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.Filter;

/**
 * @author mjz
 * @date 2021/9/9
 */
@Configuration
public class FilterConfig {

    @Bean
    @ConditionalOnExpression("${commons.web.clearFilter.enabled:true}")
    public FilterRegistrationBean<Filter> clearFilterRegistration() {
        PreFilter preFilter = new PreFilter();
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        // 添加过滤器
        registration.setFilter(preFilter);
        // 设置过滤路径，/*所有路径
        registration.addUrlPatterns("/*");
        // 设置优先级
        registration.setName("preFilter");
        // 设置优先级
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

}
