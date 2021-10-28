package tech.ordinaryroad.commons.core.filter;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import tech.ordinaryroad.commons.core.utils.ip.IpUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author mjz
 * @date 2021/9/9
 */
@Slf4j
public class PreFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        log.debug("来源IP：{}", IpUtils.getIpAddr(servletRequest));
        log.debug("请求的地址：{}", servletRequest.getRequestURI());
        log.debug("请求方式：{}", servletRequest.getMethod());
//        log.debug("请求用户：{}", SecurityUtils.getUsername(servletRequest));
        log.debug("请求参数：{}", JSON.toJSONString(servletRequest.getParameterMap()));
        chain.doFilter(request, response);
    }

}
