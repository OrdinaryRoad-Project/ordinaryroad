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

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.DeviceInfo;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.thingsboard.service.OrThingsBoardDeviceService;
import tech.ordinaryroad.ioe.api.dto.IoEDeviceInfoDTO;
import tech.ordinaryroad.ioe.api.request.IoEDeviceInfoQueryRequest;
import tech.ordinaryroad.ioe.facade.IIoEDeviceInfoFacade;
import tech.ordinaryroad.ioe.mapstruct.IoEDeviceInfoMapStruct;
import tech.ordinaryroad.ioe.service.IoEService;
import tech.ordinaryroad.ioe.utis.IoEUtils;

import java.util.Optional;

/**
 * @author mjz
 * @date 2022/3/26
 */
@RequiredArgsConstructor
@Component
public class IoEDeviceInfoFacadeImpl implements IIoEDeviceInfoFacade {

    private final IoEService ioEService;
    private final OrThingsBoardDeviceService thingsBoardDeviceService;
    private final IoEDeviceInfoMapStruct mapStruct;

    @Override
    public Result<IoEDeviceInfoDTO> findById(String id) {
        Optional<DeviceInfo> optional = thingsBoardDeviceService.getDeviceInfoById(id);
        return optional.map(data -> Result.success(mapStruct.transfer(data))).orElseGet(Result::fail);
    }

    @Override
    public Result<PageInfo<IoEDeviceInfoDTO>> list(IoEDeviceInfoQueryRequest request) {
        final String customerId = ioEService.getUser().getCustomerId();
        final String deviceType = request.getDeviceType();
        final String deviceProfileId = request.getDeviceProfileId();

        final PageLink pageLink = IoEUtils.requestToPageLink(request);
        final PageData<DeviceInfo> deviceInfoPageData = thingsBoardDeviceService.listDeviceInfos(customerId, deviceType, deviceProfileId, pageLink);
        final PageInfo<IoEDeviceInfoDTO> pageInfo = IoEUtils.pageDataToPageInfo(pageLink, deviceInfoPageData, mapStruct::transfer);

        return Result.success(pageInfo);
    }

}
