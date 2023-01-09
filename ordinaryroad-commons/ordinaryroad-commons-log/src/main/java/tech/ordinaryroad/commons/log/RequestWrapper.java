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

package tech.ordinaryroad.commons.log;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.MimeTypeUtils;
import tech.ordinaryroad.commons.base.exception.BaseException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 暂不支持MultipartRequest
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private String body = StrUtil.EMPTY;
    private long id;
    private final boolean jsonContentType;

    public RequestWrapper(Long requestId, HttpServletRequest request) {
        super(request);
        this.id = requestId;
        String contentType = request.getContentType();
        this.jsonContentType = StrUtil.isNotBlank(contentType) && MimeTypeUtils.APPLICATION_JSON.includes(MimeTypeUtils.parseMimeType(request.getContentType()));

        if (this.jsonContentType) {
            try {
                this.body = IoUtil.read(request.getInputStream(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new BaseException(e);
            }
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (this.jsonContentType) {
            final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));

            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener listener) {

                }

                @Override
                public int read() throws IOException {
                    return bais.read();
                }
            };
        } else {
            return super.getInputStream();
        }
    }

    public String getBody() {
        return body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
