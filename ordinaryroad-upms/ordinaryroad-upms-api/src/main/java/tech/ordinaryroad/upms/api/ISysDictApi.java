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
package tech.ordinaryroad.upms.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.constants.ServiceNameCons;
import tech.ordinaryroad.upms.dto.SysDictDTO;
import tech.ordinaryroad.upms.request.SysDictQueryRequest;
import tech.ordinaryroad.upms.request.SysDictSaveRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2022/1/5
 */
@Api(value = "字典API")
@FeignClient(name = ServiceNameCons.SERVICE_NAME, contextId = "iSysDictApi")
public interface ISysDictApi {

    @PostMapping(value = "/dict/create")
    Result<SysDictDTO> create(@Validated @RequestBody SysDictSaveRequest request);

    @PostMapping(value = "/dict/delete")
    Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request);

    @PostMapping(value = "/dict/update")
    Result<SysDictDTO> update(@Validated @RequestBody SysDictSaveRequest request);

    @PostMapping(value = "/dict/find/id")
    Result<SysDictDTO> findById(@RequestBody SysDictQueryRequest request);

    @PostMapping(value = "/dict/find/unique")
    Result<SysDictDTO> findByUniqueColumn(@RequestBody SysDictQueryRequest request);

    @PostMapping(value = "/dict/find_all")
    Result<List<SysDictDTO>> findAll(@RequestBody SysDictQueryRequest request);

    @PostMapping(value = "/dict/list")
    Result<PageInfo<SysDictDTO>> list(@RequestBody SysDictQueryRequest request);
}
