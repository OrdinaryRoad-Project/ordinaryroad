package tech.ordinaryroad.commons.core.base.exception;


import tech.ordinaryroad.commons.core.base.cons.IStatusCode;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;

/**
 * 异常基类
 *
 * @author mjz
 * @date 2021/9/4
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -522952657202704068L;

    private IStatusCode statusCode = StatusCode.COMMON_EXCEPTION;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }

    public BaseException(String msg, IStatusCode errorCode) {
        super(msg);
        this.statusCode = errorCode;
    }

    public BaseException(IStatusCode errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode;
    }

    public BaseException(IStatusCode errorCode, Throwable throwable) {
        super(errorCode.getMessage(), throwable);
        this.statusCode = errorCode;
    }

    public BaseException(String msg, IStatusCode errorCode, Throwable throwable) {
        super(errorCode.getMessage() + ":" + msg, throwable);
        this.statusCode = errorCode;
    }

    public IStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(IStatusCode statusCode) {
        this.statusCode = statusCode;
    }

}
