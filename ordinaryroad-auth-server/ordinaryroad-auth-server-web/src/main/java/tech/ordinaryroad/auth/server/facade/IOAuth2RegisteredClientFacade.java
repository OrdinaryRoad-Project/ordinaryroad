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
package tech.ordinaryroad.auth.server.facade;

import com.github.pagehelper.PageInfo;
import tech.ordinaryroad.auth.server.dto.OAuth2RegisteredClientDTO;
import tech.ordinaryroad.auth.server.request.OAuth2RegisteredClientQueryRequest;
import tech.ordinaryroad.auth.server.request.OAuth2RegisteredClientSaveRequest;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;

import java.util.List;

/**
 * @author mjz
 * @date 2022/1/14
 */
public interface IOAuth2RegisteredClientFacade {

    /**
     * 创建
     *
     * @param request Request
     * @return DTO
     */
    Result<OAuth2RegisteredClientDTO> create(OAuth2RegisteredClientSaveRequest request);

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
    Result<OAuth2RegisteredClientDTO> update(OAuth2RegisteredClientSaveRequest request);

    /**
     * 查询
     *
     * @param request Request
     * @return DTO
     */
    Result<OAuth2RegisteredClientDTO> findById(OAuth2RegisteredClientQueryRequest request);

    /**
     * 根据唯一列查询
     *
     * @param request Request
     * @return DTO
     */
    Result<OAuth2RegisteredClientDTO> findByUniqueColumn(OAuth2RegisteredClientQueryRequest request);

    /**
     * 查询所有
     *
     * @param request Request
     * @return List
     */
    Result<List<OAuth2RegisteredClientDTO>> findAll(OAuth2RegisteredClientQueryRequest request);

    /**
     * 分页查询所有
     *
     * @param request Request
     * @return Page
     */
    Result<PageInfo<OAuth2RegisteredClientDTO>> list(OAuth2RegisteredClientQueryRequest request);

}
