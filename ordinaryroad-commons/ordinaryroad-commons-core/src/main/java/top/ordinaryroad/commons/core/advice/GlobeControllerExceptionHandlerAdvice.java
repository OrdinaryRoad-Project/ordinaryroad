package top.ordinaryroad.commons.core.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import top.ordinaryroad.commons.core.base.cons.StatusCode;
import top.ordinaryroad.commons.core.base.exception.BaseException;
import top.ordinaryroad.commons.core.base.result.Result;
import top.ordinaryroad.commons.core.utils.ExceptionUtil;
import top.ordinaryroad.commons.core.utils.StringUtils;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class GlobeControllerExceptionHandlerAdvice {

    /**
     * 全局异常处理，异常返回统一处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result<?> exceptionHandler(HttpServletResponse response, Exception ex) {
        return excepHandler(response, ex);
    }

    /**
     * exception 处理
     *
     * @param ex
     * @return
     */
    private Result<?> excepHandler(HttpServletResponse response, Exception ex) {
        String rootErrorMessage = ExceptionUtil.getRootErrorMessage(ex);
        /*
        String rootErrorMessage = ExceptionUtil.getRootErrorMessage(ex);
        String exceptionMessage = ExceptionUtil.getExceptionMessage(ex);
        log.error("rootError: {}", rootErrorMessage);
        log.error("exception: {}", exceptionMessage);

        String rootCauseMessage = ExceptionUtil.getRootCauseMessage(ex);
        String message = ExceptionUtil.getMessage(ex);
        log.error("rootCause: {}", rootCauseMessage);
        log.error("message: {}", message);
        */
        if (ex instanceof BaseException) {
            BaseException bizEx = (BaseException) ex;
            int code = 0;
            if (bizEx.getStatusCode() != null && bizEx.getStatusCode().getCode() != 0) {
                code = bizEx.getStatusCode().getCode();
            }
            String bizExMessage = bizEx.getMessage();
//            log.error("traceId：{}", MDC.get("traceId"));
            if (StringUtils.isNotBlank(bizExMessage)) {
                return Result.fail(code, bizExMessage, StringUtils.substringWithDots(ExceptionUtil.getExceptionMessage(ex)));
            } else {
                return Result.fail(code, rootErrorMessage);
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
                return Result.fail(StatusCode.PARAM_NOT_VALID.getCode(), strBuilder.toString(), rootErrorMessage);
            }
        } else if (ex instanceof NoHandlerFoundException) {
            // 404 错误处理
            NoHandlerFoundException noHandlerFoundException = (NoHandlerFoundException) ex;
            return Result.fail(HttpStatus.NOT_FOUND.value(), String.format("%s 不存在", noHandlerFoundException.getRequestURL()), rootErrorMessage);
        } else if (ex instanceof IllegalArgumentException) {
            // assert 参数异常
            IllegalArgumentException illegalArgumentException = (IllegalArgumentException) ex;
            return Result.fail(StatusCode.PARAM_NOT_VALID.getCode(), illegalArgumentException.getMessage(), rootErrorMessage);
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            // 方法不支持
            HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) ex;
            String method = exception.getMethod();
            return Result.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), String.format("%s 方法不支持", method), rootErrorMessage);
        } else if (ex instanceof HttpMessageNotReadableException) {
            return Result.fail(StatusCode.PARAM_NOT_VALID, rootErrorMessage);
        }
        return Result.fail(StatusCode.COMMON_EXCEPTION, rootErrorMessage);
    }

}	
 