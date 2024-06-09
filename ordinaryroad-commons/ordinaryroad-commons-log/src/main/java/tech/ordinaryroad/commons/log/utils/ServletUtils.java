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
package tech.ordinaryroad.commons.log.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

/**
 * @author mjz
 * @date 2022/11/29
 */
public class ServletUtils {

    /**
     * 读取headerMap，(xxx,string[])
     *
     * @param request HttpServletRequest
     * @return Map<String, Enumeration < String>>
     */
    public static Map<String, List<String>> getHeaderMap(HttpServletRequest request) {
        final Map<String, List<String>> headerMap = new HashMap<>();

        final Enumeration<String> names = request.getHeaderNames();
        if (names != null) {
            String name;
            while (names.hasMoreElements()) {
                name = names.nextElement();
                headerMap.put(name, CollUtil.newArrayList(request.getHeaders(name)));
            }
        }

        return headerMap;
    }

    /**
     * 读取headerMap，(xxx,string[])
     *
     * @param response HttpServletResponse
     * @return Map<String, Enumeration < String>>
     */
    public static Map<String, Collection<String>> getHeaderMap(HttpServletResponse response) {
        final Map<String, Collection<String>> headerMap = new HashMap<>();

        final Collection<String> names = response.getHeaderNames();
        if (names != null) {
            for (String name : names) {
                headerMap.put(name, response.getHeaders(name));
            }
        }

        return headerMap;
    }

    /**
     * 读取CookieMap，(xxx,xxx=xxx)
     *
     * @param request HttpServletRequest
     * @return Map<String, String>
     */
    public static Map<String, String> readCookieMap(HttpServletRequest request) {
        final Map<String, String> cookieMap = new HashMap<>();

        final Cookie[] cookies = request.getCookies();
        if (ArrayUtil.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getName() + "=" + StrUtil.nullToEmpty(cookie.getValue()));
            }
        }

        return cookieMap;
    }

    /**
     * 读取queryParamsMap，(xxx,list)
     *
     * @param request HttpServletRequest
     * @return Map<String, String>
     */
    public static Map<String, List<String>> getQueryParamsMap(HttpServletRequest request) {
        final Map<String, List<String>> queryParamsMap = new HashMap<>();

        final String queryString = URLUtil.decode(request.getQueryString());
        if (StrUtil.isNotBlank(queryString)) {
            String[] split = queryString.split("&");
            String[] strings;
            for (String s : split) {
                strings = s.split("=");
                String key = strings[0];
                String value = strings[1];
                queryParamsMap.computeIfPresent(key, (k, v) -> {
                    v.add(value);
                    return v;
                });
                queryParamsMap.computeIfAbsent(key, k -> CollUtil.newArrayList(value));
            }
        }

        return queryParamsMap;
    }

    // region

    /**
     * 获取客户端IP {@link cn.hutool.extra.servlet.ServletUtil#getClientIP}
     *
     * <p>
     * 默认检测的Header:
     *
     * <pre>
     * 1、X-Forwarded-For
     * 2、X-Real-IP
     * 3、Proxy-Client-IP
     * 4、WL-Proxy-Client-IP
     * </pre>
     *
     * <p>
     * otherHeaderNames参数用于自定义检测的Header<br>
     * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
     * </p>
     *
     * @param request          请求对象{@link HttpServletRequest}
     * @param otherHeaderNames 其他自定义头文件，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     */
    public static String getClientIP(HttpServletRequest request, String... otherHeaderNames) {
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        if (ArrayUtil.isNotEmpty(otherHeaderNames)) {
            headers = ArrayUtil.addAll(headers, otherHeaderNames);
        }

        return getClientIPByHeader(request, headers);
    }

    /**
     * 获取客户端IP {@link cn.hutool.extra.servlet.ServletUtil#getClientIPByHeader}
     *
     * <p>
     * headerNames参数用于自定义检测的Header<br>
     * 需要注意的是，使用此方法获取的客户IP地址必须在Http服务器（例如Nginx）中配置头信息，否则容易造成IP伪造。
     * </p>
     *
     * @param request     请求对象{@link HttpServletRequest}
     * @param headerNames 自定义头，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     * @since 4.4.1
     */
    public static String getClientIPByHeader(HttpServletRequest request, String... headerNames) {
        String ip;
        for (String header : headerNames) {
            ip = request.getHeader(header);
            if (!NetUtil.isUnknown(ip)) {
                return NetUtil.getMultistageReverseProxyIp(ip);
            }
        }

        ip = request.getRemoteAddr();
        return NetUtil.getMultistageReverseProxyIp(ip);
    }
    // endregion

}
