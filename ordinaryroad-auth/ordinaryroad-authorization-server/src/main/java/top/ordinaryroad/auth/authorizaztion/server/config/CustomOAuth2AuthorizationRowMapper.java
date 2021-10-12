package top.ordinaryroad.auth.authorizaztion.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.LinkedHashSet;

/**
 * 修复序列化反序列化LinkedHashSet出现的问题
 *
 * @author mjz
 */
public class CustomOAuth2AuthorizationRowMapper extends JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper {

    public CustomOAuth2AuthorizationRowMapper(RegisteredClientRepository registeredClientRepository) {
        super(registeredClientRepository);
        // Add LinkedHashSet to white list
        ObjectMapper objectMapper = this.getObjectMapper();
        objectMapper.addMixIn(LinkedHashSet.class, LinkedHashSetMixin.class);
    }

}