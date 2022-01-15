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

import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.ordinaryroad.auth.server.constants.ServiceNameCons;
import tech.ordinaryroad.auth.server.dto.OAuth2UserInfoDTO;
import tech.ordinaryroad.commons.core.base.result.Result;

/**
 * @author mjz
 * @date 2022/1/15
 */
@Api(value = "OAuth2 API")
@FeignClient(name = ServiceNameCons.SERVICE_NAME, contextId = "iOAuth2Api")
public interface IOAuth2Api {

    /**
     * 根据clientId和openid获取or帐号
     *
     * @param clientId clientId
     * @param openid   openid
     * @return Result
     */
    @RequestMapping("/oauth2/getOrNumber")
    Result<String> getOrNumber(@RequestParam("client_id") String clientId, @RequestParam String openid);

    /**
     * Client端根据 Access-Token 置换用户信息
     *
     * @param accessToken Access Token
     * @return OAuth2UserInfoDTO
     */
    @RequestMapping("/oauth2/userinfo")
    Result<OAuth2UserInfoDTO> userinfo(@RequestParam("access_token") String accessToken);

}
