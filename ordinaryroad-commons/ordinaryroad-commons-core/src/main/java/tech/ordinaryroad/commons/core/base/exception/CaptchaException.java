package tech.ordinaryroad.commons.core.base.exception;

/**
 * 验证码错误异常类
 *
 * @author ruoyi
 */
public class CaptchaException extends BaseException {

    private static final long serialVersionUID = 9036656405436182436L;

    public CaptchaException(String msg) {
        super(msg);
    }

}
