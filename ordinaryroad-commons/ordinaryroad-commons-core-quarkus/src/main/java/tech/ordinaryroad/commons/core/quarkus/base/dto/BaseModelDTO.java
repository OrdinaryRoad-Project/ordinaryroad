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
package tech.ordinaryroad.commons.core.quarkus.base.dto;

import lombok.Data;
import tech.ordinaryroad.commons.base.dto.BaseDTO;

import java.time.LocalDateTime;

/**
 * Model DTO抽象类，包含 {@code BaseDO} 的一些通用字段
 *
 * @author mjz
 * @date 2021/11/29
 */
@Data
public class BaseModelDTO extends BaseDTO {

    private static final long serialVersionUID = 5269406345998745414L;

    //    @SchemaProperty(name = "主键uuid")
    private String uuid;

    //    @SchemaProperty(name = "创建时间")
    private LocalDateTime createdTime;

    //    @SchemaProperty(name = "创建者OR账号")
    private String createBy;

    //    @SchemaProperty(name = "更新时间")
    private LocalDateTime updateTime;

    //    @SchemaProperty(name = "更新者OR账号")
    private String updateBy;

}
