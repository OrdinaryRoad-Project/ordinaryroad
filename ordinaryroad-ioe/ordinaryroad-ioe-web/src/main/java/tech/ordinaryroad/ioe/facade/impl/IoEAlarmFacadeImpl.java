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

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.alarm.AlarmInfo;
import org.thingsboard.server.common.data.alarm.AlarmSearchStatus;
import org.thingsboard.server.common.data.alarm.AlarmStatus;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.id.EntityIdFactory;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.TimePageLink;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.thingsboard.service.OrThingsBoardAlarmService;
import tech.ordinaryroad.ioe.api.dto.IoEAlarmInfoDTO;
import tech.ordinaryroad.ioe.api.request.IoEAlarmInfoQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEEntityAlarmInfoQueryRequest;
import tech.ordinaryroad.ioe.facade.IIoEAlarmFacade;
import tech.ordinaryroad.ioe.mapstruct.IoEAlarmMapStruct;
import tech.ordinaryroad.ioe.service.IoEService;
import tech.ordinaryroad.ioe.utis.IoEUtils;

/**
 * @author mjz
 * @date 2022/4/11
 */
@RequiredArgsConstructor
@Component
public class IoEAlarmFacadeImpl implements IIoEAlarmFacade {

    private final OrThingsBoardAlarmService thingsBoardAlarmService;
    private final IoEAlarmMapStruct mapStruct;
    private final IoEService ioEService;

    @Override
    public Result<Void> delete(String alarmId) {
        try {
            thingsBoardAlarmService.delete(alarmId);
            return Result.success();
        } catch (Exception e) {
            return Result.fail(e.getLocalizedMessage());
        }
    }

    @Override
    public Result<Void> ack(String alarmId) {
        try {
            thingsBoardAlarmService.ack(alarmId);
            return Result.success();
        } catch (Exception e) {
            return Result.fail(e.getLocalizedMessage());
        }
    }

    @Override
    public Result<Void> clear(String alarmId) {
        try {
            thingsBoardAlarmService.clear(alarmId);
            return Result.success();
        } catch (Exception e) {
            return Result.fail(e.getLocalizedMessage());
        }
    }

    @Override
    public Result<PageInfo<IoEAlarmInfoDTO>> list(IoEAlarmInfoQueryRequest request) {
        final String userId = ioEService.getUserId();

        final TimePageLink timePageLink = IoEUtils.requestToTimePageLink(request);

        final String alarmSearchStatus = request.getSearchStatus();
        AlarmSearchStatus searchStatus = null;
        if (alarmSearchStatus != null) {
            searchStatus = AlarmSearchStatus.valueOf(alarmSearchStatus);
        }

        final String alarmStatus = request.getStatus();
        AlarmStatus status = null;
        if (alarmStatus != null) {
            status = AlarmStatus.valueOf(alarmStatus);
        }

        final PageData<AlarmInfo> pageData = thingsBoardAlarmService.getAllAlarms(userId, searchStatus, status, timePageLink, request.getFetchOriginator());
        final PageInfo<IoEAlarmInfoDTO> pageInfo = IoEUtils.pageDataToPageInfo(timePageLink, pageData, mapStruct::transfer);

        return Result.success(pageInfo);
    }

    @Override
    public Result<PageInfo<IoEAlarmInfoDTO>> listEntity(IoEEntityAlarmInfoQueryRequest request) {
        final EntityId entityId = EntityIdFactory.getByTypeAndId(request.getEntityType(), request.getEntityId());

        final TimePageLink timePageLink = IoEUtils.requestToTimePageLink(request);

        final PageData<AlarmInfo> pageData = thingsBoardAlarmService.getAlarms(entityId, AlarmSearchStatus.valueOf(request.getSearchStatus()), AlarmStatus.valueOf(request.getStatus()), timePageLink, request.getFetchOriginator());
        final PageInfo<IoEAlarmInfoDTO> pageInfo = IoEUtils.pageDataToPageInfo(timePageLink, pageData, mapStruct::transfer);

        return Result.success(pageInfo);
    }

}
