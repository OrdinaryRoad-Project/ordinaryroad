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
package tech.ordinaryroad.ioe.facade.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.query.AlarmData;
import org.thingsboard.server.common.data.query.AlarmDataPageLink;
import org.thingsboard.server.common.data.security.DeviceCredentials;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.thingsboard.service.OrThingsBoardDeviceService;
import tech.ordinaryroad.ioe.api.dto.IoEAlarmDataDTO;
import tech.ordinaryroad.ioe.api.dto.IoEDeviceCredentialsDTO;
import tech.ordinaryroad.ioe.api.dto.IoEDeviceDTO;
import tech.ordinaryroad.ioe.api.request.IoEAlarmDataQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEDeviceAlarmDataQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEDeviceDeleteRequest;
import tech.ordinaryroad.ioe.api.request.IoEDeviceSaveRequest;
import tech.ordinaryroad.ioe.facade.IIoEDeviceFacade;
import tech.ordinaryroad.ioe.mapstruct.IoEAlarmMapStruct;
import tech.ordinaryroad.ioe.mapstruct.IoEDeviceMapStruct;
import tech.ordinaryroad.ioe.service.IoEService;
import tech.ordinaryroad.ioe.utis.IoEUtils;

import java.util.Optional;

/**
 * @author mjz
 * @date 2022/3/27
 */
@RequiredArgsConstructor
@Component
public class IoEDeviceFacadeImpl implements IIoEDeviceFacade {

    private final IoEService ioEService;
    private final OrThingsBoardDeviceService thingsBoardDeviceService;
    private final IoEDeviceMapStruct mapStruct;
    private final IoEAlarmMapStruct alarmMapStruct;

    @Override
    public Result<IoEDeviceDTO> create(IoEDeviceSaveRequest request) {
        final String deviceId = request.getDeviceId();
        final String customerId = ioEService.getCustomerId();
        final Optional<Device> optional = thingsBoardDeviceService.assignDeviceToCustomer(customerId, deviceId);
        return optional.map(device -> Result.success(mapStruct.transfer(device))).orElse(Result.fail());
    }

    @Override
    public Result<IoEDeviceDTO> delete(IoEDeviceDeleteRequest request) {
        final String deviceId = request.getDeviceId();
        final Optional<Device> optional = thingsBoardDeviceService.unassignDeviceFromCustomer(deviceId);
        return optional.map(device -> Result.success(mapStruct.transfer(device))).orElse(Result.fail());
    }

    @Override
    public Result<IoEDeviceCredentialsDTO> credentials(String id) {
        final Optional<DeviceCredentials> optional = thingsBoardDeviceService.getDeviceCredentials(id);
        return optional.map(deviceCredentials -> Result.success(mapStruct.transfer(deviceCredentials))).orElse(Result.fail());
    }

    @Override
    public Result<PageInfo<IoEAlarmDataDTO>> alarms(IoEDeviceAlarmDataQueryRequest request) {
        final AlarmDataPageLink alarmDataPageLink = IoEUtils.requestToAlarmDataPageLink(request);

        final PageData<AlarmData> deviceAlarmDataByQuery = thingsBoardDeviceService.findDeviceAlarmDataByQuery(request.getDeviceId(), alarmDataPageLink);
        final PageInfo<IoEAlarmDataDTO> pageInfo = IoEUtils.pageDataToPageInfo(alarmDataPageLink.getPage() + 1, alarmDataPageLink.getPageSize(), deviceAlarmDataByQuery, alarmMapStruct::transfer);

        return Result.success(pageInfo);
    }

    @Override
    public Result<PageInfo<IoEAlarmDataDTO>> allAlarms(IoEAlarmDataQueryRequest request) {
        final AlarmDataPageLink alarmDataPageLink = IoEUtils.requestToAlarmDataPageLink(request);

        final PageData<AlarmData> deviceAlarmDataByQuery = thingsBoardDeviceService.findAllDeviceAlarmDataByQuery(ioEService.getUserId(), alarmDataPageLink);
        final PageInfo<IoEAlarmDataDTO> pageInfo = IoEUtils.pageDataToPageInfo(alarmDataPageLink.getPage() + 1, alarmDataPageLink.getPageSize(), deviceAlarmDataByQuery, alarmMapStruct::transfer);

        return Result.success(pageInfo);
    }

    @Override
    public Result<Boolean> saveAttributes(String deviceId, String scope, JsonNode request) {
        boolean success = thingsBoardDeviceService.saveDeviceAttributes(deviceId, scope, request);
        if (success) {
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    @Override
    public Result<IoEDeviceDTO> updateLabel(String deviceId, String label) {
        Device device = thingsBoardDeviceService.updateDeviceLabel(deviceId, label);
        if (device == null) {
            return Result.fail();
        } else {
            return Result.success(mapStruct.transfer(device));
        }
    }

}
