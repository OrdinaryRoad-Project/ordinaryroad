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

package tech.ordinaryroad.commons.core.base.result;


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
