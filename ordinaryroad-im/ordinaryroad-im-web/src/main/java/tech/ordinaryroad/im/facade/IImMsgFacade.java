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

package tech.ordinaryroad.im.facade;

import com.github.pagehelper.PageInfo;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.im.dto.ImMsgDTO;
import tech.ordinaryroad.im.request.*;

/**
 * @author mjz
 * @date 2022/1/21
 */
public interface IImMsgFacade {

    /**
     * 创建
     *
     * @param request Request
     * @return DTO
     */
    Result<ImMsgDTO> create(ImMsgSaveRequest request);

    /**
     * 删除
     *
     * @param request Request
     * @return DTO
     */
    Result<Boolean> delete(BaseDeleteRequest request);

    /**
     * 读取消息
     *
     * @param request Request
     * @return Boolean
     */
    Result<Boolean> read(ImMsgReadRequest request);

    /**
     * 撤回消息
     *
     * @param request Request
     * @return Boolean
     */
    Result<Boolean> recall(ImMsgRecallRequest request);

    /**
     * 根据唯一主键查询
     *
     * @param request Request
     * @return DTO
     */
    Result<ImMsgDTO> findByUniqueColumn(ImMsgQueryRequest request);

    /**
     * 查询
     *
     * @param request Request
     * @return DTO
     */
    Result<ImMsgDTO> findById(ImMsgQueryRequest request);

    /**
     * 查询自己和他人的历史聊天记录
     *
     * @param request Request
     * @return Page
     */
    Result<PageInfo<ImMsgDTO>> history(ImMsgHistoryRequest request);

    /**
     * 分页查询所有
     *
     * @param request Request
     * @return Page
     */
    Result<PageInfo<ImMsgDTO>> list(ImMsgQueryRequest request);

}
