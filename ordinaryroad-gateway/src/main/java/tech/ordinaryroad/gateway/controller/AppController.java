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
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.OkHttps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.gateway.request.LoginRequest;

import java.util.HashMap;

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

    /**
     * 应用id
     */
    private String clientId = "ordinaryroad-gateway";
    /**
     * 应用秘钥
     */
    private String clientSecret = "secret";

    @Value("${sa-token.oauth2.auth-server-host}")
    private String serverUrl;

    private final WebClient webClient;

    /**
     * 登录：http://auth-server:9302/oauth2/authorize?response_type=code&client_id=1001&redirect_uri=http://localhost:9090/authorized&scope=userinfo
     * 登录成功后的回调，设置为已登录状态
     * <p>
     * 回调示例：http://localhost:9090/authorized?code=8mVExAojCrZN10HmozB4QtV1KS8z8NNEN2CIWVKuAzy7lmWLUHDfIEhZZ5Ys
     *
     * @return Result
     */
    @GetMapping("authorized")
    public Result<JSONObject> authorized(@RequestParam String code) {
        log.info("auth-server登录成功回调：{}", code);

        // 用code获取token
        JSONObject params = new JSONObject();
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        return exchangeToken(params);
    }

    @PostMapping("login")
    public Result<JSONObject> login(@Validated @RequestBody LoginRequest request) {
        // TODO 支持username和邮箱登录
        String orNumber = request.getOrNumber();
        // 用帐号密码直接获取token
        JSONObject params = new JSONObject();
        params.put(SaOAuth2Consts.Param.grant_type, SaOAuth2Consts.GrantType.password);
        params.put(SaOAuth2Consts.Param.client_id, clientId);
        params.put(SaOAuth2Consts.Param.username, orNumber);
        params.put(SaOAuth2Consts.Param.password, request.getPassword());
        return exchangeToken(params);
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
     * @param params 请求参数
     * @return 返回给客户端的响应
     */
    private Result<JSONObject> exchangeToken(JSONObject params) {
        // 需要将结果封装
        params.put("wrapped", true);
        Result<JSONObject> response = Result.parse(
                OkHttps.sync("http://ordinaryroad-auth-server:9302/oauth2/token")
                        .addBodyPara(params)
                        .post()
                        .getBody().toString()
        );
        // code不等于200  代表请求失败
        if (response == null) {
            return Result.fail();
        }
        if (!response.getSuccess()) {
            return response;
        }

        // 根据openid获取其对应的userId
        JSONObject data = response.getData();
        String openid = data.getString("openid");
        String clientId = params.getString(SaOAuth2Consts.Param.client_id);
        HashMap<String, String> orNumberParams = new HashMap<>(2);
        orNumberParams.put(SaOAuth2Consts.Param.client_id, clientId);
        orNumberParams.put("openid", openid);
        Result<String> orNumberResponse = Result.parse(
                OkHttps.sync("http://ordinaryroad-auth-server:9302/oauth2/getOrNumber")
                        .addBodyPara(orNumberParams)
                        .post()
                        .getBody().toString()
        );
        if (orNumberResponse == null) {
            return Result.fail();
        }
        if (!response.getSuccess()) {
            return response;
        }
        String orNumber = orNumberResponse.getData();
        // 返回相关参数
        StpUtil.login(orNumber);
        log.info("网关登录成功：{}", orNumber);
        data.put("satoken", StpUtil.getTokenValue());
        return Result.success(data);
    }

}
