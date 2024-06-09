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

package tech.ordinaryroad.commons.log.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import tech.ordinaryroad.commons.log.RequestWrapper;
import tech.ordinaryroad.commons.log.ResponseWrapper;

import java.io.IOException;

/**
 * 包装HttpServlet，便于多次读取
 *
 * @author mjz
 * @date 2022/11/29
 */
@Configuration
@WebFilter(filterName = "WrapHttpServletFilter", urlPatterns = "/**")
@Order(10000)
public class WrapHttpServletFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        ServletRequest requestWrapper = servletRequest;
        if (servletRequest instanceof HttpServletRequest) {
            requestWrapper = new RequestWrapper(Thread.currentThread().getId(), (HttpServletRequest) servletRequest);
        }
        ServletResponse responseWrapper = servletResponse;
        if (servletResponse instanceof HttpServletResponse) {
            responseWrapper = new ResponseWrapper(Thread.currentThread().getId(), (HttpServletResponse) servletResponse);
        }
        filterChain.doFilter(requestWrapper, responseWrapper);
    }

}