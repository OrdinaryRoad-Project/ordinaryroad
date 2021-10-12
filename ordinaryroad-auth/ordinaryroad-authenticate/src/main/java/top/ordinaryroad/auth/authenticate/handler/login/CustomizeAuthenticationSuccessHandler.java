package top.ordinaryroad.auth.authenticate.handler.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import top.ordinaryroad.commons.core.base.result.Result;
import top.ordinaryroad.commons.core.utils.HttpServletUtils;
import top.ordinaryroad.system.RemoteUserService;
import top.ordinaryroad.system.entity.SysUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理逻辑
 */
@Slf4j
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RemoteUserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        //更新用户表上次登录时间、更新人、更新时间等字段
        //TODO 此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展
        Object principal = authentication.getPrincipal();
        String username = (String) principal;
        Result<SysUser> update = userService.updateLoginInfo(username);
        HttpServletUtils.write(httpServletResponse, update);

    }

}
