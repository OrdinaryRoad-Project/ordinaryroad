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
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.alarm.Alarm;
import org.thingsboard.server.common.data.alarm.AlarmSeverity;
import org.thingsboard.server.common.data.alarm.AlarmStatus;
import tech.ordinaryroad.commons.thingsboard.service.OrThingsBoardAlarmService;
import tech.ordinaryroad.commons.thingsboard.service.OrThingsBoardDeviceService;
import tech.ordinaryroad.ioe.api.LatLon;
import tech.ordinaryroad.ioe.api.constant.PushCons;
import tech.ordinaryroad.ioe.entity.IoEGeofenceDO;
import tech.ordinaryroad.ioe.entity.IoEUserDO;
import tech.ordinaryroad.ioe.service.IoEGeofenceService;
import tech.ordinaryroad.ioe.service.IoEUserService;
import tech.ordinaryroad.ioe.utis.GeoUtil;
import tech.ordinaryroad.ioe.utis.RangeUnit;
import tech.ordinaryroad.push.api.IPushApi;
import tech.ordinaryroad.push.request.AndroidPushRequest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            final Optional<IoEUserDO> byCustomerId = userService.findByCustomerId(device.getCustomerId().toString());
            if (byCustomerId.isPresent()) {
                final IoEUserDO ioEUserDO = byCustomerId.get();
                final String orNumber = ioEUserDO.getOrNumber();
                final List<IoEGeofenceDO> geofenceList = geofenceService.findAllByCreateByAndDeviceId(ioEUserDO.getOpenid(), device.getId().toString());
                log.info("onDeviceTelemetry, geofenceList:{}", JSON.toJSON(geofenceList));
                // 出围栏
                List<String> namesOut = new ArrayList<>();
                // 入围栏
                List<String> namesIn = new ArrayList<>();
                // 判断是否需要告警
                for (IoEGeofenceDO geofence : geofenceList) {
                    final String geofenceName = geofence.getName();
                    if (checkMatches(geofence, p.getDouble("lat"), p.getDouble("lon"))) {
                        log.warn("onDeviceTelemetry, inside the geofence {}", geofenceName);
                        namesIn.add(geofenceName);
                    } else {
                        log.warn("onDeviceTelemetry, outside the geofence {}", geofenceName);
                        namesOut.add(geofenceName);
                    }
                }
                if (!namesOut.isEmpty()) {
                    // 需要创建出围栏告警
                    final JsonNode details = new TextNode(CollUtil.join(namesOut, "、"));
                    final Alarm alarm = thingsBoardAlarmService.saveAlarm(device.getId(), "出围栏告警", AlarmSeverity.WARNING, AlarmStatus.ACTIVE_UNACK, details);
                    log.info("onDeviceTelemetry, Android Push {} {},{}", alarm.getStartTs() == alarm.getEndTs(), alarm.getStartTs(), alarm.getEndTs());
                    if (alarm.getStartTs() == alarm.getEndTs()) {
                        // 首次创建APP推送
                        final AndroidPushRequest request = new AndroidPushRequest();
                        request.setPackageName(PushCons.ANDROID_PACKAGE_NAME);
                        request.setTitle("出围栏告警");
                        request.setContent(details.textValue());
                        request.setToOrNumber(orNumber);
                        pushApi.android(request);
                    }
                }
                if (!namesIn.isEmpty()) {
                    // 需要创建入围栏告警
                    final JsonNode details = new TextNode(CollUtil.join(namesIn, "、"));
                    final Alarm alarm = thingsBoardAlarmService.saveAlarm(device.getId(), "入围栏告警", AlarmSeverity.WARNING, AlarmStatus.ACTIVE_UNACK, details);
                    log.info("onDeviceTelemetry, Android Push {} {},{}", alarm.getStartTs() == alarm.getEndTs(), alarm.getStartTs(), alarm.getEndTs());
                    if (alarm.getStartTs() == alarm.getEndTs()) {
                        // 首次创建APP推送
                        final AndroidPushRequest request = new AndroidPushRequest();
                        request.setPackageName(PushCons.ANDROID_PACKAGE_NAME);
                        request.setTitle("入围栏告警");
                        request.setContent(details.textValue());
                        request.setToOrNumber(orNumber);
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
            return GeoUtil.contains(new Gson().toJson(geofence.getPointList()), new LatLon(latitude, longitude));
        } else {
            throw new RuntimeException("Unsupported IoEGeofenceDO type: " + geofence.getType());
        }
    }

}
