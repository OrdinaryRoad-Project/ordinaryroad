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

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.ioe.api.api.IIoEDeviceInfoApi;
import tech.ordinaryroad.ioe.api.dto.IoEDeviceInfoDTO;
import tech.ordinaryroad.ioe.api.request.IoEDeviceInfoQueryRequest;
import tech.ordinaryroad.ioe.facade.IIoEDeviceInfoFacade;

/**
 * @author mjz
 * @date 2022/3/24
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class IoEDeviceInfoController implements IIoEDeviceInfoApi {

    private final IIoEDeviceInfoFacade deviceInfoFacade;

    @Override
    public Result<PageInfo<IoEDeviceInfoDTO>> list(@RequestBody @Validated IoEDeviceInfoQueryRequest request) {
        return deviceInfoFacade.list(request);
    }

}