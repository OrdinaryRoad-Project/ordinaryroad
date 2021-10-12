package top.ordinaryroad.commons.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ordinaryroad.commons.core.base.cons.StatusCode;
import top.ordinaryroad.commons.core.base.result.Result;
import top.ordinaryroad.commons.core.utils.ExceptionUtil;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class SecurityControllerExceptionHandlerAdvice {

    /**
     * 全局异常处理，异常返回统一处理
     */
    @ResponseBody
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result<?> exceptionHandler(HttpServletResponse response, AccessDeniedException ex) {
        return excepHandler(response, ex);
    }

    /**
     * exception 处理
     *
     * @param ex
     * @return
     */
    private Result<?> excepHandler(HttpServletResponse response, AccessDeniedException ex) {
        String rootErrorMessage = ExceptionUtil.getRootErrorMessage(ex);
        if (ex instanceof AccessDeniedException) {
            // 没有权限
            return Result.fail(StatusCode.NO_PERMISSION, rootErrorMessage);
        }
        return Result.fail(StatusCode.COMMON_EXCEPTION, rootErrorMessage);
    }

}	
 