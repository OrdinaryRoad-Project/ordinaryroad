package tech.ordinaryroad.auth.server.controller;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.oauth2.model.AccessTokenModel;
import cn.dev33.satoken.util.SaResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.auth.server.dto.UserInfoDTO;
import tech.ordinaryroad.auth.server.entity.OAuth2OpenidDO;
import tech.ordinaryroad.auth.server.service.OAuth2OpenidService;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.api.ISysPermissionApi;
import tech.ordinaryroad.upms.api.ISysRequestPathApi;
import tech.ordinaryroad.upms.api.ISysRoleApi;
import tech.ordinaryroad.upms.api.ISysUserApi;
import tech.ordinaryroad.upms.dto.SysPermissionDTO;
import tech.ordinaryroad.upms.dto.SysRequestPathDTO;
import tech.ordinaryroad.upms.dto.SysRoleDTO;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.SysPermissionQueryRequest;
import tech.ordinaryroad.upms.request.SysRequestPathQueryRequest;
import tech.ordinaryroad.upms.request.SysRoleQueryRequest;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Sa-OAuth2 Server端 控制器
 */
@RestController
@RequiredArgsConstructor
public class SaOAuth2ServerController {

    private final OAuth2OpenidService openidService;
    private final ISysUserApi sysUserApi;
    private final ISysRoleApi sysRoleApi;
    private final ISysPermissionApi sysPermissionApi;
    private final ISysRequestPathApi sysRequestPathApi;

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

    /**
     * Client端根据 Access-Token 置换用户信息
     *
     * @param accessToken Access Token
     * @return UserInfoDTO
     */
    @RequestMapping("/oauth2/userinfo")
    public Result<UserInfoDTO> userinfo(@RequestParam("access_token") String accessToken) {
        // 获取 Access-Token 对应的账号id
        Object loginId = SaOAuth2Util.getLoginIdByAccessToken(accessToken);
        // 校验 Access-Token 是否具有权限: userinfo
        SaOAuth2Util.checkScope(accessToken, "userinfo");

        UserInfoDTO userInfoDTO = new UserInfoDTO();

        // 获取User
        SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
        sysUserQueryRequest.setOrNumber((String) loginId);
        SysUserDTO sysUserDTO = sysUserApi.findByUniqueColumn(sysUserQueryRequest).getData();
        userInfoDTO.setUser(sysUserDTO);

        // 校验 Access-Token 是否属于ordinaryroad-gateway，即通过网关登录需要返回用户相关权限信息
        AccessTokenModel accessTokenModel = SaOAuth2Util.checkAccessToken(accessToken);
        if ("ordinaryroad-gateway".equals(accessTokenModel.clientId)) {
            // 获取Roles
            SysRoleQueryRequest sysRoleQueryRequest = new SysRoleQueryRequest();
            sysRoleQueryRequest.setUserUuid(sysUserDTO.getUuid());
            List<SysRoleDTO> roles = sysRoleApi.findAllByUserUuid(sysRoleQueryRequest).getData();
            userInfoDTO.setRoles(roles);

            // 获取Permission
            SysPermissionQueryRequest sysPermissionQueryRequest = new SysPermissionQueryRequest();
            sysPermissionQueryRequest.setUserUuid(sysUserDTO.getUuid());
            List<SysPermissionDTO> permissions = sysPermissionApi.findAllByUserUuid(sysPermissionQueryRequest).getData();
            userInfoDTO.setPermissions(permissions);

            // 获取RequestPath
            SysRequestPathQueryRequest sysRequestPathQueryRequest = new SysRequestPathQueryRequest();
            List<String> permissionUuids = permissions.stream().map(SysPermissionDTO::getUuid).collect(Collectors.toList());
            sysRequestPathQueryRequest.setPermissionUuids(permissionUuids);
            List<SysRequestPathDTO> requestPaths = sysRequestPathApi.findAllByPermissionUuids(sysRequestPathQueryRequest).getData();
            userInfoDTO.setRequestPaths(requestPaths);
        }
        return Result.success(userInfoDTO);
    }

}
