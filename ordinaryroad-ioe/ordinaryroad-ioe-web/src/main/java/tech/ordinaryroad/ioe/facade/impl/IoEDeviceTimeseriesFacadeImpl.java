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
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.kv.Aggregation;
import org.thingsboard.server.common.data.kv.TsKvEntry;
import org.thingsboard.server.common.data.page.SortOrder;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.thingsboard.service.OrThingsBoardTimeseriesService;
import tech.ordinaryroad.ioe.api.dto.IoETsKvEntryDTO;
import tech.ordinaryroad.ioe.api.request.IoEDeviceTimeseriesQueryRequest;
import tech.ordinaryroad.ioe.facade.IIoEDeviceTimeseriesFacade;
import tech.ordinaryroad.ioe.mapstruct.IoEKvEntityMapStruct;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2022/3/29
 */
@RequiredArgsConstructor
@Component
public class IoEDeviceTimeseriesFacadeImpl implements IIoEDeviceTimeseriesFacade {

    private final OrThingsBoardTimeseriesService timeseriesService;
    private final IoEKvEntityMapStruct mapStruct;

    @Override
    public Result<List<String>> getTimeseriesKeys(String id) {
        return Result.success(timeseriesService.getTimeseriesKeys(DeviceId.fromString(id)));
    }

    @Override
    public Result<List<IoETsKvEntryDTO>> getTimeseries(IoEDeviceTimeseriesQueryRequest request) {
        String id = request.getId();
        List<String> keys = request.getKeys();
        Long startTime = request.getStartTime();
        Long endTime = request.getEndTime();
        Long interval = request.getInterval();
        Aggregation agg = Aggregation.valueOf(request.getAgg());
        SortOrder.Direction direction = SortOrder.Direction.valueOf(request.getDirection());
        Integer limit = request.getLimit();
        Boolean useStrictDataTypes = request.getUseStrictDataTypes();
        final List<TsKvEntry> timeseries = timeseriesService.getTimeseries(DeviceId.fromString(id), keys, startTime, endTime, interval, agg, direction, limit, useStrictDataTypes);
        final List<IoETsKvEntryDTO> collect = timeseries.stream().map(mapStruct::transfer).collect(Collectors.toList());
        return Result.success(collect);
    }
}
