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
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.ioe.api.api.IIoEGeofemceApi;
import tech.ordinaryroad.ioe.api.dto.IoEGeofenceDTO;
import tech.ordinaryroad.ioe.api.request.IoEGeofenceQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEGeofenceSaveRequest;
import tech.ordinaryroad.ioe.facade.IIoEGeofenceFacade;

/**
 * @author mjz
 * @date 2022/4/5
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class IoEGeofenceController implements IIoEGeofemceApi {

    private final IIoEGeofenceFacade facade;

    @Override
    public Result<IoEGeofenceDTO> create(@RequestBody @Validated IoEGeofenceSaveRequest request) {
        return facade.create(request);
    }

    @Override
    public Result<Boolean> delete(@RequestBody @Validated BaseDeleteRequest request) {
        return facade.delete(request);
    }

    @Override
    public Result<IoEGeofenceDTO> update(@RequestBody @Validated IoEGeofenceSaveRequest request) {
        return facade.update(request);
    }

    @Override
    public Result<PageInfo<IoEGeofenceDTO>> list(@RequestBody @Validated IoEGeofenceQueryRequest request) {
        return facade.list(request);
    }

}
