package top.ordinaryroad.auth.authenticate.handler;

import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;
import top.ordinaryroad.commons.core.base.cons.StatusCode;
import top.ordinaryroad.commons.core.utils.HttpServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 会话信息过期策略
 */
@Component
public class CustomizeInvalidSessionStrategy implements InvalidSessionStrategy {

    @Override
    public void onInvalidSessionDetected(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        httpServletRequest.getSession();
        HttpServletUtils.write(httpServletResponse, StatusCode.USER_ACCOUNT_EXPIRED);
    }

}
