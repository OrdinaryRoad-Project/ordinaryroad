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
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

}
