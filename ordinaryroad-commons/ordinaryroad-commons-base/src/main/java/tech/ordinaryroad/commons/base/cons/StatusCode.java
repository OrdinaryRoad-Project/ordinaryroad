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

package tech.ordinaryroad.commons.base.cons;

/**
 * 状态码枚举类
 *
 * @author mjz
 * @date 2021/9/3
 */
public enum StatusCode implements IStatusCode {

    /* 成功 */
    SUCCESS(200, "成功"),

    /* 默认异常 */
    COMMON_EXCEPTION(900, "系统异常"),
    /* 默认失败 */
    COMMON_FAIL(901, "操作失败"),
    DEMO_MODE_FAIL(902, "演示模式不允许操作"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误：2001～2999 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),
    USERNAME_ALREADY_EXIST(2010, "用户名已存在"),
    EMAIL_ALREADY_EXIST(2011, "邮箱已存在"),

    /* 接口异常：3001～3999 */
    NO_PERMISSION(3001, "没有权限"),
    NO_ROLE(3002, "没有角色"),

    /* 业务错误：4001～4999 */
    DATA_NOT_EXIST(4001, "数据不存在"),
    DATA_ALREADY_EXIST(4002, "数据已存在"),
    CODE_NOT_EXIST(4003, "验证码失效"),
    CODE_WRONG(4004, "验证码错误"),
    UUID_ALREADY_EXIST(4005, "UUID已存在"),
    NAME_ALREADY_EXIST(4006, "名称已存在"),
    CODE_ALREADY_EXIST(4007, "code已存在"),
    URL_ALREADY_EXIST(4008, "请求路径url已存在"),
    USER_NOT_EXIST(4009, "用户不存在"),
    ROLE_NOT_EXIST(4010, "角色不存在"),
    PATH_ALREADY_EXIST(4011, "路径已存在"),
    PERMISSION_NOT_EXIST(4012, "权限不存在"),
    PATH_NAME_ALREADY_EXIST(4013, "路径名称已存在"),
    DICT_NOT_EXIST(4014, "字典不存在"),
    LABEL_ALREADY_EXIST(4015, "标签已存在"),
    VALUE_ALREADY_EXIST(4016, "值已存在"),
    OPENID_ALREADY_EXIST(4017, "openid已存在"),
    CLIENT_ID_ALREADY_EXIST(4018, "clientId已存在"),
    CLIENT_NAME_ALREADY_EXIST(4019, "clientName已存在"),
    DATA_OUTDATED(4020, "数据已过期"),
    PATH_NOT_EXIST(4021, "路径不存在"),
    DICT_ITEM_NOT_EXIST(4022, "字典项不存在"),
    ;

    /**
     * 编码
     */
    private final int code;

    /**
     * 描述
     */
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
