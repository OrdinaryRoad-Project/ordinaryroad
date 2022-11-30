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
package tech.ordinaryroad.commons.log.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.ordinaryroad.commons.mybatis.model.BaseDO;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * OR操作日志表
 *
 * @author mjz
 * @date 2022/11/24
 */
@Getter
@Setter
@ToString
@Table(name = "operation_log")
public class OperationLogDO extends BaseDO {

    private static final long serialVersionUID = -7052935939908928718L;

    /**
     * IP
     */
    @Column(updatable = false)
    private String ip;

    /**
     * 请求路径
     */
    @Column(updatable = false)
    private String path;

    /**
     * 请求方法
     */
    @Column(updatable = false)
    private String method;

    /**
     * 请求头
     */
    @Column(updatable = false)
    private String headers;

    /**
     * 请求Cookie
     */
    @Column(updatable = false)
    private String cookies;

    /**
     * 路径参数
     */
    @Column(updatable = false)
    private String pathParams;

    /**
     * 查询参数
     */
    @Column(updatable = false)
    private String queryParams;

    /**
     * 请求体
     */
    @Column(updatable = false)
    private String request;

    /**
     * 响应状态
     */
    @Column(updatable = false)
    private String status;

    /**
     * 响应头
     */
    @Column(updatable = false)
    private String responseHeaders;

    /**
     * 响应Cookie
     */
    @Column(updatable = false)
    private String responseCookies;

    /**
     * 响应体
     */
    @Column(updatable = false)
    private String response;

    /**
     * 耗时
     */
    @Column(updatable = false)
    private Long consumedTime;

    /**
     * 类型
     */
    @Column(updatable = false)
    private Integer type;

    /**
     * 是否删除
     */
    @LogicDelete
    @Column(updatable = false)
    private Boolean deleted;

}
