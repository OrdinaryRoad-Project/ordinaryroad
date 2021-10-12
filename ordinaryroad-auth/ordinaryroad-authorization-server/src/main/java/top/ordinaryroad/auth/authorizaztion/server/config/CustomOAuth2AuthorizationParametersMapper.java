package top.ordinaryroad.auth.authorizaztion.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;

/**
 * 修复
 *
 * @author mjz
 */
public class CustomOAuth2AuthorizationParametersMapper extends JdbcOAuth2AuthorizationService.OAuth2AuthorizationParametersMapper {

    public CustomOAuth2AuthorizationParametersMapper() {
        ObjectMapper objectMapper = this.getObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

}