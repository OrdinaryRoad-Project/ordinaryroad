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
package tech.ordinaryroad.ioe.entity;

import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.mybatis.model.BaseDO;
import tech.ordinaryroad.ioe.api.LatLon;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Table;
import java.util.List;

/**
 * 地理围栏表
 *
 * @author mjz
 * @date 2022/4/5
 */
@Getter
@Setter
@Table(name = "ioe_geofence")
public class IoEGeofenceDO extends BaseDO {

    private static final long serialVersionUID = 1582044757835050587L;

    /**
     * ThingsBoard平台设备Id
     */
    private String deviceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 围栏类型，目前仅支持：0 1
     * <p>
     * 0    TYPE_ROUND
     * 1    TYPE_POLYGON
     * 2    TYPE_AMAPPOI
     * 3    TYPE_DISTRICT
     */
    private Integer type;

    /**
     * 半径（米） type = 0
     */
    private Integer radius;

    /**
     * 点列表
     * type = 0: 中心点坐标
     * type = 1：多边形端点
     */
    @ColumnType(typeHandler = LatLonListTypeHandler.class)
    private List<LatLon> pointList;

    /**
     * 严重程度：CRITICAL, MAJOR, MINOR, WARNING, INDETERMINATE
     *
     * @see org.thingsboard.server.common.data.alarm.AlarmSeverity
     */
    private String severity;

}
