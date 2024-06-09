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

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.log.RequestWrapper;
import tech.ordinaryroad.commons.log.ResponseWrapper;
import tech.ordinaryroad.commons.log.entity.OperationLogDO;
import tech.ordinaryroad.commons.log.service.IOperationLogInterceptorService;
import tech.ordinaryroad.commons.log.service.OperationLogService;
import tech.ordinaryroad.commons.log.utils.ServletUtils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author mjz
 * @date 2022/11/28
 */
@Slf4j
@Configuration
@ConditionalOnExpression("${ordinaryroad.commons.log.interceptor.enabled:true}")
public class OperationLogInterceptor implements HandlerInterceptor {

    private static final int MAX_BODY_LENGTH = 10000000;
    private final ThreadLocal<Long> TL_START_TIME = new ThreadLocal<>();
    private final ThreadLocal<OperationLogDO> TL_OPERATION_LOG = new ThreadLocal<>();

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private IOperationLogInterceptorService operationLogInterceptorService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (log.isDebugEnabled()) {
            log.debug("==========request start==========");
        }
        TL_START_TIME.set(System.currentTimeMillis());
        Object loginIdDefaultNull = StpUtil.getLoginIdDefaultNull();
        OperationLogDO operationLogDO = new OperationLogDO();
        TL_OPERATION_LOG.set(operationLogDO);

        operationLogDO.setIp(ServletUtils.getClientIP(request));
        operationLogDO.setPath(request.getRequestURI());
        operationLogDO.setMethod(request.getMethod());
        operationLogDO.setHeaders(JSON.toJSONString(ServletUtils.getHeaderMap(request)));
        operationLogDO.setCookies(JSON.toJSONString(ServletUtils.readCookieMap(request)));
        operationLogDO.setQueryParams(JSON.toJSONString(ServletUtils.getQueryParamsMap(request)));

        if (request instanceof RequestWrapper) {
            // 长度最大一百万
            operationLogDO.setRequest(StrUtil.subWithLength(((RequestWrapper) request).getBody(), 0, MAX_BODY_LENGTH));
        }

        if (log.isDebugEnabled()) {
            log.debug("current userId: {}", loginIdDefaultNull);
            log.debug("ip: {}", operationLogDO.getIp());
            log.debug("path: {}", operationLogDO.getPath());
            log.debug("method: {}", operationLogDO.getMethod());
            log.debug("headers: {}", operationLogDO.getHeaders());
            log.debug("cookies: {}", operationLogDO.getCookies());
            // log.debug("path params: {}", operationLogDO.getPathParams());
            log.debug("query params: {}", operationLogDO.getQueryParams());
            log.debug("request body: {}", operationLogDO.getRequest());
        }

        return Boolean.TRUE;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        OperationLogDO operationLogDO = TL_OPERATION_LOG.get();

        if (response instanceof ResponseWrapper) {
            operationLogDO.setType(operationLogInterceptorService.getType(request, response, null));
        }
        HttpStatus httpStatus = HttpStatus.resolve(response.getStatus());
        Optional.ofNullable(httpStatus).ifPresent(t -> operationLogDO.setStatus(t.name()));

        operationLogDO.setResponseHeaders(JSON.toJSONString(ServletUtils.getHeaderMap(response)));

        Result<?> result = null;
        if (response instanceof ResponseWrapper) {
            String responseString = new String(((ResponseWrapper) response).toByteArray(), StandardCharsets.UTF_8);
            try {
                result = JSON.parseObject(responseString, Result.class);
            } catch (Exception e) {
                // ignore
            }
            // 长度最大一百万
            operationLogDO.setResponse(StrUtil.subWithLength(responseString, 0, MAX_BODY_LENGTH));
        }
        Optional.ofNullable(operationLogInterceptorService.getType(request, response, result)).ifPresent(operationLogDO::setType);
        Optional.ofNullable(operationLogInterceptorService.getStatus(request, response, result)).ifPresent(operationLogDO::setStatus);

        long consumedTime = System.currentTimeMillis() - TL_START_TIME.get();
        operationLogDO.setConsumedTime(consumedTime);

        if (operationLogDO.getType() != null) {
            // save
            operationLogService.createSelective(operationLogDO);
        } else {
            if (!HttpMethod.GET.matches(operationLogDO.getMethod())) {
                log.warn("OperationLog type is null when not GET");
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("skip GET method when type is null");
                }
            }
        }

        TL_OPERATION_LOG.remove();
        TL_START_TIME.remove();

        if (log.isDebugEnabled()) {
            log.debug("response status: {}", operationLogDO.getStatus());
            log.debug("response headers: {}", operationLogDO.getResponseHeaders());
            // log.debug("response cookies: {}", operationLogDO.getResponseCookies());
            log.debug("response body: {}", operationLogDO.getResponse());
            log.debug("consumed time: {}", operationLogDO.getConsumedTime());
            log.debug("==========request end==========");
        }
    }

}
