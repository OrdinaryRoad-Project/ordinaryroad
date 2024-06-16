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
import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.BooleanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.ordinaryroad.auth.server.dto.OAuth2UserInfoDTO;
import tech.ordinaryroad.auth.server.entity.OAuth2OpenidDO;
import tech.ordinaryroad.auth.server.mapstruct.OAuth2UserinfoMapStruct;
import tech.ordinaryroad.auth.server.request.OAuth2GetOrNumberRequest;
import tech.ordinaryroad.auth.server.service.OAuth2OpenidService;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.satoken.util.OrOAuth2Util;
import tech.ordinaryroad.upms.api.ISysUserApi;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;

import java.util.Map;
import java.util.Optional;

/**
 * Sa-OAuth2 Server端 控制器
 */
@RestController
@RequiredArgsConstructor
public class SaOAuth2ServerController {

    private final OAuth2OpenidService oAuth2OpenidService;
    private final ISysUserApi sysUserApi;
    private final OAuth2UserinfoMapStruct mapStruct;

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

    @PostMapping("/oauth2/getOrNumber")
    public Result<String> getOrNumber(@Validated @RequestBody OAuth2GetOrNumberRequest request) {
        Optional<OAuth2OpenidDO> byClientIdAndOpenid = oAuth2OpenidService.findByClientIdAndOpenid(request.getClientId(), request.getOpenid());
        return byClientIdAndOpenid.map(oAuth2OpenidDO -> Result.success(oAuth2OpenidDO.getOrNumber())).orElse(Result.fail(StatusCode.DATA_NOT_EXIST));
    }

    @GetMapping("/oauth2/userinfo")
    public Object userinfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestParam(name = "wrapped", defaultValue = "false") Boolean wrapped) {
        Result<OAuth2UserInfoDTO> userinfo = this.userinfo();
        if (BooleanUtil.isTrue(wrapped)) {
            return userinfo;
        } else {
            return userinfo.getData();
        }
    }

    @GetMapping("/oauth2/userinfo/wrapped")
    public Result<OAuth2UserInfoDTO> userinfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return this.userinfo();
    }

    private Result<OAuth2UserInfoDTO> userinfo() {
        String accessToken = OrOAuth2Util.getAccessToken();
        // 获取 Access-Token 对应的账号id
        String orNumber = (String) SaOAuth2Util.getLoginIdByAccessToken(accessToken);
        // 校验 Access-Token 是否具有权限: userinfo
        SaOAuth2Util.checkScope(accessToken, "userinfo");

        // 获取User
        SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
        sysUserQueryRequest.setOrNumber(orNumber);
        SysUserDTO sysUserDTO = sysUserApi.findByUniqueColumn(sysUserQueryRequest).getData();
        return Result.success(mapStruct.transfer(sysUserDTO));
    }

}
