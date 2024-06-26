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
import lombok.ToString;
import tech.ordinaryroad.commons.mybatis.model.BaseDO;

import java.io.Serial;


/**
 * 字典表
 *
 * @author mjz
 * @date 2022/1/5
 */
@Getter
@Setter
@ToString
@Entity.Table( "sys_dict")
public class SysDictDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 4439289501566187991L;

    /**
     * 字典名称
     */
    @Entity.Column
    private String dictName;

    /**
     * 字典code
     */
    @Entity.Column
    private String dictCode;

    /**
     * 备注
     */
    @Entity.Column
    private String remark;

}
