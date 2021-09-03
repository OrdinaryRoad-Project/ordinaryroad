package top.ordinaryroad.commons.core.base.result;

import top.ordinaryroad.commons.core.base.cons.StatusCode;

/**
 * 请求结果
 *
 * @param <T> 数据类型
 * @author mjz
 * @date 2021/9/3
 */
public class Result<T> implements IBaseResult<T> {

    private static final long serialVersionUID = 6621884598477949769L;

    private int code;

    private String msg;

    private T data;

    private boolean success;

    private String details;

    public Result() {
    }

    public Result(boolean success) {
        this.success = success;
        this.code = success ? StatusCode.SUCCESS.getCode() : StatusCode.COMMON_FAIL.getCode();
        this.msg = success ? StatusCode.SUCCESS.getMessage() : StatusCode.COMMON_FAIL.getMessage();
    }

    public Result(boolean success, StatusCode statusCode) {
        this.success = success;
        this.code = success ? StatusCode.SUCCESS.getCode() : (statusCode == null ? StatusCode.COMMON_FAIL.getCode() : statusCode.getCode());
        this.msg = success ? StatusCode.SUCCESS.getMessage() : (statusCode == null ? StatusCode.COMMON_FAIL.getMessage() : statusCode.getMessage());
    }

    public Result(boolean success, T data) {
        this.success = success;
        this.code = success ? StatusCode.SUCCESS.getCode() : StatusCode.COMMON_FAIL.getCode();
        this.msg = success ? StatusCode.SUCCESS.getMessage() : StatusCode.COMMON_FAIL.getMessage();
        this.data = data;
    }

    public Result(boolean success, StatusCode statusCode, T data) {
        this.success = success;
        this.code = success ? StatusCode.SUCCESS.getCode() : (statusCode == null ? StatusCode.COMMON_FAIL.getCode() : statusCode.getCode());
        this.msg = success ? StatusCode.SUCCESS.getMessage() : (statusCode == null ? StatusCode.COMMON_FAIL.getMessage() : statusCode.getMessage());
        this.data = data;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public boolean getSuccess() {
        return success;
    }

    @Override
    public String getDetails() {
        return details;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static Result<?> success() {
        return new Result<>(true);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, data);
    }

    public static Result<?> fail() {
        return new Result<>(false);
    }

    public static Result<?> fail(StatusCode statusCode) {
        return new Result<>(false, statusCode);
    }

}
