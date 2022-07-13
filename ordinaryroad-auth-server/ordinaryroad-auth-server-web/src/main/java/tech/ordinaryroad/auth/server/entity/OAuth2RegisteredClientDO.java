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
package tech.ordinaryroad.auth.server.entity;

import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.mybatis.model.BaseDO;

import javax.persistence.Table;

/**
 * 注册的客户端表
 *
 * @author mjz
 * @date 2021/11/15
 */
@Getter
@Setter
@Table(name = "oauth2_registered_client")
public class OAuth2RegisteredClientDO extends BaseDO {

    private static final long serialVersionUID = -2913489148235618445L;

    /**
     * clientId
     */
    private String clientId;

    /**
     * clientSecret
     */
    private String clientSecret;

    /**
     * clientName
     */
    private String clientName;

    /**
     * 重定向链接
     */
    private String redirectUris;

    /**
     * 授权范围
     */
    private String scopes;

}
