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
package tech.ordinaryroad.auth.server.service;

import cn.dev33.satoken.oauth2.model.SaClientModel;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.auth.server.dao.OAuth2RegisteredClientDAO;
import tech.ordinaryroad.auth.server.entity.OAuth2RegisteredClientDO;
import tech.ordinaryroad.commons.mybatis.service.BaseService;

import java.util.Objects;

/**
 * @author mjz
 * @date 2021/11/15
 */
@Service
public class OAuth2RegisteredClientService extends BaseService<OAuth2RegisteredClientDAO, OAuth2RegisteredClientDO> {

    public SaClientModel findByClientId(String clientId) {
        OAuth2RegisteredClientDO record = new OAuth2RegisteredClientDO();
        record.setClientId(clientId);
        OAuth2RegisteredClientDO oAuth2RegisteredClientDO = super.dao.selectOne(record);
        if (Objects.isNull(oAuth2RegisteredClientDO)) {
            return null;
        }
        return new SaClientModel(
                oAuth2RegisteredClientDO.getClientId(),
                oAuth2RegisteredClientDO.getClientSecret(),
                oAuth2RegisteredClientDO.getScopes(),
                oAuth2RegisteredClientDO.getRedirectUris()
        );
    }

}
