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

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author mjz
 * @date 2022/4/10
 */
@Getter
@Setter
@ApiModel
public class IoEAlarmDataQueryRequest extends BaseIoEDataQueryRequest {

    private static final long serialVersionUID = -2161000370703943911L;

    @ApiModelProperty("开始时间")
    private Long startTs = null;

    @ApiModelProperty("结束时间")
    private Long endTs = null;

    private Long timeWindow = null;

    @ApiModelProperty("类型列表")
    private List<String> typeList;

    @ApiModelProperty(value = "状态列表", allowableValues = "ANY, ACTIVE, CLEARED, ACK, UNACK")
    @NotEmpty(message = "状态列表不能为空")
    private List<String> searchStatusList;

    @ApiModelProperty(value = "严重性列表", allowableValues = "CRITICAL, MAJOR, MINOR, WARNING, INDETERMINATE")
    private List<String> severityList;

    @ApiModelProperty("搜索传播警报")
    private Boolean searchPropagatedAlarms;

}


