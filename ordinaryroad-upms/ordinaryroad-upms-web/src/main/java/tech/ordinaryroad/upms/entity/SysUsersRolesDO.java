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
package tech.ordinaryroad.upms.entity;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.mybatis.model.BaseDO;

import javax.persistence.Table;

/**
 * 用户角色关联关系表
 *
 * @author mjz
 * @date 2021/11/3
 */
@Getter
@Setter
@Table(name = "sys_users_roles")
public class SysUsersRolesDO extends BaseDO {

    private static final long serialVersionUID = 8132681650981971837L;

    /**
     * 用户uuid
     */
    private String userUuid;

    /**
     * 角色uuid
     */
    private String roleUuid;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
