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
package tech.ordinaryroad.commons.core.utils.exception;

import cn.dev33.satoken.exception.*;
import cn.dev33.satoken.oauth2.exception.SaOAuth2Exception;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.result.Result;

/**
 * @author mjz
 * @date 2021/12/7
 */
public class ExceptionUtils {

    public static Result<?> getResult(Throwable throwable) {
        if (throwable instanceof Exception) {
            return getResult((Exception) throwable);
        } else {
            return Result.fail(StatusCode.COMMON_FAIL.getCode(), throwable.getMessage());
        }
    }

    public static Result<?> getResult(Exception e) {
        if (e instanceof SaTokenException) {
            return getResult((SaTokenException) e);
        } else {
            return Result.fail(StatusCode.COMMON_FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 解析SaTokenException，返回Result
     *
     * @param saTokenException SaTokenException
     * @return Result
     */
    public static Result<?> getResult(SaTokenException saTokenException) {
        String simpleMessage = StrUtil.subPre(ExceptionUtil.getSimpleMessage(saTokenException), 500);
        if (saTokenException instanceof NotPermissionException) {
            return Result.fail(StatusCode.NO_PERMISSION.getCode(), simpleMessage);
        } else if (saTokenException instanceof NotRoleException) {
            return Result.fail(StatusCode.NO_ROLE.getCode(), simpleMessage);
        } else if (saTokenException instanceof NotLoginException) {
            return Result.fail(StatusCode.USER_NOT_LOGIN.getCode(), simpleMessage);
        } else if (saTokenException instanceof DisableLoginException) {
            return Result.fail(StatusCode.USER_ACCOUNT_DISABLE.getCode(), simpleMessage);
        } else if (saTokenException instanceof SaOAuth2Exception) {
            return Result.fail(StatusCode.COMMON_EXCEPTION.getCode(), simpleMessage);
        }
        return Result.fail(StatusCode.COMMON_EXCEPTION.getCode(), simpleMessage);
    }

}
