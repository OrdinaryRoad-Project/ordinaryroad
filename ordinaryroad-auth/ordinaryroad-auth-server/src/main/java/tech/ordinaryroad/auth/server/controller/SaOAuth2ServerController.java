package tech.ordinaryroad.auth.server.controller;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.util.SaResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.auth.server.entity.OAuth2OpenidDO;
import tech.ordinaryroad.auth.server.service.OAuth2OpenidService;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.api.ISysUserApi;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;

import java.util.Optional;

/**
 * Sa-OAuth2 Server端 控制器
 */
@RestController
@RequiredArgsConstructor
public class SaOAuth2ServerController {

    private final OAuth2OpenidService openidService;
    private final ISysUserApi sysUserApi;

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

    /**
     * 根据clientId和openid获取or帐号
     *
     * @param clientId clientId
     * @param openid   openid
     * @return Result
     */
    @RequestMapping("/oauth2/getOrNumber")
    public Result<String> getOrNumber(@RequestParam("client_id") String clientId, @RequestParam String openid) {
        Optional<OAuth2OpenidDO> byClientIdAndOpenid = openidService.findByClientIdAndOpenid(clientId, openid);
        return byClientIdAndOpenid.map(oAuth2OpenidDO -> Result.success(oAuth2OpenidDO.getOrNumber())).orElseGet(Result::fail);
    }

    // TODO ---------- 开放相关资源接口： Client端根据 Access-Token ，置换相关资源 ------------
    // 获取Userinfo信息：昵称、头像、性别等等
    @RequestMapping("/oauth2/userinfo")
    public Result<SysUserDTO> userinfo() {
        // 获取 Access-Token 对应的账号id
        String accessToken = SaHolder.getRequest().getParamNotNull("Authorization");
        Object loginId = SaOAuth2Util.getLoginIdByAccessToken(accessToken);
        // 校验 Access-Token 是否具有权限: userinfo
        SaOAuth2Util.checkScope(accessToken, "userinfo");

        SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
        sysUserQueryRequest.setOrNumber((String) loginId);
        return sysUserApi.findByUniqueColumn(sysUserQueryRequest);
    }

}
