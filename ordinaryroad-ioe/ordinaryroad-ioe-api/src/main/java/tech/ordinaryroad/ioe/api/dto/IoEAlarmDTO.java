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
package tech.ordinaryroad.ioe.api.dto;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author mjz
 * @date 2022/4/11
 * @see org.thingsboard.server.common.data.alarm.Alarm
 */
@Data
@ApiModel
public class IoEAlarmDTO extends BaseIoEDTO {

    private static final long serialVersionUID = -1930972367515319579L;

    private String name;

    private String type;

    private String severity;

    private String status;

    private Long startTs;

    private Long endTs;

    private Long ackTs;

    private Long clearTs;

    private JsonNode details;

    private Boolean propagate;

    private Boolean propagateToOwner;

    private Boolean propagateToTenant;

    private List<String> propagateRelationTypes;

}
