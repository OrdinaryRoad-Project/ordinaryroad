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
package tech.ordinaryroad.ioe.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.ioe.api.api.IIoEDeviceApi;
import tech.ordinaryroad.ioe.api.dto.IoEAlarmDataDTO;
import tech.ordinaryroad.ioe.api.dto.IoEDeviceCredentialsDTO;
import tech.ordinaryroad.ioe.api.dto.IoEDeviceDTO;
import tech.ordinaryroad.ioe.api.request.IoEAlarmDataQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEDeviceAlarmDataQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEDeviceDeleteRequest;
import tech.ordinaryroad.ioe.api.request.IoEDeviceSaveRequest;
import tech.ordinaryroad.ioe.facade.IIoEDeviceFacade;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author mjz
 * @date 2022/3/27
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class IoEDeviceController implements IIoEDeviceApi {

    private final IIoEDeviceFacade deviceFacade;

    @Override
    public Result<IoEDeviceDTO> create(@RequestBody @Validated IoEDeviceSaveRequest request) {
        return deviceFacade.create(request);
    }

    @Override
    public Result<IoEDeviceDTO> delete(@RequestBody @Validated IoEDeviceDeleteRequest request) {
        return deviceFacade.delete(request);
    }

    @Override
    public Result<IoEDeviceCredentialsDTO> credentials(@PathVariable @Validated @NotBlank String id) {
        return deviceFacade.credentials(id);
    }

    @Override
    public Result<PageInfo<IoEAlarmDataDTO>> alarms(@RequestBody @Validated IoEDeviceAlarmDataQueryRequest request) {
        return deviceFacade.alarms(request);
    }

    @Override
    public Result<PageInfo<IoEAlarmDataDTO>> allAlarms(@RequestBody @Validated IoEAlarmDataQueryRequest request) {
        return deviceFacade.allAlarms(request);
    }

    @Override
    public Result<Boolean> saveAttributes(@PathVariable @Validated @NotBlank String deviceId, @PathVariable @Validated @NotBlank String scope, @RequestBody @NotNull JsonNode request) {
        return deviceFacade.saveAttributes(deviceId, scope, request);
    }

    @Override
    public Result<IoEDeviceDTO> updateLabel(@PathVariable @Validated @NotBlank String deviceId, @RequestParam @Validated @NotNull String label) {
        return deviceFacade.updateLabel(deviceId, label);
    }

}
