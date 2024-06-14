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

package tech.ordinaryroad.commons.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.ordinaryroad.commons.swagger.properties.OrSwaggerProperties;

/**
 * OAuth2认证
 */
@RequiredArgsConstructor
@Configuration
public class SwaggerConfiguration {

    private final OrSwaggerProperties swaggerProperties;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info()
                        .title(swaggerProperties.getTitle())
                        .description(swaggerProperties.getDescription())
                        .termsOfService(swaggerProperties.getTermsOfServiceUrl())
                        .version(swaggerProperties.getVersion())
                        .license(new License().name(swaggerProperties.getLicense()).url(swaggerProperties.getLicenseUrl()))
                        .contact(swaggerProperties.getContact())
        );
    }

//    @Bean("defaultApi")
//    public Docket defaultApi() {
//        OrSwaggerProperties.EndpointsProperties endpointsProperties = swaggerProperties.getEndpoints();
//        // schema
//        List<GrantType> grantTypes = new ArrayList<>();
//
//        // 授权码模式AuthorizationCodeGrant
//        OrSwaggerProperties.TokenRequestEndpointProperties tokenRequestEndpointProperties = endpointsProperties.getTokenRequest();
//        TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint(
//                tokenRequestEndpointProperties.getUrl(),
//                tokenRequestEndpointProperties.getClientId(),
//                tokenRequestEndpointProperties.getClientSecret()
//        );
//        OrSwaggerProperties.TokenEndpointProperties tokenEndpointProperties = endpointsProperties.getToken();
//        TokenEndpoint tokenEndpoint = new TokenEndpoint(tokenEndpointProperties.getUrl(), tokenEndpointProperties.getTokenName());
//        AuthorizationCodeGrant authorizationCodeGrant = new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint);
//        grantTypes.add(authorizationCodeGrant);
//
//        OAuth oAuth = new OAuthBuilder().name("oauth2").grantTypes(grantTypes).build();
//
//        //context
//        //scope方位
//        List<AuthorizationScope> scopes = new ArrayList<>();
//        scopes.add(new AuthorizationScope("read", "read all resources"));
//
//        SecurityReference securityReference = new SecurityReference("oauth2", scopes.toArray(new AuthorizationScope[]{}));
//
//        SecurityContext securityContext = SecurityContext.builder()
//                .securityReferences(CollectionUtils.newArrayList(securityReference))
//                .forPaths(PathSelectors.ant("/**"))
//                .build();
//
//
//        //schemas
//        List<SecurityScheme> securitySchemes = CollectionUtils.newArrayList(oAuth);
//        //securyContext
//        List<SecurityContext> securityContexts = CollectionUtils.newArrayList(securityContext);
//
////        String groupName = "2.X版本";
//        return new Docket(DocumentationType.SWAGGER_2)
////                .host("https://www.baidu.com")
//                .apiInfo(groupApiInfo())
////                .groupName(groupName)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("tech.ordinaryroad"))
//                .paths(s -> !PathSelectors.regex("/error.*").test(s))
//                .build()
//                .securityContexts(securityContexts).securitySchemes(securitySchemes);
//    }
//
//    private ApiInfo groupApiInfo() {
//        return new ApiInfoBuilder()
//                .title(swaggerProperties.getTitle())
//                .description(swaggerProperties.getDescription())
//                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
//                .contact(swaggerProperties.getContact())
//                .version(swaggerProperties.getVersion())
//                .license(swaggerProperties.getLicense())
//                .licenseUrl(swaggerProperties.getLicenseUrl())
//                .build();
//    }

}
