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
package tech.ordinaryroad.commons.log.service.impl;

import cn.hutool.core.util.EnumUtil;
import org.springframework.http.HttpStatus;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.log.service.IOperationLogInterceptorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 默认实现类
 *
 * @author mjz
 * @date 2022/11/29
 */
public class DefaultOperationLogInterceptorServiceImpl implements IOperationLogInterceptorService {

    @Override
    public Integer getType(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, Result<?> result) {
        Integer type;

        if (Objects.nonNull(result)) {
            type = result.getCode();
        } else {
            type = response.getStatus();
        }

        return type;
    }

    @Override
    public String getStatus(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, Result<?> result) {
        String status = null;

        // 先设置HTTP状态码
        HttpStatus httpStatus = HttpStatus.resolve(response.getStatus());
        if (Objects.nonNull(httpStatus)) {
            status = httpStatus.name();
        }

        // 尝试解析响应体中的code
        if (Objects.nonNull(result)) {
            int code = result.getCode();
            StatusCode statusCode = EnumUtil.getBy(StatusCode::getCode, code);
            if (Objects.nonNull(statusCode)) {
                status = statusCode.name();
            }
        }

        return status;
    }

}
