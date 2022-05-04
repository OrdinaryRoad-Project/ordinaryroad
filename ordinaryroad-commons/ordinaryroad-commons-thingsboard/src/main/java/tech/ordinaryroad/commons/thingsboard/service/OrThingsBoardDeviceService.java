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
import com.fasterxml.jackson.databind.JsonNode;
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
import org.thingsboard.server.common.data.query.AlarmData;
import org.thingsboard.server.common.data.query.AlarmDataPageLink;
import org.thingsboard.server.common.data.query.EntityTypeFilter;
import org.thingsboard.server.common.data.query.SingleEntityFilter;
import org.thingsboard.server.common.data.security.DeviceCredentials;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author mjz
 * @date 2022/3/25
 */
@RequiredArgsConstructor
@Service
public class OrThingsBoardDeviceService {

    private final OrThingsBoardClientService clientService;
    private final OrThingsBoardTimeseriesService timeseriesService;
    private final OrThingsBoardAlarmService alarmService;

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
     * Requested device must be owned by tenant that the user belongs to. Device name is an unique property of device. So it can be used to identify the device.
     * <p>
     * Available for users with 'TENANT_ADMIN' authority.
     *
     * @param deviceName 设备名称
     * @return Optional
     */
    public Optional<Device> getTenantDevice(@NotBlank String deviceName) {
        return clientService.getClient().getTenantDevice(deviceName);
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

    /**
     * Fetch the Device Info object based on the provided Device Id. If the user has the authority of 'Tenant Administrator', the server checks that the device is owned by the same tenant. If the user has the authority of 'Customer User', the server checks that the device is assigned to the same customer. Device Info is an extension of the default Device object that contains information about the assigned customer name and device profile name.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param id 设备Id
     * @return Optional
     */
    public Optional<DeviceInfo> getDeviceInfoById(@NotBlank String id) {
        return clientService.getClient().getDeviceInfoById(DeviceId.fromString(id));
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

    /**
     * 分页查询设备的告警信息
     *
     * @param deviceId          设备Id
     * @param alarmDataPageLink AlarmDataPageLink
     * @return PageData
     */
    public PageData<AlarmData> findDeviceAlarmDataByQuery(@NotBlank String deviceId, @NotNull AlarmDataPageLink alarmDataPageLink) {
        final SingleEntityFilter singleEntityFilter = new SingleEntityFilter();
        singleEntityFilter.setSingleEntity(DeviceId.fromString(deviceId));
        return alarmService.findAlarmDataByQuery(singleEntityFilter, alarmDataPageLink, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    /**
     * 分页查询所有设备的告警信息
     *
     * @param alarmDataPageLink AlarmDataPageLink
     * @return PageData
     */
    public PageData<AlarmData> findAllDeviceAlarmDataByQuery(@NotBlank String userId, @NotNull AlarmDataPageLink alarmDataPageLink) {
        final EntityTypeFilter entityTypeFilter = new EntityTypeFilter();
        entityTypeFilter.setEntityType(EntityType.DEVICE);
        return alarmService.findAlarmDataByQuery(userId, entityTypeFilter, alarmDataPageLink, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Creates or updates the entity attributes based on Entity Id and the specified attribute scope. List of possible attribute scopes depends on the entity type:
     * <p>
     * SERVER_SCOPE - supported for all entity types;
     * CLIENT_SCOPE - supported for devices;
     * SHARED_SCOPE - supported for devices.
     * <p>
     * The request payload is a JSON object with key-value format of attributes to create or update. For example:
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
     * Referencing a non-existing entity Id or invalid entity type will cause an error.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param deviceId    设备Id
     * @param serverScope SERVER_SCOPE, CLIENT_SCOPE, SHARED_SCOPE
     * @param jsonNode    JSON格式的属性
     * @return 是否成功
     * @see org.thingsboard.server.common.data.DataConstants#allScopes()
     */
    public boolean saveDeviceAttributes(@NotBlank String deviceId, @NotBlank String serverScope, @NotNull JsonNode jsonNode) {
        return clientService.getClient().saveDeviceAttributes(DeviceId.fromString(deviceId), serverScope, jsonNode);
    }

    /**
     * Create or update the Device. When creating device, platform generates Device Id as time-based UUID. Device credentials are also generated if not provided in the 'accessToken' request parameter. The newly created device id will be present in the response. Specify existing Device id to update the device. Referencing non-existing device Id will cause 'Not Found' error.
     * <p>
     * Device name is unique in the scope of tenant. Use unique identifiers like MAC or IMEI for the device names and non-unique 'label' field for user-friendly visualization purposes.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param device Device
     * @return Device
     */
    public Device saveDevice(Device device) {
        return this.clientService.getClient().saveDevice(device);
    }

    /**
     * 更新设备标签
     *
     * @param id    设备Id
     * @param label 标签
     * @return Device
     */
    public Device updateDeviceLabel(String id, String label) {
        Optional<DeviceInfo> deviceInfoById = this.getDeviceInfoById(id);
        Device device = null;
        if (deviceInfoById.isPresent()) {
            device = deviceInfoById.get();
            device.setLabel(label);
            device = this.saveDevice(device);
        }
        return device;
    }

}
