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
import tech.ordinaryroad.auth.server.dao.OAuth2OpenidDAO;
import tech.ordinaryroad.auth.server.entity.OAuth2OpenidDO;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;
import java.util.Optional;

/**
 * @author mjz
 * @date 2021/11/15
 */
@Service
public class OAuth2OpenidService extends BaseService<OAuth2OpenidDAO, OAuth2OpenidDO> {

    public Optional<OAuth2OpenidDO> findByClientIdAndOrNumber(String clientId, String orNumber) {
        Sqls sqls = Sqls.custom();

        sqls.andEqualTo("clientId", clientId);
        sqls.andEqualTo("orNumber", orNumber);

        Example example = Example.builder(OAuth2OpenidDO.class)
                .where(sqls)
                .build();

        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public Optional<OAuth2OpenidDO> findByClientIdAndOpenid(String clientId, String openid) {
        Sqls sqls = Sqls.custom();

        sqls.andEqualTo("clientId", clientId);
        sqls.andEqualTo("openid", openid);

        Example example = Example.builder(OAuth2OpenidDO.class)
                .where(sqls)
                .build();

        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public Optional<OAuth2OpenidDO> findByOpenid(String openid) {
        Example example = Example.builder(OAuth2OpenidDO.class)
                .where(Sqls.custom().andEqualTo("openid", openid))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<OAuth2OpenidDO> findAll(OAuth2OpenidDO oAuth2OpenidDO) {
        Sqls sqls = Sqls.custom();

        String orNumber = oAuth2OpenidDO.getOrNumber();
        if (StrUtil.isNotBlank(orNumber)) {
            sqls.andLike("orNumber", "%" + orNumber + "%");
        }
        String clientId = oAuth2OpenidDO.getClientId();
        if (StrUtil.isNotBlank(clientId)) {
            sqls.andLike("clientId", "%" + clientId + "%");
        }
        String openid = oAuth2OpenidDO.getOpenid();
        if (StrUtil.isNotBlank(openid)) {
            sqls.andLike("openid", "%" + openid + "%");
        }

        return super.dao.selectByExample(Example.builder(OAuth2OpenidDO.class).where(sqls).build());
    }

}
