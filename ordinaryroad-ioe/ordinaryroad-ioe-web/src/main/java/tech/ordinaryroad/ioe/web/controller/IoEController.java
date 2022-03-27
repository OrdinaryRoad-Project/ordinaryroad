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
package tech.ordinaryroad.ioe.web.controller;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Consts;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thingsboard.server.common.data.Customer;
import org.thingsboard.server.common.data.User;
import tech.ordinaryroad.auth.server.api.IOAuth2Api;
import tech.ordinaryroad.auth.server.dto.OAuth2UserInfoDTO;
import tech.ordinaryroad.auth.server.request.OAuth2UserinfoRequest;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.exception.BaseException;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.satoken.properties.OAuth2ClientProperties;
import tech.ordinaryroad.commons.thingsboard.properties.OrThingsBoardProperties;
import tech.ordinaryroad.commons.thingsboard.service.OrThingsBoardCustomerService;
import tech.ordinaryroad.commons.thingsboard.service.OrThingsBoardUserService;
import tech.ordinaryroad.ioe.api.api.IIoEApi;
import tech.ordinaryroad.ioe.api.dto.IoEUserDTO;
import tech.ordinaryroad.ioe.api.dto.IoEUserinfoDTO;
import tech.ordinaryroad.ioe.api.request.IoEUserQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEUserSaveRequest;
import tech.ordinaryroad.ioe.facade.IIoEUserFacade;
import tech.ordinaryroad.upms.dto.SysUserDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mjz
 * @date 2022/3/22
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class IoEController implements IIoEApi {

    private final OAuth2ClientProperties oAuth2ClientProperties;
    private final IIoEUserFacade ioEUserFacade;
    private final IOAuth2Api oAuth2Api;
    private final OrThingsBoardProperties thingsBoardProperties;
    private final OrThingsBoardCustomerService thingsBoardCustomerService;
    private final OrThingsBoardUserService thingsBoardUserService;

    @Transactional(rollbackFor = BaseException.class)
    @Override
    public ModelAndView authorized(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String state,
            // refuse
            @RequestParam(required = false) String handle,
            // 拒绝消息
            @RequestParam(required = false) String msg
    ) {
        log.info("auth-server登录成功回调：{},{}", code, state);

        // 1. 用code获取Openid和AccessToken
        JSONObject params = new JSONObject();
        params.put(SaOAuth2Consts.Param.grant_type, SaOAuth2Consts.GrantType.authorization_code);
        params.put(SaOAuth2Consts.Param.code, code);
        params.put(SaOAuth2Consts.Param.client_id, oAuth2ClientProperties.getClientId());
        params.put(SaOAuth2Consts.Param.client_secret, oAuth2ClientProperties.getClientSecret());
        params.put(SaOAuth2Consts.Param.state, state);
        // 需要将结果封装
        params.put("wrapped", true);
        Result<JSONObject> response = Result.parse(
                HttpUtil.get(oAuth2ClientProperties.getAuthServerHost() + SaOAuth2Consts.Api.token, params)
        );
        // code不等于200  代表请求失败
        if (response == null) {
            throw new BaseException(StatusCode.COMMON_FAIL);
        }
        if (!response.getSuccess()) {
            throw new BaseException(response.getMsg());
        }

        JSONObject data = response.getData();
        log.info("IoE authorized(), 1. AccessToken: {}", data);
        final String accessToken = data.getString("access_token");
        String openid = data.getString("openid");

        // 2. 使用Openid登录IoE
        StpUtil.login(openid, Boolean.TRUE);
        final String tokenValue = StpUtil.getTokenValue();
        log.info("IoE authorized(), 2. login successfully.,\nAccessToken: {}\nOpenid：{}\nSaToken：{}", data, openid, tokenValue);

        // 3. 根据openid查询IoE User是否存在，不存在则直接创建
        final IoEUserQueryRequest request = new IoEUserQueryRequest();
        request.setOpenid(openid);
        final Result<IoEUserDTO> ioeUserResult = ioEUserFacade.findByUniqueColumn(request);
        IoEUserDTO ioEUserDTO;
        if (ioeUserResult.getSuccess()) {
            log.info("IoE authorized(), 3. IoEUser already existed, skip create");
            ioEUserDTO = ioeUserResult.getData();
        } else {
            log.info("IoE authorized(), 3.1/5 IoEUser not exist, creating...");
            // 获取userinfo
            final OAuth2UserinfoRequest oAuth2UserinfoRequest = new OAuth2UserinfoRequest();
            oAuth2UserinfoRequest.setAccessToken(accessToken);
            final Result<OAuth2UserInfoDTO> userinfoResult = oAuth2Api.userinfo(oAuth2UserinfoRequest);
            if (!userinfoResult.getSuccess()) {
                throw new BaseException(userinfoResult.getMsg());
            }
            final OAuth2UserInfoDTO userInfoDTO = userinfoResult.getData();
            final SysUserDTO sysUserDTO = userInfoDTO.getUser();
            final String defaultUsername = sysUserDTO.getUsername();

            // 创建Customer
            final String tenantId = thingsBoardProperties.getTenant().getId();
            final Customer customer = thingsBoardCustomerService.create(tenantId, null, defaultUsername);
            log.info("IoE authorized(), 3.2/5 ThingsBoard Customer created successfully, {}", customer);

            // 创建Customer下的User
            final String customerId = customer.getId().toString();
            final String email = sysUserDTO.getEmail();
            final User user = thingsBoardUserService.create(email, defaultUsername, null, tenantId, customerId);
            log.info("IoE authorized(), 3.3/5 ThingsBoard User created successfully, {}", user);

            // 直接激活User
            final String userId = user.getId().toString();
            thingsBoardUserService.active(userId);
            log.info("IoE authorized(), 3.4/5 ThingsBoard User activation succeeded");

            // 创建新IoE用户
            final IoEUserSaveRequest ioEUserSaveRequest = new IoEUserSaveRequest();
            ioEUserSaveRequest.setOpenid(openid);
            ioEUserSaveRequest.setCustomerId(customerId);
            ioEUserSaveRequest.setUserId(userId);
            ioEUserSaveRequest.setUsername(defaultUsername);
            final Result<IoEUserDTO> ioEUserDTOResult = ioEUserFacade.create(ioEUserSaveRequest);
            if (!ioEUserDTOResult.getSuccess()) {
                throw new BaseException(ioEUserDTOResult.getMsg());
            }
            ioEUserDTO = ioEUserDTOResult.getData();
            log.info("IoE authorized(), 3.5/5 IoEUser successfully created");
        }

        // 4. 处理完毕，传参到网页进行重定向
        Map<String, Object> map = new HashMap<>(3);
        map.put(SaOAuth2Consts.Param.access_token, accessToken);
        map.put("satoken", tokenValue);
        map.put(SaOAuth2Consts.Param.state, state);
        log.info("IoE authorized(), 4. Finished, IoEUser: {}", ioEUserDTO);
        return new ModelAndView("authorized.html", map);
    }

    @Override
    public Result<IoEUserinfoDTO> userinfo() {
        final IoEUserinfoDTO ioEUserinfoDTO = new IoEUserinfoDTO();

        final String openid = StpUtil.getLoginIdAsString();

        final IoEUserQueryRequest request = new IoEUserQueryRequest();
        request.setOpenid(openid);
        final Result<IoEUserDTO> ioeUserResult = ioEUserFacade.findByUniqueColumn(request);
        if (!ioeUserResult.getSuccess()) {
            return Result.fail();
        }
        ioEUserinfoDTO.setUser(ioeUserResult.getData());

        return Result.success(ioEUserinfoDTO);
    }

}