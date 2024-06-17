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
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.constant.UpmsConstants;
import tech.ordinaryroad.upms.dto.SysDictItemDTO;
import tech.ordinaryroad.upms.request.SysDictItemQueryRequest;
import tech.ordinaryroad.upms.request.SysDictItemSaveRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2022/1/5
 */
@Tag(name = "字典项API")
@HttpExchange(UpmsConstants.SERVICE_URL)
public interface ISysDictItemApi {

    @PostExchange(value = "/dict_item/create")
    Result<SysDictItemDTO> create(@Validated @RequestBody SysDictItemSaveRequest request);

    @PostExchange(value = "/dict_item/delete")
    Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request);

    @PostExchange(value = "/dict_item/update")
    Result<SysDictItemDTO> update(@Validated @RequestBody SysDictItemSaveRequest request);

    @PostExchange(value = "/dict_item/find/id")
    Result<SysDictItemDTO> findById(@RequestBody SysDictItemQueryRequest request);

    @PostExchange(value = "/dict_item/detail")
    Result<SysDictItemDTO> detail(@RequestBody SysDictItemQueryRequest request);

    @PostExchange(value = "/dict_item/find_all")
    Result<List<SysDictItemDTO>> findAll(@RequestBody SysDictItemQueryRequest request);

    @PostExchange(value = "/dict_item/find_all/foreign")
    Result<List<SysDictItemDTO>> findAllByForeignColumn(@RequestBody SysDictItemQueryRequest request);

    @PostExchange(value = "/dict_item/list")
    Result<PageInfo<SysDictItemDTO>> list(@RequestBody SysDictItemQueryRequest request);
}
