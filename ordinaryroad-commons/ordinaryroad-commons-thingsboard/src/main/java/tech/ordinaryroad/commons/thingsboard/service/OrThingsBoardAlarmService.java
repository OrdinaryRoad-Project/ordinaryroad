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

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.alarm.*;
import org.thingsboard.server.common.data.id.AlarmId;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.TimePageLink;
import org.thingsboard.server.common.data.query.*;

import java.util.List;

/**
 * @author mjz
 * @date 2022/4/7
 */
@RequiredArgsConstructor
@Service
public class OrThingsBoardAlarmService {

    private final OrThingsBoardClientService clientService;

    /**
     * Creates or Updates the Alarm. When creating alarm, platform generates Alarm Id as time-based UUID. The newly created Alarm id will be present in the response. Specify existing Alarm id to update the alarm. Referencing non-existing Alarm Id will cause 'Not Found' error.
     * <p>
     * Platform also deduplicate the alarms based on the entity id of originator and alarm 'type'. For example, if the user or system component create the alarm with the type 'HighTemperature' for device 'Device A' the new active alarm is created. If the user tries to create 'HighTemperature' alarm for the same device again, the previous alarm will be updated (the 'end_ts' will be set to current timestamp). If the user clears the alarm (see 'Clear Alarm(clearAlarm)'), than new alarm with the same type and same device may be created.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param entityId      实体Id
     * @param type          类型
     * @param alarmSeverity 重要程度
     * @param alarmStatus   状态
     * @param details       详情
     * @return Alarm
     */
    public Alarm saveAlarm(EntityId entityId, String type, AlarmSeverity alarmSeverity, AlarmStatus alarmStatus, JsonNode details) {
        return clientService.getClient().saveAlarm(
                Alarm.builder()
                        .originator(entityId)
                        .type(type)
                        .severity(alarmSeverity)
                        .status(alarmStatus)
                        .details(details)
                        .build()
        );
    }

    /**
     * Deletes the Alarm. Referencing non-existing Alarm Id will cause an error.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param alarmId AlarmId
     */
    public void delete(String alarmId) {
        clientService.getClient().deleteAlarm(AlarmId.fromString(alarmId));
    }

    /**
     * Acknowledge the Alarm. Once acknowledged, the 'ack_ts' field will be set to current timestamp and special rule chain event 'ALARM_ACK' will be generated. Referencing non-existing Alarm Id will cause an error.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param alarmId AlarmId
     */
    public void ack(String alarmId) {
        clientService.getClient().ackAlarm(AlarmId.fromString(alarmId));
    }

    /**
     * Clear the Alarm. Once cleared, the 'clear_ts' field will be set to current timestamp and special rule chain event 'ALARM_CLEAR' will be generated. Referencing non-existing Alarm Id will cause an error.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param alarmId AlarmId
     */
    public void clear(String alarmId) {
        clientService.getClient().clearAlarm(AlarmId.fromString(alarmId));
    }

    /**
     * Returns a page of alarms for the selected entity. Specifying both parameters 'searchStatus' and 'status' at the same time will cause an error. You can specify parameters to filter the results. The result is wrapped with PageData object that allows you to iterate over result set using pagination. See the 'Model' tab of the Response Class for more details.
     * <p>
     * Available for users with 'TENANT_ADMIN' or 'CUSTOMER_USER' authority.
     *
     * @param entityId        EntityId
     * @param searchStatus    AlarmSearchStatus
     * @param status          AlarmStatus
     * @param pageLink        TimePageLink
     * @param fetchOriginator A boolean value to specify if the alarm originator name will be filled in the AlarmInfo object field: 'originatorName' or will returns as null.
     * @return PageData
     */
    public PageData<AlarmInfo> getAlarms(EntityId entityId, AlarmSearchStatus searchStatus, AlarmStatus status, TimePageLink pageLink, Boolean fetchOriginator) {
        return clientService.getClient().getAlarms(entityId, searchStatus, status, pageLink, fetchOriginator);
    }

    /**
     * This method description defines how Alarm Data Query extends the Entity Data Query. See method 'Find Entity Data by Query' first to get the info about 'Entity Data Query'.
     * <p>
     * The platform will first search the entities that match the entity and key filters. Then, the platform will use 'Alarm Page Link' to filter the alarms related to those entities. Finally, platform fetch the properties of alarm that are defined in the 'alarmFields' and combine them with the other entity, attribute and latest time-series fields to return the result.
     * <p>
     * See example of the alarm query below. The query will search first 100 active alarms with type 'Temperature Alarm' or 'Fire Alarm' for any device with current temperature > 0. The query will return combination of the entity fields: name of the device, device model and latest temperature reading and alarms fields: createdTime, type, severity and status:
     *
     * @param entityFilter
     * @param pageLink
     * @param entityFields
     * @param latestValues
     * @param keyFilters
     * @param alarmFields
     * @return
     */
    public PageData<AlarmData> findAlarmDataByQuery(EntityFilter entityFilter, AlarmDataPageLink pageLink, List<EntityKey> entityFields, List<EntityKey> latestValues, List<KeyFilter> keyFilters, List<EntityKey> alarmFields) {
        return clientService.getClient().findAlarmDataByQuery(
                new AlarmDataQuery(entityFilter, pageLink, entityFields, latestValues, keyFilters, alarmFields)
        );
    }

    /**
     * 分页查询用户所有的告警数据
     *
     * @param userId 用户Id
     * @return PageData
     */
    public PageData<AlarmData> findAlarmDataByQuery(String userId, EntityFilter entityFilter, AlarmDataPageLink pageLink, List<EntityKey> entityFields, List<EntityKey> latestValues, List<KeyFilter> keyFilters, List<EntityKey> alarmFields) {
        return clientService.newClient(userId).findAlarmDataByQuery(
                new AlarmDataQuery(entityFilter, pageLink, entityFields, latestValues, keyFilters, alarmFields)
        );
    }

}
