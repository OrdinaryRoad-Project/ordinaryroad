package top.ordinaryroad.commons.core.base.exception;

/**
 * 逻辑删除失败
 *
 * @author mjz
 * @date 2021/9/6
 */
public class LogicDeleteException extends BaseException {

    private static final long serialVersionUID = -1456861038963393029L;

    public LogicDeleteException(String msg) {
        super(msg);
    }

}
