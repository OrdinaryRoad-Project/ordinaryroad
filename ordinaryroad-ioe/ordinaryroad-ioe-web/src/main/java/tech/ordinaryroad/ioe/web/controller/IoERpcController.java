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
package tech.ordinaryroad.ioe.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.thingsboard.service.OrThingsBoardRpcService;
import tech.ordinaryroad.ioe.api.api.IIoERpcApi;
import tech.ordinaryroad.ioe.api.request.IoERpcRequest;
import tech.ordinaryroad.ioe.utis.IoEUtils;

import javax.validation.constraints.NotBlank;

/**
 * @author mjz
 * @date 2022/4/18
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class IoERpcController implements IIoERpcApi {

    private final OrThingsBoardRpcService thingsBoardRpcService;

    @Override
    public Result<Void> oneWay(@PathVariable @Validated @NotBlank String deviceId, @RequestBody @Validated IoERpcRequest request) {
        JsonNode jsonNode = IoEUtils.rpcRequestToJsonNode(request);
        thingsBoardRpcService.oneWay(deviceId, jsonNode);
        return Result.success();
    }

    @Override
    public Result<JSONObject> twoWay(@PathVariable @Validated @NotBlank String deviceId, @RequestBody @Validated IoERpcRequest request) {
        JsonNode jsonNode = IoEUtils.rpcRequestToJsonNode(request);
        return Result.success(JSON.parseObject(thingsBoardRpcService.twoWay(deviceId, jsonNode).toString()));
    }
}
