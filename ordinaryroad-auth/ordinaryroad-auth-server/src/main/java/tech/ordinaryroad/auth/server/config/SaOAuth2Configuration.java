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
package tech.ordinaryroad.auth.server.config;

import cn.dev33.satoken.oauth2.config.SaOAuth2Config;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Consts;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ModelAndView;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.api.ISysUserApi;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * OAuth2配置
 *
 * @author mjz
 * @date 2021/11/15
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SaOAuth2Configuration {

    private final ISysUserApi userApi;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public void setSaOAuth2Config(SaOAuth2Config cfg) {
        // 开启Password模式
        cfg.setIsPassword(true);

        cfg
                // TODO 美化登录页面 配置：未登录时返回的View
                .setNotLoginView(() -> new ModelAndView("login.html"))
                // 配置：登录处理函数
                .setDoLoginHandle((orNumber, password) -> {

                    SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
                    sysUserQueryRequest.setOrNumber(orNumber);
                    Result<SysUserDTO> byUniqueColumn = userApi.findByUniqueColumn(sysUserQueryRequest);
                    if (byUniqueColumn.getSuccess()) {
                        SysUserDTO data = byUniqueColumn.getData();

                        // 密码校验
                        if (passwordEncoder.matches(password, data.getPassword())) {
                            // 登录
                            log.info("登录成功：{}", orNumber);
                            StpUtil.login(data.getOrNumber());
                            return Result.success();
                        }
                    }
                    return Result.fail();
                })
                // TODO 配置：确认授权时返回的View
                .setConfirmView((clientId, scope) -> {
                    Map<String, Object> map = new HashMap<>(2);
                    map.put(SaOAuth2Consts.Param.client_id, clientId);
                    map.put(SaOAuth2Consts.Param.scope, scope);
                    return new ModelAndView("confirm.html", map);
                });
    }

}
