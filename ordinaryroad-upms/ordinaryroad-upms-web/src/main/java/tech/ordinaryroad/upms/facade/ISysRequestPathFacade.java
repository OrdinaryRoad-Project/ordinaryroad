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
package tech.ordinaryroad.upms.facade;

import com.github.pagehelper.PageInfo;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.dto.SysRequestPathDTO;
import tech.ordinaryroad.upms.request.SysRequestPathQueryRequest;
import tech.ordinaryroad.upms.request.SysRequestPathSaveRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2021/11/8
 */
public interface ISysRequestPathFacade {
    /**
     * 创建
     *
     * @param request Request
     * @return DTO
     */
    Result<SysRequestPathDTO> create(SysRequestPathSaveRequest request);

    /**
     * 删除
     *
     * @param request Request
     * @return DTO
     */
    Result<Boolean> delete(BaseDeleteRequest request);

    /**
     * 更新
     *
     * @param request Request
     * @return DTO
     */
    Result<SysRequestPathDTO> update(SysRequestPathSaveRequest request);

    /**
     * 查询
     *
     * @param request Request
     * @return DTO
     */
    Result<SysRequestPathDTO> findById(SysRequestPathQueryRequest request);

    /**
     * 根据唯一主键查询
     *
     * @param request Request
     * @return Result
     */
    Result<SysRequestPathDTO> findByUniqueColumn(SysRequestPathQueryRequest request);

    /**
     * 根据主键列表查询
     *
     * @param request Request
     * @return DTO
     */
    Result<List<SysRequestPathDTO>> findAllByIds(BaseQueryRequest request);

    /**
     * 查询所有
     *
     * @param request Request
     * @return List
     */
    Result<List<SysRequestPathDTO>> findAll(SysRequestPathQueryRequest request);

    /**
     * 分页查询所有
     *
     * @param request Request
     * @return Page
     */
    Result<PageInfo<SysRequestPathDTO>> list(SysRequestPathQueryRequest request);

    /**
     * 根据用户uuid查询所有请求路径
     *
     * @param request Request
     * @return List
     */
    Result<List<SysRequestPathDTO>> findAllByPermissionUuids(SysRequestPathQueryRequest request);

    /**
     * 根据用户uuid查询所有请求路径
     *
     * @param request Request
     * @return List
     */
    Result<List<SysRequestPathDTO>> findAllByUserUuid(SysRequestPathQueryRequest request);
}
