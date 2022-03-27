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
package tech.ordinaryroad.commons.thingsboard.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ThingsBoard相关
 *
 * @author mjz
 * @date 2022/3/19
 */
@Data
@ConfigurationProperties(prefix = "ordinaryroad.commons.thingsboard")
public class OrThingsBoardProperties {

    private OrThingsBoardClientProperties client = new OrThingsBoardClientProperties();

    private OrThingsBoardTenantProperties tenant = new OrThingsBoardTenantProperties();

    private OrThingsBoardCustomerProperties customer = new OrThingsBoardCustomerProperties();

    @Data
    public static class OrThingsBoardClientProperties {
        /**
         * ThingsBoard REST API URL
         */
        private String url;

        /**
         * Tenant Administrator username
         */
        private String username;

        /**
         * Tenant Administrator password
         */
        private String password;
    }

    @Data
    public static class OrThingsBoardTenantProperties {
        /**
         * Tenant UUID
         */
        private String id;
    }

    @Data
    public static class OrThingsBoardCustomerProperties {

        public static final String DEFAULT_USER_PASSWORD = "Abc123";

        /**
         * 激活用户时的默认密码
         */
        private String defaultUserPassword = DEFAULT_USER_PASSWORD;
    }
}
