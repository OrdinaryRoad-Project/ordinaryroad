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
import tech.ordinaryroad.auth.server.dto.OAuth2OpenidDTO;
import tech.ordinaryroad.auth.server.request.OAuth2OpenidQueryRequest;
import tech.ordinaryroad.auth.server.request.OAuth2OpenidSaveRequest;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;

import java.util.List;

/**
 * @author mjz
 * @date 2022/1/14
 */
public interface IOAuth2OpenidFacade {

    /**
     * 创建
     *
     * @param request Request
     * @return DTO
     */
    Result<OAuth2OpenidDTO> create(OAuth2OpenidSaveRequest request);

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
    Result<OAuth2OpenidDTO> update(OAuth2OpenidSaveRequest request);

    /**
     * 查询
     *
     * @param request Request
     * @return DTO
     */
    Result<OAuth2OpenidDTO> findById(OAuth2OpenidQueryRequest request);

    /**
     * 根据clientId和OR账号查询
     *
     * @param request Request
     * @return DTO
     */
    Result<OAuth2OpenidDTO> findByClientIdAndOrNumber(OAuth2OpenidQueryRequest request);

    /**
     * 根据clientId和Openid查询
     *
     * @param request Request
     * @return DTO
     */
    Result<OAuth2OpenidDTO> findByClientIdAndOpenid(OAuth2OpenidQueryRequest request);

    /**
     * 查询所有
     *
     * @param request Request
     * @return List
     */
    Result<List<OAuth2OpenidDTO>> findAll(OAuth2OpenidQueryRequest request);

    /**
     * 分页查询所有
     *
     * @param request Request
     * @return Page
     */
    Result<PageInfo<OAuth2OpenidDTO>> list(OAuth2OpenidQueryRequest request);

}
