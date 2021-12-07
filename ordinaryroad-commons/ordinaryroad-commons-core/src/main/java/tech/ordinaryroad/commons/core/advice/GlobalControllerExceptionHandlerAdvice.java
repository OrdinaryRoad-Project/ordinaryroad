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
package tech.ordinaryroad.commons.core.advice;

import cn.dev33.satoken.exception.SaTokenException;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.exception.BaseException;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.core.utils.exception.ExceptionUtils;

import javax.servlet.http.HttpServletResponse;


/**
 * 全局Controller异常捕获
 *
 * @author mjz
 * @date 2021/10/27
 */
@Slf4j
@ControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class GlobalControllerExceptionHandlerAdvice {
    /**
     * 全局异常处理，异常返回统一处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result<?> exceptionHandler(HttpServletResponse response, Exception ex) {
        return excepHandler(ex);
    }

    /**
     * exception 处理
     *
     * @param ex Exception
     * @return Result
     */
    private Result<?> excepHandler(Exception ex) {
        log.error("全局异常拦截", ex);
        String rootCauseMessage = StrUtil.subPre(ExceptionUtil.getRootCauseMessage(ex), 500);
        if (ex instanceof BaseException) {
            BaseException bizEx = (BaseException) ex;
            int code = 0;
            if (bizEx.getStatusCode() != null && bizEx.getStatusCode().getCode() != 0) {
                code = bizEx.getStatusCode().getCode();
            }
            String bizExMessage = bizEx.getMessage();
//            log.error("traceId：{}", MDC.get("traceId"));
            if (StrUtil.isNotBlank(bizExMessage)) {
                return Result.fail(code, bizExMessage, StrUtil.subPre(ExceptionUtil.getMessage(ex), 500));
            } else {
                return Result.fail(code, ExceptionUtil.getSimpleMessage(ex));
            }
        } else if (ex instanceof BindException) {
            // hibernate 校验
            // @RequestParam 参数校验失败
            BindException exception = (BindException) ex;
            StringBuilder strBuilder = new StringBuilder();
            if (exception.getBindingResult().hasErrors()) {
                for (FieldError fieldErro : exception.getBindingResult().getFieldErrors()) {

                    if (fieldErro.isBindingFailure()) {
                        return Result.fail(String.format("参数 %s 非法", fieldErro.getField()));
                    }

                    if (strBuilder.length() > 0) {
                        // 多条信息空格隔开
                        strBuilder.append(" ");
                    }
                    strBuilder.append(fieldErro.getDefaultMessage());
                }
                if (strBuilder.length() > 0) {
                    log.error("ViolationException:{}", strBuilder);
                }
                return Result.fail(StatusCode.PARAM_NOT_VALID.getCode(), strBuilder.toString(), rootCauseMessage);
            }
        } else if (ex instanceof NoHandlerFoundException) {
            // 404 错误处理
            NoHandlerFoundException noHandlerFoundException = (NoHandlerFoundException) ex;
            return Result.fail(HttpStatus.NOT_FOUND.value(), String.format("%s 不存在", noHandlerFoundException.getRequestURL()), rootCauseMessage);
        } else if (ex instanceof IllegalArgumentException) {
            // assert 参数异常
            IllegalArgumentException illegalArgumentException = (IllegalArgumentException) ex;
            return Result.fail(StatusCode.PARAM_NOT_VALID.getCode(), illegalArgumentException.getMessage(), rootCauseMessage);
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            // 方法不支持
            HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) ex;
            String method = exception.getMethod();
            return Result.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), String.format("%s 方法不支持", method), rootCauseMessage);
        } else if (ex instanceof SaTokenException) {
            return ExceptionUtils.getResult(ex);
        } else if (ex instanceof HttpMessageNotReadableException) {
            return Result.fail(StatusCode.PARAM_NOT_VALID, rootCauseMessage);
        }
        return Result.fail(StatusCode.COMMON_EXCEPTION, rootCauseMessage);
    }
}
