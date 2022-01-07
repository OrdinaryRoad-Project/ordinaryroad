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
package tech.ordinaryroad.upms.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.core.base.request.save.BaseSaveRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author mjz
 * @date 2022/1/5
 */
@Getter
@Setter
@ApiModel
public class SysDictItemSaveRequest extends BaseSaveRequest {

    private static final long serialVersionUID = 7822992415286092761L;

    @ApiModelProperty("字典uuid")
    @NotBlank(message = "字典uuid不能为空")
    @Size(max = 32, message = "字典uuid长度不能超过32")
    private String dictUuid;

    @ApiModelProperty("显示标签")
    @NotBlank(message = "显示标签不能为空")
    @Size(max = 50, message = "显示标签长度不能超过50")
    private String label;

    @ApiModelProperty("值")
    @NotBlank(message = "值不能为空")
    @Size(max = 10, message = "值长度不能超过10")
    private String value;

    @ApiModelProperty("显示排序")
    private Integer sort;

    @ApiModelProperty("备注")
    private String remark;
}
