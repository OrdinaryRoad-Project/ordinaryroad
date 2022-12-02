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
import tech.ordinaryroad.commons.core.base.dto.BaseModelDTO;

/**
 * @author mjz
 * @date 2022/11/30
 */
@Data
@ApiModel
public class OperationLogDTO extends BaseModelDTO {

    private static final long serialVersionUID = -2175243628914642534L;

    @ApiModelProperty("IP")
    private String ip;

    @ApiModelProperty("请求路径")
    private String path;

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("请求头")
    private String headers;

    @ApiModelProperty("请求Cookie")
    private String cookies;

    @ApiModelProperty("路径参数")
    private String pathParams;

    @ApiModelProperty("查询参数")
    private String queryParams;

    @ApiModelProperty("请求体")
    private String request;

    @ApiModelProperty("响应状态")
    private String status;

    @ApiModelProperty("响应头")
    private String responseHeaders;

    @ApiModelProperty("响应Cookie")
    private String responseCookies;

    @ApiModelProperty("响应体")
    private String response;

    @ApiModelProperty("耗时")
    private Long consumedTime;

    @ApiModelProperty("类型")
    private Integer type;

}
