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
package tech.ordinaryroad.upms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.ordinaryroad.commons.core.base.dto.BaseModelDTO;

/**
 * @author mjz
 * @date 2022/1/5
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
@ApiModel
public class SysDictItemDTO extends BaseModelDTO {

    private static final long serialVersionUID = 2307479291526597256L;

    @EqualsAndHashCode.Include
    @ApiModelProperty("字典uuid")
    private String dictUuid;

    @EqualsAndHashCode.Include
    @ApiModelProperty("显示标签")
    private String label;

    @EqualsAndHashCode.Include
    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("显示排序")
    private Integer sort;

    @ApiModelProperty("备注")
    private String remark;
}
