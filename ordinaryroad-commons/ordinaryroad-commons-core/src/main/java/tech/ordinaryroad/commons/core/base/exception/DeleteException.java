package tech.ordinaryroad.commons.core.base.exception;

/**
 * 删除失败
 *
 * @author mjz
 * @date 2021/9/9
 */
public class DeleteException extends BaseException {

    private static final long serialVersionUID = -5746564229957308928L;

    public DeleteException(String msg) {
        super(msg);
    }

}
