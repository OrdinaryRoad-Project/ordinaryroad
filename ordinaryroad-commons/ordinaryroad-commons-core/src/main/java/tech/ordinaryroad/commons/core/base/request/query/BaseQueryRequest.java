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

package tech.ordinaryroad.commons.core.base.request.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.core.base.request.BaseRequest;

import java.util.List;

/**
 * 查询请求抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
public class BaseQueryRequest extends BaseRequest {

    private static final long serialVersionUID = -3803036361359796031L;

    /**
     * 主键uuid
     */
    @ApiModelProperty("主键uuid")
    private String uuid;

    /**
     * 主键uuid列表
     */
    @ApiModelProperty("主键uuid列表")
    private List<String> uuids;

    /**
     * 个数
     */
    @ApiModelProperty("个数")
    private Integer limit = 20;

    /**
     * 偏移量
     */
    @ApiModelProperty("偏移量")
    private Integer offset = 0;

    /**
     * 升序字段列表
     */
    @ApiModelProperty("升序字段列表")
    private String[] orderBy;

    /**
     * 降序字段列表
     */
    @ApiModelProperty("降序字段列表")
    private String[] orderByDesc;

}
