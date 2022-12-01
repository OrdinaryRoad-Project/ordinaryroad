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

package tech.ordinaryroad.gateway.utils;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.ordinaryroad.commons.core.base.result.Result;

import java.util.function.Consumer;

/**
 * 客户端工具类
 *
 * @author mjz
 * @date 2021/11/27
 */
public class ServerHttpUtils {

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
        return headers -> {
            headers.set("satoken", satoken);
        };
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param data     响应成功的内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, Object data) {
        return webFluxResponseWriter(response, HttpStatus.OK, Result.success(data));
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param result   响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, Result<?> result) {
        return webFluxResponseWriter(response, HttpStatus.OK, result);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param status   http状态码
     * @param result   响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, HttpStatus status, Result<?> result) {
        return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, status, result);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response    ServerHttpResponse
     * @param contentType content-type
     * @param status      http状态码
     * @param result      响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String contentType, HttpStatus status, Result<?> result) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
        DataBuffer dataBuffer = response.bufferFactory().wrap(result.toString().getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
