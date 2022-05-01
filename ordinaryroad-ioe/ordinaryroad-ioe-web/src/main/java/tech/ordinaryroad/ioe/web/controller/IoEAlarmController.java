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

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.ioe.api.api.IIoEAlarmApi;
import tech.ordinaryroad.ioe.api.dto.IoEAlarmInfoDTO;
import tech.ordinaryroad.ioe.api.request.IoEAlarmInfoQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEEntityAlarmInfoQueryRequest;
import tech.ordinaryroad.ioe.facade.IIoEAlarmFacade;

import javax.validation.constraints.NotBlank;

/**
 * @author mjz
 * @date 2022/4/11
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class IoEAlarmController implements IIoEAlarmApi {

    private final IIoEAlarmFacade alarmFacade;

    @Override
    public Result<Void> delete(@PathVariable @Validated @NotBlank String id) {
        return alarmFacade.delete(id);
    }

    @Override
    public Result<Void> ack(@PathVariable @Validated @NotBlank String id) {
        return alarmFacade.ack(id);
    }

    @Override
    public Result<Void> clear(@PathVariable @Validated @NotBlank String id) {
        return alarmFacade.clear(id);
    }

    @Override
    public Result<PageInfo<IoEAlarmInfoDTO>> list(@RequestBody @Validated IoEAlarmInfoQueryRequest request) {
        return alarmFacade.list(request);
    }

    @Override
    public Result<PageInfo<IoEAlarmInfoDTO>> listEntity(@RequestBody @Validated IoEEntityAlarmInfoQueryRequest request) {
        return alarmFacade.listEntity(request);
    }

}
