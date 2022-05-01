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
package tech.ordinaryroad.ioe.api.api;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.ioe.api.constant.ServiceNameCons;
import tech.ordinaryroad.ioe.api.request.IoERpcRequest;

import javax.validation.constraints.NotBlank;

/**
 * @author mjz
 * @date 2022/4/18
 */
@Api(value = "IoE Rpc API")
@FeignClient(name = ServiceNameCons.SERVICE_NAME, contextId = "iIoERpcApi")
public interface IIoERpcApi {

    @PostMapping("/rpc/oneway/{deviceId}")
    Result<Void> oneWay(@PathVariable @Validated @NotBlank String deviceId, @RequestBody @Validated IoERpcRequest request);

    @PostMapping("/rpc/twoway/{deviceId}")
    Result<JSONObject> twoWay(@PathVariable @Validated @NotBlank String deviceId, @RequestBody @Validated IoERpcRequest request);

}
