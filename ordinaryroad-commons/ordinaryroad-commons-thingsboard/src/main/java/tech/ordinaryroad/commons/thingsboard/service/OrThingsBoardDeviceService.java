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

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.*;
import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.id.DeviceProfileId;
import org.thingsboard.server.common.data.kv.Aggregation;
import org.thingsboard.server.common.data.kv.TsKvEntry;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.common.data.page.SortOrder;
import org.thingsboard.server.common.data.security.DeviceCredentials;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author mjz
 * @date 2022/3/25
 */
@RequiredArgsConstructor
@Service
public class OrThingsBoardDeviceService {

    private final OrThingsBoardClientService clientService;
    private final OrThingsBoardTimeseriesService timeseriesService;

    /**
     * Creates assignment of the device to customer. Customer will be able to query device afterwards.
     * <p>
     * Available for users with 'TENANT_ADMIN' authority.
     *
     * @param id       客户Id
     * @param deviceId 设备Id
     * @return Optional
     */
    public Optional<Device> assignDeviceToCustomer(@NotBlank String id, @NotBlank String deviceId) {
        return clientService.getClient().assignDeviceToCustomer(new CustomerId(UUID.fromString(id)), DeviceId.fromString(deviceId));
    }

    /**
     * Clears assignment of the device to customer. Customer will not be able to query device afterwards.
     * <p>
     * Available for users with 'TENANT_ADMIN' authority.
     *
     * @param id 设备Id
     * @return Optional
     */
    public Optional<Device> unassignDeviceFromCustomer(@NotBlank String id) {
        return clientService.getClient().unassignDeviceFromCustomer(DeviceId.fromString(id));
    }

    /**
     * Returns a set of unique device profile names based on devices that are either owned by the tenant or assigned to the customer which user is performing the request.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @return List
     */
    public List<EntitySubtype> listDeviceTypes() {
        return clientService.getClient().getDeviceTypes();
    }

    public PageData<Device> listDevices(@NotBlank String id, @NotNull PageLink pageLink) {
        return this.listDevices(id, null, pageLink);
    }

    /**
     * Returns a page of devices objects assigned to customer. You can specify parameters to filter the results. The result is wrapped with PageData object that allows you to iterate over result set using pagination. See the 'Model' tab of the Response Class for more details.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param id         Customer Id
     * @param deviceType 设备类型名称
     * @param pageLink   分页参数
     * @return PageData
     */
    public PageData<Device> listDevices(@NotBlank String id, String deviceType, @NotNull PageLink pageLink) {
        return clientService.getClient()
                .getCustomerDevices(
                        new CustomerId(UUID.fromString(id)),
                        StrUtil.blankToDefault(deviceType, null),
                        pageLink
                );
    }

    public PageData<DeviceProfileInfo> listDeviceProfileInfos(@NotNull PageLink pageLink) {
        return this.listDeviceProfileInfos(pageLink, null);
    }

    /**
     * Returns a page of devices profile info objects owned by tenant. You can specify parameters to filter the results. The result is wrapped with PageData object that allows you to iterate over result set using pagination. See the 'Model' tab of the Response Class for more details. Device Profile Info is a lightweight object that includes main information about Device Profile excluding the heavyweight configuration object.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param pageLink            分页参数 (*PageSize *Page)
     * @param deviceTransportType DEFAULT, MQTT, COAP, LWM2M, SNMP 默认为MQTT
     * @return PageData
     * @see DeviceTransportType
     */
    public PageData<DeviceProfileInfo> listDeviceProfileInfos(@NotNull PageLink pageLink, DeviceTransportType deviceTransportType) {
        return clientService.getClient()
                .getDeviceProfileInfos(
                        pageLink,
                        Objects.requireNonNullElse(deviceTransportType, DeviceTransportType.MQTT)
                );
    }

    public PageData<DeviceInfo> listDeviceInfos(@NotBlank String id, @NotNull PageLink pageLink) {
        return this.listDeviceInfos(id, null, null, pageLink);
    }

    /**
     * Returns a page of devices info objects assigned to customer. You can specify parameters to filter the results. The result is wrapped with PageData object that allows you to iterate over result set using pagination. See the 'Model' tab of the Response Class for more details. Device Info is an extension of the default Device object that contains information about the assigned customer name and device profile name.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param id              Customer Id (*)
     * @param deviceType      设备类型名称
     * @param deviceProfileId 设备配置Id
     * @param pageLink        分页参数 (*PageSize *Page)
     * @return PageData
     */
    public PageData<DeviceInfo> listDeviceInfos(@NotBlank String id, String deviceType, String deviceProfileId, @NotNull PageLink pageLink) {
        DeviceProfileId profileId;
        if (StrUtil.isBlank(deviceProfileId)) {
            profileId = null;
        } else {
            profileId = DeviceProfileId.fromString(deviceProfileId);
        }

        return clientService.getClient()
                .getCustomerDeviceInfos(
                        new CustomerId(UUID.fromString(id)),
                        StrUtil.blankToDefault(deviceType, null),
                        profileId,
                        pageLink
                );
    }

    public List<String> getTimeseriesKeys(@NotBlank String id) {
        return timeseriesService.getTimeseriesKeys(DeviceId.fromString(id));
    }

    public List<TsKvEntry> getTimeseries(@NotBlank String id, @NotNull List<String> keys,
                                         @NotNull Long startTime, @NotNull Long endTime,
                                         Long interval, Aggregation agg, SortOrder.Direction direction, Integer limit, Boolean useStrictDataTypes) {
        return timeseriesService.getTimeseries(DeviceId.fromString(id), keys, startTime, endTime, interval, agg, direction, limit, useStrictDataTypes);
    }

    /**
     * If during device creation there wasn't specified any credentials, platform generates random 'ACCESS_TOKEN' credentials.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param id 设备Id
     * @return Optional
     */
    public Optional<DeviceCredentials> getDeviceCredentials(@NotBlank String id) {
        return clientService.getClient().getDeviceCredentialsByDeviceId(DeviceId.fromString(id));
    }

}
