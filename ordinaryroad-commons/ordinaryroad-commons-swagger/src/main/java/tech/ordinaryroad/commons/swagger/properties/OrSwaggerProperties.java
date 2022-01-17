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
package tech.ordinaryroad.commons.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.Contact;

/**
 * @author mjz
 * @date 2022/1/17
 */
@Data
@ConfigurationProperties(prefix = "ordinaryroad.commons.swagger")
public class OrSwaggerProperties {
    private String title = "OrdinaryRoad";
    private String description = "<div style='font-size:14px;color:red;'>OrdinaryRoad APIs</div>";
    private String termsOfServiceUrl;
    private String version = "1.0-SNAPSHOT";
    private String license = "MIT License";
    private String licenseUrl = "https://github.com/1962247851/ordinaryroad/blob/main/LICENSE";
    private Contact contact = new Contact("OrdinaryRoad", "https://ordinaryroad.top", "1962247851@qq.com");
    private EndpointsProperties endpoints = new EndpointsProperties();

    @Data
    public class EndpointsProperties {
        private TokenRequestEndpointProperties tokenRequest = new TokenRequestEndpointProperties();
        private TokenEndpointProperties token = new TokenEndpointProperties();
    }

    @Data
    public class TokenRequestEndpointProperties {
        private String url;
        private String clientId;
        private String clientSecret;
    }

    @Data
    public class TokenEndpointProperties {
        private String url;
        private String tokenName;
    }
}
