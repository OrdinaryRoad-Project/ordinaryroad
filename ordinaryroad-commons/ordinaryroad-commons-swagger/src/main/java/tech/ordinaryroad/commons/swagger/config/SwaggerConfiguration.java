package tech.ordinaryroad.commons.swagger.config;

import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO OAuth2认证
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean(value = "defaultApi")
    public Docket defaultApi() {
        // schema
        List<GrantType> grantTypes = new ArrayList<>();

        // 授权码模式AuthorizationCodeGrant
        TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint("http://localhost:9302/oauth2/authorize", "ordinaryroad-knife", "secret");
        TokenEndpoint tokenEndpoint = new TokenEndpoint("http://localhost:9302/oauth2/token", "access_token");
        AuthorizationCodeGrant authorizationCodeGrant = new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint);
        grantTypes.add(authorizationCodeGrant);

        OAuth oAuth = new OAuthBuilder().name("oauth2").grantTypes(grantTypes).build();

        //context
        //scope方位
        List<AuthorizationScope> scopes = new ArrayList<>();
        scopes.add(new AuthorizationScope("read", "read all resources"));

        SecurityReference securityReference = new SecurityReference("oauth2", scopes.toArray(new AuthorizationScope[]{}));

        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(CollectionUtils.newArrayList(securityReference))
                .forPaths(PathSelectors.ant("/**"))
                .build();


        //schemas
        List<SecurityScheme> securitySchemes = CollectionUtils.newArrayList(oAuth);
        //securyContext
        List<SecurityContext> securityContexts = CollectionUtils.newArrayList(securityContext);

//        String groupName = "2.X版本";
        return new Docket(DocumentationType.SWAGGER_2)
//                .host("https://www.baidu.com")
                .apiInfo(groupApiInfo())
//                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("tech.ordinaryroad"))
                .paths(s -> !PathSelectors.regex("/error.*").test(s))
                .build()
                .securityContexts(securityContexts).securitySchemes(securitySchemes);
    }

    private ApiInfo groupApiInfo() {
        return new ApiInfoBuilder()
                .title("OrdinaryRoad接口文档")
                .description("<div style='font-size:14px;color:red;'>OrdinaryRoad APIs</div>")
                .termsOfServiceUrl("https://ordinaryroad.top")
//                .contact("group@qq.com")
                .version("1.0")
                .build();
    }

}
