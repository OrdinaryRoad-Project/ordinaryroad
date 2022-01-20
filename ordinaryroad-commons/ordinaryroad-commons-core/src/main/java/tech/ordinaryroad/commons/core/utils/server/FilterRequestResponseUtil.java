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

package tech.ordinaryroad.commons.core.utils.server;


import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

/**
 * 过滤器请求/响应工具类
 *
 * @author mjz
 * @date 2021/11/26
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
