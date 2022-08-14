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
package tech.ordinaryroad.commons.satoken.config;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.ordinaryroad.auth.server.api.IOAuth2OpenidApi;
import tech.ordinaryroad.auth.server.api.IOAuth2RegisteredClientApi;
import tech.ordinaryroad.auth.server.dto.OAuth2OpenidDTO;
import tech.ordinaryroad.auth.server.dto.OAuth2RegisteredClientDTO;
import tech.ordinaryroad.auth.server.request.OAuth2OpenidQueryRequest;
import tech.ordinaryroad.auth.server.request.OAuth2OpenidSaveRequest;
import tech.ordinaryroad.auth.server.request.OAuth2RegisteredClientQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.core.utils.openid.OpenidUtil;

/**
 * Sa-Token OAuth2.0 整合实现
 */
@Component
@RequiredArgsConstructor
public class SaOAuth2TemplateImpl extends SaOAuth2Template {

    private final IOAuth2OpenidApi oAuth2OpenidApi;
    private final IOAuth2RegisteredClientApi oAuth2RegisteredClientApi;

    @Override
    public SaClientModel getClientModel(String clientId) {
        // 从数据库查询
        OAuth2RegisteredClientQueryRequest request = new OAuth2RegisteredClientQueryRequest();
        request.setClientId(clientId);
        Result<OAuth2RegisteredClientDTO> byUniqueColumn = oAuth2RegisteredClientApi.findByUniqueColumn(request);
        if (byUniqueColumn.getSuccess()) {
            OAuth2RegisteredClientDTO data = byUniqueColumn.getData();
            return new SaClientModel()
                    .setClientId(data.getClientId())
                    .setClientSecret(data.getClientSecret())
                    .setContractScope(data.getScopes())
                    .setAllowUrl(data.getRedirectUris());
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getOpenid(String clientId, Object loginId) {
        String orNumber = (String) loginId;

        // 从数据库查询，不存在则新增
        OAuth2OpenidQueryRequest oAuth2OpenidQueryRequest = new OAuth2OpenidQueryRequest();
        oAuth2OpenidQueryRequest.setClientId(clientId);
        oAuth2OpenidQueryRequest.setOrNumber(orNumber);
        Result<OAuth2OpenidDTO> byClientIdAndOrNumber = oAuth2OpenidApi.findByClientIdAndOrNumber(oAuth2OpenidQueryRequest);
        if (byClientIdAndOrNumber.getSuccess()) {
            return byClientIdAndOrNumber.getData().getOpenid();
        }
        // 新增
        String openid = OpenidUtil.generateRandom(clientId, orNumber);

        OAuth2OpenidSaveRequest oAuth2OpenidSaveRequest = new OAuth2OpenidSaveRequest();
        oAuth2OpenidSaveRequest.setClientId(clientId);
        oAuth2OpenidSaveRequest.setOrNumber(orNumber);
        oAuth2OpenidSaveRequest.setOpenid(openid);
        oAuth2OpenidApi.create(oAuth2OpenidSaveRequest);
        return openid;
    }

}
