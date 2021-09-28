package top.ordinaryroad.commons.core.base.exception;

/**
 * 批量删除失败
 *
 * @author mjz
 * @date 2021/9/9
 */
public class BatchDeleteException extends BaseException {

    private static final long serialVersionUID = 2003468869648113132L;

    public BatchDeleteException(String msg) {
        super(msg);
    }

}
