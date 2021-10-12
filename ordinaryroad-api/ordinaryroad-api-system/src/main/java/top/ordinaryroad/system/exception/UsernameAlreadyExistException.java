package top.ordinaryroad.system.exception;

import top.ordinaryroad.commons.core.base.cons.StatusCode;
import top.ordinaryroad.commons.core.base.exception.BaseException;

/**
 * @author mjz
 * @date 2021/9/13
 */
public class UsernameAlreadyExistException extends BaseException {

    private static final long serialVersionUID = 7271096722020719646L;

    public UsernameAlreadyExistException() {
        super(StatusCode.USERNAME_ALREADY_EXIST);
    }

}
