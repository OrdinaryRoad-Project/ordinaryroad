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
package tech.ordinaryroad.ioe.facade;

import com.github.pagehelper.PageInfo;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.ioe.api.dto.IoEDeviceInfoDTO;
import tech.ordinaryroad.ioe.api.request.IoEDeviceInfoQueryRequest;

/**
 * @author mjz
 * @date 2022/3/26
 */
public interface IIoEDeviceInfoFacade {

    /**
     * 根据Id查询设备信息
     *
     * @param id 设备Id
     * @return DTO
     */
    Result<IoEDeviceInfoDTO> findById(String id);

    /**
     * 分页查询用户拥有的设备列表
     *
     * @param request Request
     * @return Page
     */
    Result<PageInfo<IoEDeviceInfoDTO>> list(IoEDeviceInfoQueryRequest request);

}
