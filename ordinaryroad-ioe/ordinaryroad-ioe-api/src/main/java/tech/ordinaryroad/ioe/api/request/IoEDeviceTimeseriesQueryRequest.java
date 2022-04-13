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
package tech.ordinaryroad.ioe.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.core.base.request.BaseRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2022/3/29
 */
@Getter
@Setter
@ApiModel
public class IoEDeviceTimeseriesQueryRequest extends BaseRequest {

    private static final long serialVersionUID = -5553338680784586602L;

    @ApiModelProperty(required = true)
    private String id;

    @ApiModelProperty(required = true)
    private List<String> keys;

    @ApiModelProperty(required = true)
    private Long startTime;

    @ApiModelProperty(required = true)
    private Long endTime;

    private Long interval;

    @ApiModelProperty(value = "聚合", allowableValues = "MIN, MAX, AVG, SUM, COUNT, NONE")
    private String agg;

    @ApiModelProperty(value = "排序方向", allowableValues = "ASC, DESC")
    private String direction;

    private Integer limit;
    private Boolean useStrictDataTypes;
}
