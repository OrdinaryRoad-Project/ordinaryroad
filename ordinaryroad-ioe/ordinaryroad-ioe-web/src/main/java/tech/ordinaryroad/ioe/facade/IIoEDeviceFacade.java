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

import com.fasterxml.jackson.databind.JsonNode;
import com.github.pagehelper.PageInfo;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.ioe.api.dto.IoEAlarmDataDTO;
import tech.ordinaryroad.ioe.api.dto.IoEDeviceCredentialsDTO;
import tech.ordinaryroad.ioe.api.dto.IoEDeviceDTO;
import tech.ordinaryroad.ioe.api.request.IoEAlarmDataQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEDeviceAlarmDataQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEDeviceDeleteRequest;
import tech.ordinaryroad.ioe.api.request.IoEDeviceSaveRequest;

/**
 * @author mjz
 * @date 2022/3/27
 */
public interface IIoEDeviceFacade {

    /**
     * 创建设备
     *
     * @param request Request
     * @return DTO
     */
    Result<IoEDeviceDTO> create(IoEDeviceSaveRequest request);

    /**
     * 删除设备
     *
     * @param request Request
     * @return DTO
     */
    Result<IoEDeviceDTO> delete(IoEDeviceDeleteRequest request);

    /**
     * 查询设备凭证
     *
     * @param id 设备Id
     * @return DTO
     */
    Result<IoEDeviceCredentialsDTO> credentials(String id);

    /**
     * 分页查询设备告警数据
     *
     * @param request Request
     * @return PageIndo
     */
    Result<PageInfo<IoEAlarmDataDTO>> alarms(IoEDeviceAlarmDataQueryRequest request);

    /**
     * 分页查询所有设备的告警数据
     *
     * @param request Request
     * @return PageIndo
     */
    Result<PageInfo<IoEAlarmDataDTO>> allAlarms(IoEAlarmDataQueryRequest request);

    /**
     * 保存设备属性
     *
     * @param deviceId 设备Id
     * @param scope    范围
     * @param request  属性
     * @return Result
     */
    Result<Boolean> saveAttributes(String deviceId, String scope, JsonNode request);

    /**
     * 更新设备标签
     *
     * @param deviceId 设备Id
     * @param label    标签
     * @return DTO
     */
    Result<IoEDeviceDTO> updateLabel(String deviceId, String label);

}
