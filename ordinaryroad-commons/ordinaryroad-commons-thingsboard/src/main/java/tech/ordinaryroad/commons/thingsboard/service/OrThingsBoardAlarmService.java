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
import org.thingsboard.server.common.data.alarm.Alarm;
import org.thingsboard.server.common.data.alarm.AlarmSeverity;
import org.thingsboard.server.common.data.alarm.AlarmStatus;
import org.thingsboard.server.common.data.id.EntityId;

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

}
