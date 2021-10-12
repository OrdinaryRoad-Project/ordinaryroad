package top.ordinaryroad.commons.core.base.dto;

import com.alibaba.fastjson.JSON;

/**
 * DTO抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
public class BaseDTO implements IBaseDTO {

    private static final long serialVersionUID = 1127342762307994571L;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
