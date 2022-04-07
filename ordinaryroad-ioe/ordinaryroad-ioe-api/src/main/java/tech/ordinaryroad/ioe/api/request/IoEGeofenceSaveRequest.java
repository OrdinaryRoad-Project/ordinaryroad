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
package tech.ordinaryroad.ioe.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import tech.ordinaryroad.commons.core.base.request.save.BaseSaveRequest;
import tech.ordinaryroad.ioe.api.LatLon;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author mjz
 * @date 2022/4/5
 */
@Getter
@Setter
@ApiModel
public class IoEGeofenceSaveRequest extends BaseSaveRequest {

    private static final long serialVersionUID = 8666332804314537275L;

    @ApiModelProperty("ThingsBoard平台设备Id")
    @Size(min = 36, max = 36, message = "ThingsBoard平台设备Id长度必须为36")
    private String deviceId;

    @ApiModelProperty("名称")
    @Size(min = 1, max = 20, message = "名称长度1-20")
    private String name;

    @ApiModelProperty(value = "围栏类型，目前仅支持：0 1", allowableValues = "0,1")
    private Integer type;

    @ApiModelProperty("半径（米）")
    @Range(min = 100, max = 5000, message = "半径范围为100-5000")
    private Integer radius;

    @ApiModelProperty("点列表")
    @NotEmpty(message = "点列表不能为空")
    private List<LatLon> pointList;

}
