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

package tech.ordinaryroad.commons.core.quarkus.base.request.query;

import lombok.Getter;
import lombok.Setter;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import tech.ordinaryroad.commons.core.quarkus.base.request.BaseRequest;

import javax.ws.rs.DefaultValue;
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

    private static final long serialVersionUID = -1218778042472018859L;

    /**
     * 创建者Id
     */
//    @SchemaProperty(name = "创建者OR账号")
    @QueryParam
    private String createBy;

    /**
     * 更新者Id
     */
//    @SchemaProperty(name = "更新者OR账号")
    @QueryParam
    private String updateBy;

    /**
     * 每页显示条数
     */
//    @SchemaProperty(name = "个数")
//    @Range(min = 1, max = 1000, message = "个数范围为1-1000")
//    @DefaultValue("20")
//    @DecimalMax(value = "1000", message = "个数最大为1000")
    @DefaultValue("20")
    @PathParam
    private Long size = 20L;

    /**
     * 页码
     */
//    @SchemaProperty(name = "偏移量")
//    @Min(value = 0, message = "偏移量最小为0")
//    @DefaultValue("0")
    @PathParam
    private Long page = 1L;

    /**
     * 升序字段列表
     */
//    @SchemaProperty(name = "升序字段列表")
    @QueryParam
    private List<String> orderBy;

    /**
     * 降序字段列表
     */
//    @SchemaProperty(name = "降序字段列表")
    @QueryParam
    private List<String> orderByDesc;

    /**
     * 起始时间
     */
//    @SchemaProperty(name = "起始时间")
    @QueryParam
    private Long startTime;

    /**
     * 结束时间
     */
//    @SchemaProperty(name = "结束时间")
    @QueryParam
    private Long endTime;

}
