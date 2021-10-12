package top.ordinaryroad.system.constant;

import top.ordinaryroad.commons.core.base.cons.IStatusCode;

/**
 * @author mjz
 * @date 2021/9/13
 */
public enum ModulesSystemStatusCode implements IStatusCode {

    /**
     * 用户名已存在
     */
    USERNAME_ALREADY_EXIST(9401, "用户名已存在");
    /**
     * 编码
     */
    private final int code;

    /**
     * 描述
     */
    private final String message;

    ModulesSystemStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
