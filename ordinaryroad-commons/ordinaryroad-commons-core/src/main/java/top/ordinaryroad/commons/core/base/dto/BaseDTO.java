package top.ordinaryroad.commons.core.base.dto;

import com.alibaba.fastjson.JSON;

/**
 * DTO抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
public class BaseDTO implements IBaseDTO {

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
