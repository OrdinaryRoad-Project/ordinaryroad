package top.ordinaryroad.commons.core.base.result;


import java.io.Serializable;

/**
 * 结果接口
 *
 * @author mjz
 * @date 2021/9/3
 */
public interface IBaseResult<T> extends Serializable {

    /**
     * 返回code
     *
     * @return code
     */
    int getCode();

    /**
     * 得到消息
     *
     * @return msg
     */
    String getMsg();

    /**
     * 得到数据
     *
     * @return data
     */
    T getData();

    /**
     * 是否成功
     *
     * @return success
     */
    boolean getSuccess();

    /**
     * 异常详情
     *
     * @return 异常详情
     */
    String getDetails();

    void setCode(int code);

    void setMsg(String msg);

    void setSuccess(boolean success);

    void setData(T data);

    void setDetails(String details);
}
