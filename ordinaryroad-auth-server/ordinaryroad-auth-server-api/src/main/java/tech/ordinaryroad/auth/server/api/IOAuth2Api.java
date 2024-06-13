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
package tech.ordinaryroad.auth.server.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import tech.ordinaryroad.auth.server.dto.OAuth2UserInfoDTO;
import tech.ordinaryroad.auth.server.request.OAuth2GetOrNumberRequest;
import tech.ordinaryroad.commons.core.base.result.Result;

/**
 * @author mjz
 * @date 2022/1/15
 */
@Tag(name = "OAuth2 API")
@HttpExchange("http://ordinaryroad-auth-server")
public interface IOAuth2Api {

    /**
     * 根据clientId和openid获取or帐号
     *
     * @param request Request
     * @return Result
     */
    @PostExchange("/oauth2/getOrNumber")
    Result<String> getOrNumber(@Validated @RequestBody OAuth2GetOrNumberRequest request);

    /**
     * Client端根据 Access-Token 置换用户信息
     *
     * @param authorization AUTHORIZATION：Bearer ${access_token}
     * @param wrapped       是否使用Result包裹起来，默认False
     * @return Result / OAuth2UserInfoDTO
     */
    @GetExchange("/oauth2/userinfo")
    Object userinfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestParam(name = "wrapped", defaultValue = "false") Boolean wrapped);

    /**
     * Client端根据 Access-Token 置换用户信息（代码调用）
     *
     * @param authorization AUTHORIZATION：Bearer ${access_token}
     * @return Result
     */
    @GetExchange("/oauth2/userinfo/wrapped")
    Result<OAuth2UserInfoDTO> userinfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);

}
