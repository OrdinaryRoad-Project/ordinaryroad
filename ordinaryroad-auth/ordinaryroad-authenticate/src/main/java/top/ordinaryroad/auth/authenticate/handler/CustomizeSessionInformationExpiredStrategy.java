package top.ordinaryroad.auth.authenticate.handler;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;
import top.ordinaryroad.commons.core.base.cons.StatusCode;
import top.ordinaryroad.commons.core.utils.HttpServletUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 会话信息过期（被挤下线）策略
 */
@Component
public class CustomizeSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException {
        HttpServletResponse httpServletResponse = sessionInformationExpiredEvent.getResponse();

        HttpServletUtils.write(httpServletResponse, StatusCode.USER_ACCOUNT_USE_BY_OTHERS);
    }
}
