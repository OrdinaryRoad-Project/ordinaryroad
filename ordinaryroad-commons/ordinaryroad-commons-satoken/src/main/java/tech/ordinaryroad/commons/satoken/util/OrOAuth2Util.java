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
package tech.ordinaryroad.commons.satoken.util;

import cn.dev33.satoken.context.SaHolder;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.http.HttpHeaders;

/**
 * @author mjz
 * @date 2022/4/24
 */
public class OrOAuth2Util {

    public static final String OAUTH2_PARAM_TOKEN_TYPE = "token_type";

    public static final String OAUTH2_AUTHORIZATION_TOKEN_TYPE = "Bearer";
    public static final Integer OAUTH2_AUTHORIZATION_HEADER_VALUE_ARRAY_SIZE = 2;

    /**
     * 从Authorization Header中获取AccessToken
     * Authorization：Bearer xxxxxxxxxxxxxxxxxx
     *
     * @return SaAccessToken
     */
    public static String getAccessToken() {
        String authorization = StrUtil.EMPTY;
        String authorizationHeaderValue = SaHolder.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isNotBlank(authorizationHeaderValue)) {
            String[] split = authorizationHeaderValue.split(StrUtil.SPACE);
            if (ArrayUtil.length(split) == OAUTH2_AUTHORIZATION_HEADER_VALUE_ARRAY_SIZE) {
                if (StrUtil.equals(split[0], OAUTH2_AUTHORIZATION_TOKEN_TYPE)) {
                    authorization = split[1];
                }
            }
        }
        return authorization;
    }

    public static String generateAuthorizationHeader(String accessToken) {
        return OAUTH2_AUTHORIZATION_TOKEN_TYPE + StrUtil.SPACE + StrUtil.emptyIfNull(accessToken);
    }

}
