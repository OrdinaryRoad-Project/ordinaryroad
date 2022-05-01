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

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.auth.server.dao.OAuth2RegisteredClientDAO;
import tech.ordinaryroad.auth.server.entity.OAuth2RegisteredClientDO;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;
import java.util.Optional;

/**
 * @author mjz
 * @date 2021/11/15
 */
@Service
public class OAuth2RegisteredClientService extends BaseService<OAuth2RegisteredClientDAO, OAuth2RegisteredClientDO> {

    public Optional<OAuth2RegisteredClientDO> findByClientId(String clientId) {
        Example example = Example.builder(OAuth2RegisteredClientDO.class)
                .where(Sqls.custom().andEqualTo("clientId", clientId))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public Optional<OAuth2RegisteredClientDO> findByClientName(String clientName) {
        Example example = Example.builder(OAuth2RegisteredClientDO.class)
                .where(Sqls.custom().andEqualTo("clientName", clientName))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<OAuth2RegisteredClientDO> findAll(OAuth2RegisteredClientDO oAuth2RegisteredClientDO, BaseQueryRequest baseQueryRequest) {
        WeekendSqls<OAuth2RegisteredClientDO> sqls = WeekendSqls.custom();

        String clientId = oAuth2RegisteredClientDO.getClientId();
        if (StrUtil.isNotBlank(clientId)) {
            sqls.andLike(OAuth2RegisteredClientDO::getClientId, "%" + clientId + "%");
        }
        String clientName = oAuth2RegisteredClientDO.getClientName();
        if (StrUtil.isNotBlank(clientName)) {
            sqls.andLike(OAuth2RegisteredClientDO::getClientName, "%" + clientName + "%");
        }
        String redirectUris = oAuth2RegisteredClientDO.getRedirectUris();
        if (StrUtil.isNotBlank(redirectUris)) {
            sqls.andLike(OAuth2RegisteredClientDO::getRedirectUris, "%" + redirectUris + "%");
        }
        String scopes = oAuth2RegisteredClientDO.getScopes();
        if (StrUtil.isNotBlank(scopes)) {
            sqls.andLike(OAuth2RegisteredClientDO::getScopes, "%" + scopes + "%");
        }

        Example.Builder exampleBuilder = Example.builder(OAuth2RegisteredClientDO.class).where(sqls);

        return super.findAll(baseQueryRequest, sqls, exampleBuilder);
    }

}
