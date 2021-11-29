package tech.ordinaryroad.gateway.flter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.core.utils.server.ServletUtils;
import tech.ordinaryroad.gateway.service.ICaptchaService;

import java.nio.charset.StandardCharsets;

/**
 * 验证码过滤器
 *
 * @author ruoyi
 */
@RequiredArgsConstructor
@Order
@Component
public class ValidateCodeFilter implements WebFilter {

    private static final String[] VALIDATE_URL = new String[]{"/login", "/upms/user/register"};
    private static final String OR_NUMBER = "orNumber";
    private static final String EMAIL = "email";
    private static final String CODE = "code";

    private final ICaptchaService captchaService;

    @NotNull
    @Override
    public Mono<Void> filter(@NotNull ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 非登录/注册请求，不处理
        int index = ArrayUtil.indexOfIgnoreCase(VALIDATE_URL, request.getURI().getPath());
        if (index == ArrayUtil.INDEX_NOT_FOUND) {
            return chain.filter(exchange);
        }


        // set gateway:captcha.register:1962247851@qq.com:string "\xac\xed\x00\x05t\x00\x06565396"
        // 会造成 HttpMessageNotReadableException，得生成一个新的RequestBody
        // 解決方法 https://blog.csdn.net/cjbfzxz/article/details/106653242

        // 生成新的Request
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .flatMap(dataBuffer -> {
                    DataBufferUtils.retain(dataBuffer);

                    final Flux<DataBuffer> cachedFlux = Flux.defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));

                    // 修改后的新的request
                    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                        @NotNull
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return cachedFlux;
                        }
                    };
                    return Mono.from(
                            cachedFlux.flatMap(cachedDataBuffer -> {
                                JSONObject originalBody = JSONObject.parseObject(IoUtil.read(cachedDataBuffer.asInputStream(), StandardCharsets.UTF_8));
                                String code = originalBody.getString(CODE);
                                try {
                                    switch (index) {
                                        case 0:
                                            captchaService.checkLoginCaptcha(originalBody.getString(OR_NUMBER), code);
                                            break;
                                        case 1:
                                            captchaService.checkRegisterCaptcha(originalBody.getString(EMAIL), code);
                                            break;
                                        default:
                                    }
                                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                                } catch (Exception e) {
                                    return ServletUtils.webFluxResponseWriter(exchange.getResponse(), Result.fail(e.getMessage()));
                                }
                            })
                    );
                });
    }

}
