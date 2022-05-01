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
package tech.ordinaryroad.commons.thingsboard.service;

import cn.hutool.core.util.BooleanUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.DataConstants;
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.kv.Aggregation;
import org.thingsboard.server.common.data.kv.TsKvEntry;
import org.thingsboard.server.common.data.page.SortOrder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author mjz
 * @date 2022/3/28
 */
@RequiredArgsConstructor
@Service
public class OrThingsBoardTimeseriesService {

    private final OrThingsBoardClientService clientService;

    /**
     * Creates or updates the device attributes based on device id and specified attribute scope. The request payload is a JSON object with key-value format of attributes to create or update. For example:
     *
     * <pre>
     * {
     *  "stringKey":"value1",
     *  "booleanKey":true,
     *  "doubleKey":42.0,
     *  "longKey":73,
     *  "jsonKey": {
     *     "someNumber": 42,
     *     "someArray": [1,2,3],
     *     "someNestedObject": {"key": "value"}
     *  }
     * }
     * </pre>
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param id      设备Id
     * @param scope   A string value representing the attributes scope. For example,'CLIENT_SCOPE', 'SERVER_SCOPE', 'SHARED_SCOPE'
     * @param request JsonNode
     * @return Boolean
     * @see DataConstants#CLIENT_SCOPE
     * @see DataConstants#SERVER_SCOPE
     * @see DataConstants#SHARED_SCOPE
     */
    public Boolean saveDeviceAttributes(@NotBlank String id, @NotBlank String scope, @NotNull JsonNode request) {
        return clientService.getClient().saveDeviceAttributes(DeviceId.fromString(id), scope, request);
    }

    /**
     * Returns a set of unique time-series key names for the selected entity.
     * <p>
     * Referencing a non-existing entity Id or invalid entity type will cause an error.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param id Entity Id
     * @return List
     */
    public List<String> getTimeseriesKeys(@NotNull EntityId id) {
        return clientService.getClient().getTimeseriesKeys(id);
    }

    /**
     * Returns a range of time-series values for specified entity. Returns not aggregated data by default. Use aggregation function ('agg') and aggregation interval ('interval') to enable aggregation of the results on the database / server side. The aggregation is generally more efficient then fetching all records.
     *
     * <pre>
     * {
     *   "temperature": [
     *     {
     *       "value": 36.7,
     *       "ts": 1609459200000
     *     },
     *     {
     *       "value": 36.6,
     *       "ts": 1609459201000
     *     }
     *   ]
     * }
     * </pre>
     * Referencing a non-existing entity Id or invalid entity type will cause an error.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param id                 Entity Id
     * @param keys               A string value representing the comma-separated list of telemetry keys.
     * @param startTime          A long value representing the start timestamp of the time range in milliseconds, UTC.
     * @param endTime            A long value representing the end timestamp of the time range in milliseconds, UTC.
     * @param interval           A long value representing the aggregation interval range in milliseconds.
     * @param agg                A string value representing the aggregation function. If the interval is not specified, 'agg' parameter will use 'NONE' value.
     * @param direction          Sort order. ASC (ASCENDING) or DESC (DESCENDING)
     * @param limit              An integer value that represents a max number of timeseries data points to fetch. This parameter is used only in the case if 'agg' parameter is set to 'NONE'.
     * @param useStrictDataTypes Enables/disables conversion of telemetry values to strings. Conversion is enabled by default. Set parameter to 'true' in order to disable the conversion.
     * @return List
     */
    public List<TsKvEntry> getTimeseries(
            @NotNull EntityId id, @NotEmpty List<String> keys,
            @NotNull Long startTime, @NotNull Long endTime,
            Long interval, Aggregation agg, SortOrder.Direction direction, Integer limit, Boolean useStrictDataTypes
    ) {
        return clientService.getClient()
                .getTimeseries(
                        id,
                        keys,
                        interval,
                        agg,
                        direction,
                        startTime,
                        endTime,
                        limit,
                        BooleanUtil.isTrue(useStrictDataTypes)
                );
    }

}
