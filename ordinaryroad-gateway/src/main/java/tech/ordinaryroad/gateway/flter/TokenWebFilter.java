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
package tech.ordinaryroad.gateway.flter;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.oauth2.model.AccessTokenModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.core.utils.server.FilterRequestResponseUtil;
import tech.ordinaryroad.commons.core.utils.server.ServletUtils;


/**
 * 修改请求Header，增加satoken字段
 * <p>
 * https://stackoverflow.com/questions/58429556/globalfilter-vs-webfilter
 *
 * @author mjz
 * @date 2021/11/26
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class TokenWebFilter implements WebFilter {

    @NotNull
    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authorization = request.getHeaders().getFirst("Authorization");
        if (StrUtil.isNotBlank(authorization)) {
            String accessToken = authorization;
            assert accessToken != null;
            if (accessToken.contains(" ")) {
                accessToken = accessToken.substring(accessToken.indexOf(" ") + 1);
            }
            AccessTokenModel accessTokenModel;
            try {
                accessTokenModel = SaOAuth2Util.checkAccessToken(accessToken);
            } catch (Exception e) {
                return ServletUtils.webFluxResponseWriter(exchange.getResponse(), Result.fail(e.getMessage()));
            }
            String tokenValue = StpUtil.getTokenValueByLoginId(accessTokenModel.loginId);
            // 将Authorization解析为新header：satoken
            ServerHttpRequest newHttpRequest = FilterRequestResponseUtil.getNewHttpRequest(
                    request, FilterRequestResponseUtil.getNewHttpHeadersConsumer(tokenValue)
            );
            return chain.filter(exchange.mutate().request(newHttpRequest).build());
        } else {
            return chain.filter(exchange);
        }
    }

}
