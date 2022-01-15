package tech.ordinaryroad.auth.server.web.controller;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.util.SaResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.auth.server.api.IOAuth2Api;
import tech.ordinaryroad.auth.server.dto.OAuth2UserInfoDTO;
import tech.ordinaryroad.auth.server.facade.IOAuth2Facade;
import tech.ordinaryroad.commons.core.base.result.Result;

/**
 * Sa-OAuth2 Server端 控制器
 */
@RestController
@RequiredArgsConstructor
public class SaOAuth2ServerController implements IOAuth2Api {

    private final IOAuth2Facade oAuth2Facade;

    /**
     * 处理所有OAuth相关请求
     *
     * @param wrapped 是否封装，默认为否
     * @return Object
     */
    @RequestMapping("/oauth2/*")
    public Object oauth2(@RequestParam(defaultValue = "false") Boolean wrapped) {
        Object o = SaOAuth2Handle.serverRequest();
        if (o instanceof SaResult) {
            SaResult saResult = (SaResult) o;
            Object data = saResult.getData();
            if (wrapped) {
                return Result.success(data);
            } else {
                return data;
            }
        }
        return o;
    }

    @Override
    public Result<String> getOrNumber(@RequestParam("client_id") String clientId, @RequestParam String openid) {
        return oAuth2Facade.getOrNumber(clientId, openid);
    }

    @Override
    public Result<OAuth2UserInfoDTO> userinfo(@RequestParam("access_token") String accessToken) {
        return oAuth2Facade.userinfo(accessToken);
    }

}
