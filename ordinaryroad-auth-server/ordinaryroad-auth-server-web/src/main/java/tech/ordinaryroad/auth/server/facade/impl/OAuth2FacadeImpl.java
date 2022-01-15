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
package tech.ordinaryroad.auth.server.facade.impl;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.auth.server.dto.OAuth2UserInfoDTO;
import tech.ordinaryroad.auth.server.entity.OAuth2OpenidDO;
import tech.ordinaryroad.auth.server.facade.IOAuth2Facade;
import tech.ordinaryroad.auth.server.request.OAuth2GetOrNumberRequest;
import tech.ordinaryroad.auth.server.request.OAuth2UserinfoRequest;
import tech.ordinaryroad.auth.server.service.OAuth2OpenidService;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.api.ISysUserApi;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;

import java.util.Optional;

/**
 * @author mjz
 * @date 2022/1/15
 */
@RequiredArgsConstructor
@Component
public class OAuth2FacadeImpl implements IOAuth2Facade {

    private final OAuth2OpenidService oAuth2OpenidService;
    private final ISysUserApi userApi;

    @Override
    public Result<String> getOrNumber(OAuth2GetOrNumberRequest request) {
        Optional<OAuth2OpenidDO> byClientIdAndOpenid = oAuth2OpenidService.findByClientIdAndOpenid(request.getClientId(), request.getOpenid());
        return byClientIdAndOpenid.map(oAuth2OpenidDO -> Result.success(oAuth2OpenidDO.getOrNumber())).orElseGet(Result::fail);
    }

    @Override
    public Result<OAuth2UserInfoDTO> userinfo(OAuth2UserinfoRequest request) {
        String accessToken = request.getAccessToken();
        // 获取 Access-Token 对应的账号id
        String orNumber = (String) SaOAuth2Util.getLoginIdByAccessToken(accessToken);
        // 校验 Access-Token 是否具有权限: userinfo
        SaOAuth2Util.checkScope(accessToken, "userinfo");

        OAuth2UserInfoDTO oAuth2UserInfoDTO = new OAuth2UserInfoDTO();
        // 获取User
        SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
        sysUserQueryRequest.setOrNumber(orNumber);
        SysUserDTO sysUserDTO = userApi.findByUniqueColumn(sysUserQueryRequest).getData();
        oAuth2UserInfoDTO.setUser(sysUserDTO);
        return Result.success(oAuth2UserInfoDTO);
    }

}
