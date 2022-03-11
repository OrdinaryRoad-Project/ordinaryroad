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

package tech.ordinaryroad.im.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.im.constant.ServiceNameCons;
import tech.ordinaryroad.im.dto.ImMsgDTO;
import tech.ordinaryroad.im.request.ImMsgHistoryRequest;
import tech.ordinaryroad.im.request.ImMsgQueryRequest;

/**
 * 即时通信 消息API
 *
 * @author mjz
 * @date 2022/1/21
 */
@Api("即时通信 消息API")
@FeignClient(name = ServiceNameCons.SERVICE_NAME, contextId = "iImMsgApi")
public interface IImMsgApi {

    @PostMapping("/msg/list")
    Result<PageInfo<ImMsgDTO>> list(@RequestBody @Validated ImMsgQueryRequest request);

    @PostMapping("/msg/history")
    Result<PageInfo<ImMsgDTO>> history(@RequestBody @Validated ImMsgHistoryRequest request);

}
