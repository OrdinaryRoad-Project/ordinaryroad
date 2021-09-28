package top.ordinaryroad.commons.core.base.exception;

/**
 * @author mjz
 * @date 2021/9/6
 */
public class UtilException extends BaseException {

    public UtilException(Throwable throwable) {
        super(throwable);
    }

    public UtilException(String msg) {
        super(msg);
    }
}
