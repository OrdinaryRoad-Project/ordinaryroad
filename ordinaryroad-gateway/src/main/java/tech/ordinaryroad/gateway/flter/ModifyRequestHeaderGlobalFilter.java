package tech.ordinaryroad.gateway.flter;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.oauth2.model.AccessTokenModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tech.ordinaryroad.commons.core.utils.server.FilterRequestResponseUtil;

/**
 * 修改请求Header，增加satoken字段
 *
 * @author mjz
 * @date 2021/11/26
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class ModifyRequestHeaderGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ServerHttpRequest request = exchange.getRequest();
            String authorization = request.getHeaders().getFirst("Authorization");
            if (StrUtil.isNotBlank(authorization)) {
                String accessToken = authorization;
                if (accessToken.contains(" ")) {
                    accessToken = accessToken.substring(accessToken.indexOf(" ") + 1);
                }
                AccessTokenModel accessTokenModel = SaOAuth2Util.checkAccessToken(accessToken);
                String tokenValue = StpUtil.getTokenValueByLoginId(accessTokenModel.loginId);
                // 将Authorization解析为新header：satoken
                ServerHttpRequest newHttpRequest = FilterRequestResponseUtil.getNewHttpRequest(
                        request, FilterRequestResponseUtil.getNewHttpHeadersConsumer(tokenValue)
                );
                return chain.filter(exchange.mutate().request(newHttpRequest).build());
            } else {
                return chain.filter(exchange);
            }
        } catch (Exception e) {
            return chain.filter(exchange);
        }
    }

}
