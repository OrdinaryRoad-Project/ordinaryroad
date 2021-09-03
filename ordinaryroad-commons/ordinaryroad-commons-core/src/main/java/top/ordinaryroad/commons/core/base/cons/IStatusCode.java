package top.ordinaryroad.commons.core.base.cons;

/**
 * 状态码接口
 *
 * @author mjz
 * @date 2021/9/3
 */
public interface IStatusCode {

    /**
     * 状态码
     *
     * @return code
     */
    int getCode();

    /**
     * 信息
     *
     * @return message
     */
    String getMessage();
}
