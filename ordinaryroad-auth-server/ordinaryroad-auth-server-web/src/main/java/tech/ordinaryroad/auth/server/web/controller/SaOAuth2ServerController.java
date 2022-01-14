package tech.ordinaryroad.auth.server.web.controller;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.util.SaResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.auth.server.api.IOAuth2OpenidApi;
import tech.ordinaryroad.auth.server.dto.OAuth2OpenidDTO;
import tech.ordinaryroad.auth.server.dto.OAuth2UserInfoDTO;
import tech.ordinaryroad.auth.server.request.OAuth2OpenidQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.api.ISysUserApi;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;

/**
 * Sa-OAuth2 Server端 控制器
 */
@RestController
@RequiredArgsConstructor
public class SaOAuth2ServerController {

    private final IOAuth2OpenidApi oAuth2OpenidApi;
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
        OAuth2OpenidQueryRequest request = new OAuth2OpenidQueryRequest();
        request.setClientId(clientId);
        request.setOpenid(clientId);
        Result<OAuth2OpenidDTO> byClientIdAndOpenid = oAuth2OpenidApi.findByClientIdAndOpenid(request);
        if (byClientIdAndOpenid.getSuccess()) {
            return Result.success(byClientIdAndOpenid.getData().getOrNumber());
        } else {
            return Result.fail();
        }
    }

    /**
     * Client端根据 Access-Token 置换用户信息
     *
     * @param accessToken Access Token
     * @return OAuth2UserInfoDTO
     */
    @RequestMapping("/oauth2/userinfo")
    public Result<OAuth2UserInfoDTO> userinfo(@RequestParam("access_token") String accessToken) {
        // 获取 Access-Token 对应的账号id
        String orNumber = (String) SaOAuth2Util.getLoginIdByAccessToken(accessToken);
        // 校验 Access-Token 是否具有权限: userinfo
        SaOAuth2Util.checkScope(accessToken, "userinfo");

        OAuth2UserInfoDTO oAuth2UserInfoDTO = new OAuth2UserInfoDTO();
        // 获取User
        SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
        sysUserQueryRequest.setOrNumber(orNumber);
        SysUserDTO sysUserDTO = sysUserApi.findByUniqueColumn(sysUserQueryRequest).getData();
        oAuth2UserInfoDTO.setUser(sysUserDTO);
        return Result.success(oAuth2UserInfoDTO);
    }

}
