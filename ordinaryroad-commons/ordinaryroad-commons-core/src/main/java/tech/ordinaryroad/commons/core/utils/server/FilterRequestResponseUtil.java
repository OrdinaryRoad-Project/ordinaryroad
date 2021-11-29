package tech.ordinaryroad.commons.core.utils.server;


import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

/**
 * @author mazhen
 * @className FilterHeadersUtil
 * @Description 过滤器请求/响应工具类
 * @date 2020/10/29 9:31
 */
public final class FilterRequestResponseUtil {

    public static ServerHttpRequest getNewHttpRequest(ServerHttpRequest httpRequest
            , Consumer<HttpHeaders> httpHeadersConsumer, Flux<DataBuffer> dataBufferFlux) {
        ServerHttpRequest newHttpRequest = httpRequest.mutate()
                .headers(httpHeadersConsumer)
                .build();
        return new ServerHttpRequestDecorator(newHttpRequest) {
            @Override
            public Flux<DataBuffer> getBody() {
                return dataBufferFlux;
            }
        };
    }

    public static ServerHttpRequest getNewHttpRequest(ServerHttpRequest httpRequest, Consumer<HttpHeaders> httpHeadersConsumer) {
        return httpRequest.mutate()
                .headers(httpHeadersConsumer)
                .build();
    }

    public static Consumer<HttpHeaders> getNewHttpHeadersConsumer(String satoken) {
        Consumer<HttpHeaders> consumer = headers -> {
            headers.set("satoken", satoken);
        };
        return consumer;
    }
}
