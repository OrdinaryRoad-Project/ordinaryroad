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

package tech.ordinaryroad.auth.server.web.controller;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Consts;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.BooleanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.ordinaryroad.auth.server.api.IOAuth2Api;
import tech.ordinaryroad.auth.server.dto.OAuth2UserInfoDTO;
import tech.ordinaryroad.auth.server.facade.IOAuth2Facade;
import tech.ordinaryroad.auth.server.request.OAuth2GetOrNumberRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.satoken.util.OrOAuth2Util;

import java.util.Map;

/**
 * Sa-OAuth2 Server端 控制器
 */
@RestController
@RequiredArgsConstructor
public class SaOAuth2ServerController implements IOAuth2Api {

    private final IOAuth2Facade oAuth2Facade;

    /**
     * 处理所有OAuth相关请求
     * https://sa-token.dev33.cn/doc/index.html#/oauth2/oauth2-api
     *
     * @param wrapped 是否封装，默认为否
     * @return Object
     */
    @RequestMapping(value = "/oauth2/*", method = {RequestMethod.GET, RequestMethod.POST})
    public Object oauth2(@RequestParam(defaultValue = "false") Boolean wrapped) {
        Object o = SaOAuth2Handle.serverRequest();
        if (SaHolder.getRequest().isPath(SaOAuth2Consts.Api.doConfirm)) {
            return o;
        }
        if (o instanceof SaResult) {
            SaResult saResult = (SaResult) o;
            Object data = saResult.getData();
            if (SaHolder.getRequest().isPath(SaOAuth2Consts.Api.token)) {
                if (data instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) data;
                    map.put(OrOAuth2Util.OAUTH2_PARAM_TOKEN_TYPE, OrOAuth2Util.OAUTH2_AUTHORIZATION_TOKEN_TYPE);
                }
            }
            if (wrapped) {
                return Result.success(data);
            } else {
                return data;
            }
        }
        return o;
    }

    @Override
    public Result<String> getOrNumber(@Validated @RequestBody OAuth2GetOrNumberRequest request) {
        return oAuth2Facade.getOrNumber(request);
    }

    @Override
    public Object userinfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestParam(name = "wrapped", defaultValue = "false") Boolean wrapped) {
        Result<OAuth2UserInfoDTO> userinfo = oAuth2Facade.userinfo();
        if (BooleanUtil.isTrue(wrapped)) {
            return userinfo;
        } else {
            return userinfo.getData();
        }
    }

    @Override
    public Result<OAuth2UserInfoDTO> userinfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return oAuth2Facade.userinfo();
    }

}
