package tech.ordinaryroad.auth.server.web.controller;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.util.SaResult;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.ordinaryroad.auth.server.api.IOAuth2Api;
import tech.ordinaryroad.auth.server.dto.OAuth2UserInfoDTO;
import tech.ordinaryroad.auth.server.facade.IOAuth2Facade;
import tech.ordinaryroad.auth.server.request.OAuth2GetOrNumberRequest;
import tech.ordinaryroad.auth.server.request.OAuth2UserinfoRequest;
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
     * https://sa-token.dev33.cn/doc/index.html#/oauth2/oauth2-api
     *
     * @param wrapped 是否封装，默认为否
     * @return Object
     */
    @RequestMapping(value = "/oauth2/*", method = {RequestMethod.GET, RequestMethod.POST})
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
    public Result<String> getOrNumber(@Validated @RequestBody OAuth2GetOrNumberRequest request) {
        return oAuth2Facade.getOrNumber(request);
    }

    @Override
    public Result<OAuth2UserInfoDTO> userinfo(@Validated @RequestBody OAuth2UserinfoRequest request) {
        return oAuth2Facade.userinfo(request);
    }

}
