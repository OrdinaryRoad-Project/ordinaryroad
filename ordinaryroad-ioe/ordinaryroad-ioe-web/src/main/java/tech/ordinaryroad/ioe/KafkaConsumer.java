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
package tech.ordinaryroad.ioe;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.alarm.Alarm;
import org.thingsboard.server.common.data.alarm.AlarmSeverity;
import org.thingsboard.server.common.data.alarm.AlarmStatus;
import org.thingsboard.server.common.data.id.DeviceId;
import tech.ordinaryroad.commons.thingsboard.service.OrThingsBoardAlarmService;
import tech.ordinaryroad.commons.thingsboard.service.OrThingsBoardDeviceService;
import tech.ordinaryroad.ioe.api.LatLon;
import tech.ordinaryroad.ioe.api.constant.PushCons;
import tech.ordinaryroad.ioe.entity.IoEGeofenceDO;
import tech.ordinaryroad.ioe.entity.IoEUserDO;
import tech.ordinaryroad.ioe.service.IoEGeofenceService;
import tech.ordinaryroad.ioe.service.IoEService;
import tech.ordinaryroad.ioe.service.IoEUserService;
import tech.ordinaryroad.ioe.utis.GeoUtil;
import tech.ordinaryroad.ioe.utis.RangeUnit;
import tech.ordinaryroad.push.api.IPushApi;
import tech.ordinaryroad.push.request.AndroidPushRequest;
import tech.ordinaryroad.push.request.EmailPushRequest;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author mjz
 * @date 2022/4/6
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumer {

    private final OrThingsBoardDeviceService thingsBoardDeviceService;
    private final OrThingsBoardAlarmService thingsBoardAlarmService;
    private final IoEGeofenceService geofenceService;
    private final IoEUserService userService;
    private final IPushApi pushApi;
    private final IoEService ioEService;

    @KafkaListener(topics = {"telemetry-device"}, groupId = "ioe-telemetry-device")
    public void onDeviceTelemetry(ConsumerRecord<String, String> record) {
        log.info("onDeviceTelemetry:{}", record);
        final Headers headers = record.headers();

        final String deviceType = new String(headers.lastHeader("tb_msg_md_deviceType").value(), StandardCharsets.UTF_8);
        final String deviceName = new String(headers.lastHeader("tb_msg_md_deviceName").value(), StandardCharsets.UTF_8);

        final JSONObject msg = JSON.parseObject(record.value());
        final JSONObject p = msg.getJSONObject("p");
        log.info("onDeviceTelemetry, deviceType:{},deviceName:{},msg:{}", deviceType, deviceName, msg);
        log.info("onDeviceTelemetry, p:{}", p);

        final Optional<Device> tenantDevice = thingsBoardDeviceService.getTenantDevice(deviceName);
        log.info("onDeviceTelemetry, device:{}", tenantDevice.toString());

        if (tenantDevice.isPresent()) {
            final Device device = tenantDevice.get();
            final DeviceId deviceId = device.getId();
            final Optional<IoEUserDO> byCustomerId = userService.findByCustomerId(device.getCustomerId().toString());
            if (byCustomerId.isPresent()) {
                final IoEUserDO ioEUserDO = byCustomerId.get();
                final String orNumber = ioEUserDO.getOrNumber();
                final String email = ioEService.getEmail(orNumber);
                final List<IoEGeofenceDO> geofenceList = geofenceService.findAllByCreateByAndDeviceId(ioEUserDO.getOrNumber(), deviceId.toString());
                log.info("onDeviceTelemetry, geofenceList:{}", JSON.toJSON(geofenceList));
                // 出围栏
                List<IoEGeofenceDO> geofenceOut = new ArrayList<>();
                // 入围栏
                List<IoEGeofenceDO> geofenceIn = new ArrayList<>();
                // 判断是否需要告警
                for (IoEGeofenceDO geofence : geofenceList) {
                    final String geofenceName = geofence.getName();
                    if (checkMatches(geofence, p.getDouble("lat"), p.getDouble("lon"))) {
                        geofenceIn.add(geofence);
                    } else {
                        geofenceOut.add(geofence);
                    }
                }
                if (!geofenceOut.isEmpty()) {
                    // 需要创建出围栏告警
                    Map<String, List<String>> severityNamesMap = getSeverityNamesMap(geofenceOut);
                    severityNamesMap.forEach((severity, names) -> {
                        final JsonNode details = new TextNode(CollUtil.join(names, "、"));
                        AlarmSeverity alarmSeverity = AlarmSeverity.valueOf(severity);
                        final Alarm alarm = thingsBoardAlarmService.saveAlarm(deviceId, PushCons.ANDROID_TITLE_GEOFENCE_OUTSIDE, alarmSeverity, AlarmStatus.ACTIVE_UNACK, details);

                        // 构建客户端推送
                        final AndroidPushRequest androidPushRequest = new AndroidPushRequest();
                        androidPushRequest.setPackageName(PushCons.ANDROID_PACKAGE_NAME);
                        androidPushRequest.setTitle(PushCons.ANDROID_TITLE_GEOFENCE_OUTSIDE);
                        androidPushRequest.setContent(deviceName + " " + details.textValue());
                        androidPushRequest.setToOrNumber(orNumber);
                        androidPushRequest.setChannel(PushCons.ANDROID_ALARM_CHANNEL_ID);

                        // 构建邮箱推送
                        final EmailPushRequest emailPushRequest = new EmailPushRequest();
                        emailPushRequest.setTitle(PushCons.ANDROID_TITLE_GEOFENCE_OUTSIDE);
                        emailPushRequest.setContent(deviceName + " " + details.textValue());
                        emailPushRequest.setEmail(email);

                        doPush(alarmSeverity, alarm, androidPushRequest, emailPushRequest);
                    });
                }

                if (!geofenceIn.isEmpty()) {
                    // 需要创建入围栏告警
                    Map<String, List<String>> severityNamesMap = getSeverityNamesMap(geofenceIn);
                    severityNamesMap.forEach((severity, names) -> {
                        final JsonNode details = new TextNode(CollUtil.join(names, "、"));
                        AlarmSeverity alarmSeverity = AlarmSeverity.valueOf(severity);
                        final Alarm alarm = thingsBoardAlarmService.saveAlarm(deviceId, PushCons.ANDROID_TITLE_GEOFENCE_INSIDE, alarmSeverity, AlarmStatus.ACTIVE_UNACK, details);

                        // 构建客户端推送
                        final AndroidPushRequest androidPushRequest = new AndroidPushRequest();
                        androidPushRequest.setPackageName(PushCons.ANDROID_PACKAGE_NAME);
                        androidPushRequest.setTitle(PushCons.ANDROID_TITLE_GEOFENCE_INSIDE);
                        androidPushRequest.setContent(deviceName + " " + details.textValue());
                        androidPushRequest.setToOrNumber(orNumber);
                        androidPushRequest.setChannel(PushCons.ANDROID_ALARM_CHANNEL_ID);

                        // 构建邮箱推送
                        final EmailPushRequest emailPushRequest = new EmailPushRequest();
                        emailPushRequest.setTitle(PushCons.ANDROID_TITLE_GEOFENCE_INSIDE);
                        emailPushRequest.setContent(deviceName + " " + details.textValue());
                        emailPushRequest.setEmail(email);

                        doPush(alarmSeverity, alarm, androidPushRequest, emailPushRequest);
                    });
                }
            }
        }
    }

    /**
     * 根据严重程度执行消息推送动作
     * <p>
     * CRITICAL：危险，每次产生告警发送客户端推送和邮箱推送；
     * <p>
     * MAJOR：重要，每次产生告警仅发送邮箱推送；
     * <p>
     * MINOR：次要，每次产生告警仅发送客户端推送；
     * <p>
     * WARNING：警告，仅首次产生告警发送客户端和邮箱推送；
     * <p>
     * INDETERMINATE：不确定，告警产生时不进行任何消息推送
     * <p>
     *
     * @param alarmSeverity      AlarmSeverity
     * @param alarm              Alarm
     * @param androidPushRequest AndroidPushRequest
     * @param emailPushRequest   EmailPushRequest
     */
    private void doPush(AlarmSeverity alarmSeverity, Alarm alarm, AndroidPushRequest androidPushRequest, EmailPushRequest emailPushRequest) {
        switch (alarmSeverity) {
            case CRITICAL:
                // 危险，每次产生告警发送客户端推送和邮箱推送
                pushApi.android(androidPushRequest);
                pushApi.email(emailPushRequest);
                break;
            case MAJOR:
                // 重要，每次产生告警仅发送邮箱推送
                pushApi.email(emailPushRequest);
                break;
            case MINOR:
                // 次要，每次产生告警仅发送客户端推送；
                pushApi.android(androidPushRequest);
                break;
            case WARNING:
                // 警告，仅首次产生告警发送客户端和邮箱推送
                if (alarm.getStartTs() == alarm.getEndTs()) {
                    pushApi.android(androidPushRequest);
                    pushApi.email(emailPushRequest);
                }
                break;
            case INDETERMINATE:
                // 告警产生时不进行任何消息推送
                break;
            default:
        }
    }

    @NotNull
    private Map<String, List<String>> getSeverityNamesMap(List<IoEGeofenceDO> geofenceList) {
        Map<String, List<String>> severityNamesMap = new HashMap<>();
        for (IoEGeofenceDO ioEGeofenceDO : geofenceList) {
            String severity = ioEGeofenceDO.getSeverity();
            String name = ioEGeofenceDO.getName();
            List<String> names = severityNamesMap.getOrDefault(severity, new ArrayList<>());
            names.add(name);
            severityNamesMap.putIfAbsent(severity, names);
        }
        return severityNamesMap;
    }

    @KafkaListener(topics = {"telemetry-device-pbl-vibrating"}, groupId = "ioe-telemetry-device-pbl")
    public void onDeviceTelemetryPblVibrating(ConsumerRecord<String, String> record) {
        final Headers headers = record.headers();

        final String deviceType = new String(headers.lastHeader("tb_msg_md_deviceType").value(), StandardCharsets.UTF_8);
        final String deviceName = new String(headers.lastHeader("tb_msg_md_deviceName").value(), StandardCharsets.UTF_8);
        final String lastLockStatus = new String(headers.lastHeader("tb_msg_md_lockStatus").value(), StandardCharsets.UTF_8);

        final JSONObject msg = JSON.parseObject(record.value());
        final Boolean vibrating = msg.getBoolean("vibrating");

        final Optional<Device> tenantDevice = thingsBoardDeviceService.getTenantDevice(deviceName);

        if (tenantDevice.isPresent()) {
            final Device device = tenantDevice.get();
            final Optional<IoEUserDO> byCustomerId = userService.findByCustomerId(device.getCustomerId().toString());
            if (byCustomerId.isPresent()) {
                final IoEUserDO ioEUserDO = byCustomerId.get();
                final String orNumber = ioEUserDO.getOrNumber();
                if (vibrating && "LOCKED".equals(lastLockStatus)) {
                    // 需要创建创建未解锁震动告警
                    final Alarm alarm = thingsBoardAlarmService.saveAlarm(device.getId(), PushCons.ANDROID_TITLE_VIBRATING_WHEN_LOCKED, AlarmSeverity.WARNING, AlarmStatus.ACTIVE_UNACK, null);
                    if (alarm.getStartTs() == alarm.getEndTs()) {
                        // 首次创建APP推送
                        final AndroidPushRequest request = new AndroidPushRequest();
                        request.setPackageName(PushCons.ANDROID_PACKAGE_NAME);
                        request.setTitle(PushCons.ANDROID_TITLE_VIBRATING_WHEN_LOCKED);
                        request.setContent(deviceName);
                        request.setToOrNumber(orNumber);
                        request.setChannel(PushCons.ANDROID_ALARM_CHANNEL_ID);
                        pushApi.android(request);
                    }
                }
            }
        }
    }

    protected boolean checkMatches(IoEGeofenceDO geofence, double latitude, double longitude) {
        if (geofence.getType() == 0) {
            LatLon entityCoordinates = new LatLon(latitude, longitude);
            LatLon perimeterCoordinates = geofence.getPointList().get(0);
            return geofence.getRadius() > GeoUtil.distance(entityCoordinates, perimeterCoordinates, RangeUnit.METER);
        } else if (geofence.getType() == 1) {
            List<String> latLongStringList = new ArrayList<>();
            List<Double> latAndLongList = new ArrayList<>();
            for (LatLon latLon : geofence.getPointList()) {
                latAndLongList.add(latLon.getLatitude());
                latAndLongList.add(latLon.getLongitude());
                latLongStringList.add(latAndLongList.toString());
                latAndLongList.clear();
            }
            return GeoUtil.contains(latLongStringList.toString(), new LatLon(latitude, longitude));
        } else {
            throw new RuntimeException("Unsupported IoEGeofenceDO type: " + geofence.getType());
        }
    }

}
