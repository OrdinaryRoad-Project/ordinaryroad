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
package tech.ordinaryroad.gateway.controller;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Consts;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tech.ordinaryroad.auth.server.api.IOAuth2Api;
import tech.ordinaryroad.auth.server.request.OAuth2GetOrNumberRequest;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.satoken.properties.OAuth2ClientProperties;
import tech.ordinaryroad.gateway.properties.OrGatewayProperties;
import tech.ordinaryroad.gateway.request.LoginRequest;
import tech.ordinaryroad.upms.api.ISysApi;
import tech.ordinaryroad.upms.dto.SysUserInfoDTO;
import tech.ordinaryroad.upms.request.SysUserInfoRequest;

import java.util.concurrent.*;

/**
 * 应用入口
 *
 * @author mjz
 * @date 2021/10/29
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class AppController {

    private final ISysApi sysApi;
    private final IOAuth2Api oAuth2Api;
    private final OrGatewayProperties gatewayProperties;
    private final OAuth2ClientProperties oAuth2ClientProperties;

    private final WebClient webClient;

    private final ExecutorService executorService = new ThreadPoolExecutor(
            2, 4, 12, TimeUnit.HOURS,
            new ArrayBlockingQueue<>(4), r -> {
        Thread thread = new Thread(r);
        thread.setName("AppController异步调用API线程");
        return thread;
    });

    /**
     * 登录：https://auth-server.ordinaryroad.tech:8302/oauth2/authorize?response_type=code&client_id=ordinaryroad-gateway&redirect_uri=https://ordinaryroad.tech:8090/authorized&scope=userinfo,openid
     * 登录成功后的回调，设置为已登录状态
     * <p>
     * 回调示例：https://ordinaryroad.tech:8090/authorized?code=WqgMfxtmJKVjzX9RHswCcgR4FsZyUtmcjnYEcVWqlIARl2zSgHH6c6UKAzvf
     *
     * @return Result
     */
    @GetMapping("authorized")
    public Result<JSONObject> authorized(@RequestParam String code) {
        log.info("auth-server登录成功回调：{}", code);

        // 用code获取token
        JSONObject params = new JSONObject();
        params.put(SaOAuth2Consts.Param.grant_type, SaOAuth2Consts.GrantType.authorization_code);
        params.put(SaOAuth2Consts.Param.code, code);
        params.put(SaOAuth2Consts.Param.client_id, oAuth2ClientProperties.getClientId());
        params.put(SaOAuth2Consts.Param.client_secret, oAuth2ClientProperties.getClientSecret());
        return exchangeToken(params, true);
    }

    @PostMapping("login")
    public Result<JSONObject> login(@Validated @RequestBody LoginRequest request) {
        // TODO 支持username和邮箱登录
        String orNumber = request.getOrNumber();
        // 用帐号密码直接获取token
        JSONObject params = new JSONObject();
        params.put(SaOAuth2Consts.Param.grant_type, SaOAuth2Consts.GrantType.password);
        params.put(SaOAuth2Consts.Param.client_id, oAuth2ClientProperties.getClientId());
        params.put(SaOAuth2Consts.Param.client_secret, oAuth2ClientProperties.getClientSecret());
        params.put(SaOAuth2Consts.Param.scope, "userinfo");
        params.put(SaOAuth2Consts.Param.username, orNumber);
        params.put(SaOAuth2Consts.Param.password, request.getPassword());
        return exchangeToken(params, request.getRememberMe());
    }

    @GetMapping("logout")
    public Result<?> logout() {
        if (StpUtil.isLogin()) {
            Object loginId = StpUtil.getLoginId();
            StpUtil.logout();
            log.info("网关登出成功：{}", loginId);
        }
        return Result.success();
    }

    /**
     * 向auth-server请求，换取token，并在gateway端登录
     *
     * @param params     请求参数
     * @param rememberMe 记住我
     * @return 返回给客户端的响应
     */
    private Result<JSONObject> exchangeToken(JSONObject params, Boolean rememberMe) {
        String grantType = params.getString(SaOAuth2Consts.Param.grant_type);
        String clientId = params.getString(SaOAuth2Consts.Param.client_id);
        String clientSecret = params.getString(SaOAuth2Consts.Param.client_secret);

        // 需要将结果封装
        params.put("wrapped", true);


        JSONObject tokenData;
        if (SaOAuth2Consts.GrantType.authorization_code.equals(grantType)) {
            String code = params.getString(SaOAuth2Consts.Param.code);
            Future<JSONObject> tokenFuture = executorService.submit(() -> oAuth2Api.token(grantType, clientId, clientSecret, code));
            try {
                tokenData = tokenFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                return Result.fail(e.getMessage());
            }
        } else if (SaOAuth2Consts.GrantType.password.equals(grantType)) {
            String scope = params.getString(SaOAuth2Consts.Param.scope);
            String username = params.getString(SaOAuth2Consts.Param.username);
            String password = params.getString(SaOAuth2Consts.Param.password);
            Future<JSONObject> tokenFuture = executorService.submit(() -> oAuth2Api.token(grantType, clientId, clientSecret, scope, username, password));
            try {
                tokenData = tokenFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                return Result.fail(e.getMessage());
            }
        } else {
            return Result.fail("不支持 " + grantType);
        }
        if (tokenData.containsKey("code") && tokenData.containsKey("msg")) {
            Integer code = tokenData.getInteger("code");
            if (StatusCode.SUCCESS.getCode() != code) {
                return Result.fail(tokenData.getString("msg"));
            }
        }

        // 根据openid获取其对应的userId
        String openid = tokenData.getString("openid");
        Result<String> orNumberResult;
        Future<Result<String>> orNumberFuture = executorService.submit(() -> {
            OAuth2GetOrNumberRequest request = new OAuth2GetOrNumberRequest();
            request.setClientId(clientId);
            request.setOpenid(openid);
            return oAuth2Api.getOrNumber(request);
        });
        try {
            orNumberResult = orNumberFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            return Result.fail(e.getMessage());
        }
        String orNumber = orNumberResult.getData();
        // 返回相关参数
        StpUtil.checkDisable(orNumber);
        StpUtil.login(orNumber, BooleanUtil.isTrue(rememberMe));
        String tokenValue = StpUtil.getTokenValue();
        tokenData.put("satoken", tokenValue);

        SysUserInfoRequest sysUserInfoRequest = new SysUserInfoRequest();
        sysUserInfoRequest.setSaToken(tokenValue);
        Result<SysUserInfoDTO> sysUserInfoDtoResult;
        Future<Result<SysUserInfoDTO>> userInfoFuture = executorService.submit(() -> sysApi.userInfo(sysUserInfoRequest));
        try {
            sysUserInfoDtoResult = userInfoFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            return Result.fail(e.getMessage());
        }
        tokenData.put("userInfo", sysUserInfoDtoResult.getData());

        log.info("Gateway login successfully. OrNumber：{}, Token：{}\nUserInfo: {}", orNumber, tokenValue, sysUserInfoDtoResult.getData());
        return Result.success(tokenData);
    }

}
