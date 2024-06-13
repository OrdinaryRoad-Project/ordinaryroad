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

import io.mybatis.provider.Entity;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.mybatis.model.BaseDO;

import java.io.Serial;


/**
 * 用户表
 *
 * @author mjz
 * @date 2021/10/27
 */
@Getter
@Setter
@Entity.Table("sys_user")
public class SysUserDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1213747147371494092L;

    /**
     * or帐号
     */
    @Entity.Column
    private String orNumber;
    /**
     * 头像地址
     */
    @Entity.Column
    private String avatar;
    /**
     * 邮箱
     */
    @Entity.Column
    private String email;
    /**
     * 用户名
     */
    @Entity.Column
    private String username;
    /**
     * 用户密码
     */
    @Entity.Column
    private String password;
    /**
     * 账号是否可用。默认为1（可用）
     */
    @Entity.Column
    private Boolean enabled;
    /**
     * 是否过期。默认为1（没有过期）
     */
    @Entity.Column
    private Boolean notExpired;
    /**
     * 账号是否锁定。默认为1（没有锁定）
     */
    @Entity.Column
    private Boolean notLocked;
    /**
     * 密码是否过期。默认为1（没有过期）
     */
    @Entity.Column
    private Boolean passwordNotExpired;

}
