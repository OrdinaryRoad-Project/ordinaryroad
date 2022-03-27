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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.Device;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.thingsboard.service.OrThingsBoardDeviceService;
import tech.ordinaryroad.ioe.api.dto.IoEDeviceDTO;
import tech.ordinaryroad.ioe.api.request.IoEDeviceDeleteRequest;
import tech.ordinaryroad.ioe.api.request.IoEDeviceSaveRequest;
import tech.ordinaryroad.ioe.facade.IIoEDeviceFacade;
import tech.ordinaryroad.ioe.mapstruct.IoEDeviceMapStruct;
import tech.ordinaryroad.ioe.service.IoEService;

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

}
