package top.ordinaryroad.auth.authenticate.handler.login;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import top.ordinaryroad.auth.authenticate.exception.NullUserNameException;
import top.ordinaryroad.commons.core.base.cons.StatusCode;
import top.ordinaryroad.commons.core.base.result.Result;
import top.ordinaryroad.commons.core.utils.HttpServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理逻辑
 */
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json数据
        Result<?> result;
        if (e instanceof AccountExpiredException) {
            // 账号过期
            result = Result.fail(StatusCode.USER_ACCOUNT_EXPIRED);
        } else if (e instanceof BadCredentialsException) {
            // 密码错误
            result = Result.fail(StatusCode.USER_CREDENTIALS_ERROR);
        } else if (e instanceof CredentialsExpiredException) {
            // 密码过期
            result = Result.fail(StatusCode.USER_CREDENTIALS_EXPIRED);
        } else if (e instanceof DisabledException) {
            // 账号不可用
            result = Result.fail(StatusCode.USER_ACCOUNT_DISABLE);
        } else if (e instanceof LockedException) {
            // 账号锁定
            result = Result.fail(StatusCode.USER_ACCOUNT_LOCKED);
        } else if (e instanceof NullUserNameException) {
            // 用户名为空
            result = Result.fail(StatusCode.PARAM_NOT_COMPLETE);
        } else if (e instanceof UsernameNotFoundException) {
            // 用户不存在
            result = Result.fail(StatusCode.USER_ACCOUNT_NOT_EXIST);
        } else if (e instanceof InternalAuthenticationServiceException) {
            // 用户不存在
            result = Result.fail(StatusCode.USER_ACCOUNT_NOT_EXIST);
        } else {
            // 其他错误
            result = Result.fail(StatusCode.COMMON_FAIL);
        }
        HttpServletUtils.write(httpServletResponse, result);
    }
}
