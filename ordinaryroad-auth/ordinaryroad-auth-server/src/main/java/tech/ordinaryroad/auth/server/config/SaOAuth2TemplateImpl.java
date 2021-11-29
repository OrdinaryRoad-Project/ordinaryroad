package tech.ordinaryroad.auth.server.config;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.ordinaryroad.auth.server.entity.OAuth2OpenidDO;
import tech.ordinaryroad.auth.server.service.OAuth2OpenidService;
import tech.ordinaryroad.auth.server.service.OAuth2RegisteredClientService;
import tech.ordinaryroad.commons.core.utils.openid.OpenidUtil;

import java.util.Optional;

/**
 * Sa-Token OAuth2.0 整合实现
 */
@Component
@RequiredArgsConstructor
public class SaOAuth2TemplateImpl extends SaOAuth2Template {

    private final OAuth2OpenidService openidService;
    private final OAuth2RegisteredClientService registeredClientService;

    @Override
    public SaClientModel getClientModel(String clientId) {
        // 从数据库查询
        return registeredClientService.findByClientId(clientId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getOpenid(String clientId, Object loginId) {
        String orNumber = (String) loginId;

        // 从数据库查询，不存在则新增
        Optional<OAuth2OpenidDO> byClientIdAndOrNumber = openidService.findByClientIdAndOrNumber(clientId, orNumber);
        if (byClientIdAndOrNumber.isPresent()) {
            return byClientIdAndOrNumber.get().getOpenid();
        }
        // 新增
        String openid = OpenidUtil.generateRandom(clientId, orNumber);
        OAuth2OpenidDO oAuth2OpenidDO = new OAuth2OpenidDO();
        oAuth2OpenidDO.setClientId(clientId);
        oAuth2OpenidDO.setOrNumber(orNumber);
        oAuth2OpenidDO.setOpenid(openid);
        openidService.save(oAuth2OpenidDO);
        return openid;
    }

}
