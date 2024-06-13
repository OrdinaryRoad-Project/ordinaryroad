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

package tech.ordinaryroad.commons.base.exception;


import tech.ordinaryroad.commons.base.cons.IStatusCode;
import tech.ordinaryroad.commons.base.cons.StatusCode;

import java.io.Serial;

/**
 * 异常基类
 *
 * @author mjz
 * @date 2021/9/4
 */
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -522952657202704068L;

    private IStatusCode statusCode = StatusCode.COMMON_EXCEPTION;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }

    public BaseException(String msg, IStatusCode errorCode) {
        super(msg);
        this.statusCode = errorCode;
    }

    public BaseException(IStatusCode errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode;
    }

    public BaseException(IStatusCode errorCode, Throwable throwable) {
        super(errorCode.getMessage(), throwable);
        this.statusCode = errorCode;
    }

    public BaseException(String msg, IStatusCode errorCode, Throwable throwable) {
        super(errorCode.getMessage() + ":" + msg, throwable);
        this.statusCode = errorCode;
    }

    public IStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(IStatusCode statusCode) {
        this.statusCode = statusCode;
    }

}
