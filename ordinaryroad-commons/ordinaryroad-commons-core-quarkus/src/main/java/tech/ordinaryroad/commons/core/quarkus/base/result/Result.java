/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.ordinaryroad.commons.core.quarkus.base.result;


import com.alibaba.fastjson2.JSON;
import tech.ordinaryroad.commons.base.cons.IStatusCode;
import tech.ordinaryroad.commons.base.cons.StatusCode;

/**
 * 请求结果
 *
 * @param <T> 数据类型
 * @author mjz
 * @date 2021/9/3
 */
public class Result<T> implements IBaseResult<T> {

    private static final long serialVersionUID = -4855562186962805808L;

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

    public Result(boolean success, IStatusCode statusCode) {
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

    public Result(boolean success, IStatusCode statusCode, T data) {
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

    public static <T> Result<T> success() {
        return new Result<>(true);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(false);
    }

    public static <T> Result<T> fail(IStatusCode statusCode) {
        return new Result<>(false, statusCode);
    }

    public static <T> Result<T> fail(int code, String msg) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> fail(int code, String msg, String details) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        result.setDetails(details);
        return result;
    }

    public static <T> Result<T> fail(IStatusCode statusCode, String details) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(statusCode.getCode());
        result.setMsg(statusCode.getMessage());
        result.setDetails(details);
        return result;
    }

    public static <T> Result<T> fail(String msg) {
        return fail(StatusCode.COMMON_FAIL.getCode(), msg);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static <T> Result<T> parse(String jsonString) {
        return JSON.parseObject(jsonString, Result.class);
    }

}
