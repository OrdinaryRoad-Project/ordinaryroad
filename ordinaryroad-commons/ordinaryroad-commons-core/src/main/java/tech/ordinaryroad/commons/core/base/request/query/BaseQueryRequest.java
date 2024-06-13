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


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.core.base.request.BaseRequest;

import java.io.Serial;
import java.time.LocalDateTime;
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

    @Serial
    private static final long serialVersionUID = -6517407721981279173L;

    /**
     * 主键uuid
     */
    @Schema(title = "主键uuid")
    private String uuid;

    /**
     * 创建者OR账号
     */
    @Schema(title = "创建者OR账号")
    private String createBy;

    /**
     * 更新者OR账号
     */
    @Schema(title = "更新者OR账号")
    private String updateBy;

    /**
     * 主键uuid列表
     */
    @Schema(title = "主键uuid列表")
    private List<String> uuids;

    /**
     * 个数
     */
    @Schema(title = "个数")
    private Integer limit = 20;

    /**
     * 偏移量
     */
    @Schema(title = "偏移量")
    private Integer offset = 0;

    /**
     * 根据哪些字段排序
     */
    @Schema(title = "排序字段列表")
    private String[] sortBy;

    /**
     * 是否为降序排序
     */
    @Schema(title = "是否为降序排序")
    private Boolean[] sortDesc;

    /**
     * 起始时间
     */
    @Schema(title = "起始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(title = "结束时间")
    private LocalDateTime endTime;

}
