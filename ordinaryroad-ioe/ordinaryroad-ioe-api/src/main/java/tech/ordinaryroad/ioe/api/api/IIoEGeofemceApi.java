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
package tech.ordinaryroad.ioe.api.api;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.ioe.api.constant.ServiceNameCons;
import tech.ordinaryroad.ioe.api.dto.IoEGeofenceDTO;
import tech.ordinaryroad.ioe.api.request.IoEGeofenceQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEGeofenceSaveRequest;

/**
 * @author mjz
 * @date 2022/4/5
 */
@Api(value = "地理围栏API")
@FeignClient(name = ServiceNameCons.SERVICE_NAME, contextId = "iIoEGeofemceApi")
public interface IIoEGeofemceApi {

    @PostMapping("/geofence/create")
    Result<IoEGeofenceDTO> create(@RequestBody @Validated IoEGeofenceSaveRequest request);

    @PostMapping("/geofence/delete")
    Result<Boolean> delete(@RequestBody @Validated BaseDeleteRequest request);

    @PostMapping("/geofence/update")
    Result<IoEGeofenceDTO> update(@RequestBody @Validated IoEGeofenceSaveRequest request);

    @PostMapping("/geofence/list")
    Result<PageInfo<IoEGeofenceDTO>> list(@RequestBody @Validated IoEGeofenceQueryRequest request);

}
