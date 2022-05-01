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
package tech.ordinaryroad.ioe.utis;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import com.github.pagehelper.PageInfo;
import org.thingsboard.server.common.data.alarm.AlarmSearchStatus;
import org.thingsboard.server.common.data.alarm.AlarmSeverity;
import org.thingsboard.server.common.data.page.PageData;
import org.thingsboard.server.common.data.page.PageLink;
import org.thingsboard.server.common.data.page.SortOrder;
import org.thingsboard.server.common.data.page.TimePageLink;
import org.thingsboard.server.common.data.query.AlarmDataPageLink;
import tech.ordinaryroad.commons.core.base.dto.BaseDTO;
import tech.ordinaryroad.ioe.api.request.BaseIoEQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEAlarmDataQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoERpcRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2022/3/26
 */
public class IoEUtils {

    public static PageLink requestToPageLink(BaseIoEQueryRequest request) {
        final String textSearch = request.getTextSearch();

        final Integer offset = request.getOffset();
        final Integer limit = request.getLimit();
        final int page = offset / limit;

        final SortOrder sortOrder;
        final String orderByAscProperty = ArrayUtil.get(request.getOrderBy(), 0);
        final String orderByDescProperty = ArrayUtil.get(request.getOrderByDesc(), 0);
        if (StrUtil.isNotBlank(orderByAscProperty)) {
            sortOrder = new SortOrder(orderByAscProperty, SortOrder.Direction.ASC);
        } else if (StrUtil.isNotBlank(orderByDescProperty)) {
            sortOrder = new SortOrder(orderByDescProperty, SortOrder.Direction.DESC);
        } else {
            sortOrder = null;
        }
        return new PageLink(limit, page, textSearch, sortOrder);
    }

    public static TimePageLink requestToTimePageLink(BaseIoEQueryRequest request) {
        final String textSearch = request.getTextSearch();

        final Integer offset = request.getOffset();
        final Integer limit = request.getLimit();
        final int page = offset / limit;

        final SortOrder sortOrder;
        final String orderByAscProperty = ArrayUtil.get(request.getOrderBy(), 0);
        final String orderByDescProperty = ArrayUtil.get(request.getOrderByDesc(), 0);
        if (StrUtil.isNotBlank(orderByAscProperty)) {
            sortOrder = new SortOrder(orderByAscProperty, SortOrder.Direction.ASC);
        } else if (StrUtil.isNotBlank(orderByDescProperty)) {
            sortOrder = new SortOrder(orderByDescProperty, SortOrder.Direction.DESC);
        } else {
            sortOrder = null;
        }

        Long startTime = null;
        if (request.getStartTime() != null) {
            startTime = LocalDateTimeUtil.toEpochMilli(request.getStartTime());
        }

        Long endTime = null;
        if (request.getEndTime() != null) {
            endTime = LocalDateTimeUtil.toEpochMilli(request.getEndTime());
        }

        return new TimePageLink(limit, page, textSearch, sortOrder, startTime, endTime);
    }

    public static AlarmDataPageLink requestToAlarmDataPageLink(IoEAlarmDataQueryRequest request) {
        final String textSearch = request.getTextSearch();

        final Integer offset = request.getOffset();
        final Integer limit = request.getLimit();
        final int page = offset / limit;

        final List<String> searchStatusList = request.getSearchStatusList();
        final List<AlarmSearchStatus> alarmSearchStatusList = new ArrayList<>(CollUtil.size(searchStatusList));
        if (CollUtil.isNotEmpty(searchStatusList)) {
            CollUtil.forEach(searchStatusList, (value, index) -> alarmSearchStatusList.add(AlarmSearchStatus.valueOf(value)));
        }

        final List<String> severityList = request.getSeverityList();
        final List<AlarmSeverity> alarmSeverityList = new ArrayList<>(CollUtil.size(severityList));
        if (CollUtil.isNotEmpty(severityList)) {
            CollUtil.forEach(severityList, (value, index) -> alarmSeverityList.add(AlarmSeverity.valueOf(value)));
        }

        final AlarmDataPageLink alarmDataPageLink = new AlarmDataPageLink();
        alarmDataPageLink.setPageSize(limit);
        alarmDataPageLink.setPage(page);
        alarmDataPageLink.setTextSearch(textSearch);
        alarmDataPageLink.setSortOrder(null);
        alarmDataPageLink.setDynamic(BooleanUtil.isTrue(request.getDynamic()));
        alarmDataPageLink.setSearchPropagatedAlarms(BooleanUtil.isTrue(request.getSearchPropagatedAlarms()));
        alarmDataPageLink.setSearchPropagatedAlarms(BooleanUtil.isTrue(request.getSearchPropagatedAlarms()));
        final Long startTs = request.getStartTs();
        if (startTs != null) {
            alarmDataPageLink.setStartTs(startTs);
        }
        final Long endTs = request.getEndTs();
        if (endTs != null) {
            alarmDataPageLink.setStartTs(endTs);
        }
        final Long timeWindow = request.getTimeWindow();
        if (timeWindow != null) {
            alarmDataPageLink.setTimeWindow(timeWindow);
        }
        alarmDataPageLink.setTypeList(request.getTypeList());
        alarmDataPageLink.setStatusList(alarmSearchStatusList);
        alarmDataPageLink.setSeverityList(alarmSeverityList);

        return alarmDataPageLink;
    }

    public static JsonNode rpcRequestToJsonNode(IoERpcRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.set("method", TextNode.valueOf(request.getMethod()));

        ObjectNode paramsObjectNode = objectMapper.createObjectNode();
        objectNode.set("params", paramsObjectNode);

        objectNode.set("timeout", LongNode.valueOf(request.getTimeout()));

        Long expirationTime = request.getExpirationTime();
        if (expirationTime != null) {
            objectNode.set("expirationTime", LongNode.valueOf(expirationTime));
        }

        objectNode.set("persistent", BooleanNode.valueOf(request.getPersistent()));

        Integer retries = request.getRetries();
        if (retries != null) {
            objectNode.set("retries", IntNode.valueOf(retries));
        }

        ObjectNode additionalInfoObjectNode = objectMapper.createObjectNode();
        objectNode.set("additionalInfo", additionalInfoObjectNode);

        return objectNode;
    }

    public static <T, DTO extends BaseDTO> PageInfo<DTO> pageDataToPageInfo(PageLink pageLink, PageData<T> pageData, Function<T, DTO> mapper) {
        return pageDataToPageInfo(pageLink.getPage() + 1, pageLink.getPageSize(), pageData, mapper);
    }

    /**
     * @param pageNum  从1开始
     * @param pageSize
     * @param pageData
     * @param mapper
     * @param <T>
     * @param <DTO>
     * @return
     */
    public static <T, DTO extends BaseDTO> PageInfo<DTO> pageDataToPageInfo(Integer pageNum, Integer pageSize, PageData<T> pageData, Function<T, DTO> mapper) {
        final List<DTO> dtoList = pageData.getData().stream().map(mapper).collect(Collectors.toList());

        PageInfo<DTO> pageInfo = new PageInfo<>();
        pageInfo.setList(dtoList);
        pageInfo.setTotal(pageData.getTotalElements());
        pageInfo.setHasNextPage(pageData.hasNext());
        pageInfo.setPages(pageData.getTotalPages());
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.calcByNavigatePages(pageData.getTotalPages());

        return pageInfo;
    }

}
