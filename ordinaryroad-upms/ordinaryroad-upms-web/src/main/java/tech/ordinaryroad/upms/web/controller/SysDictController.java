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
package tech.ordinaryroad.upms.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.api.ISysDictApi;
import tech.ordinaryroad.upms.dto.SysDictDTO;
import tech.ordinaryroad.upms.facade.ISysDictFacade;
import tech.ordinaryroad.upms.request.SysDictQueryRequest;
import tech.ordinaryroad.upms.request.SysDictSaveRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2022/1/5
 */
@RequiredArgsConstructor
@RestController
public class SysDictController implements ISysDictApi {

    private final ISysDictFacade sysDictFacade;

    @Override
    public Result<SysDictDTO> create(@RequestBody @Validated SysDictSaveRequest request) {
        return sysDictFacade.create(request);
    }

    @Override
    public Result<Boolean> delete(@RequestBody @Validated BaseDeleteRequest request) {
        return sysDictFacade.delete(request);
    }

    @Override
    public Result<SysDictDTO> update(@RequestBody @Validated SysDictSaveRequest request) {
        return sysDictFacade.update(request);
    }

    @Override
    public Result<SysDictDTO> findById(@RequestBody SysDictQueryRequest request) {
        return sysDictFacade.findById(request);
    }

    @Override
    public Result<SysDictDTO> findByUniqueColumn(@RequestBody SysDictQueryRequest request) {
        return sysDictFacade.findByUniqueColumn(request);
    }

    @Override
    public Result<List<SysDictDTO>> findAll(@RequestBody SysDictQueryRequest request) {
        return sysDictFacade.findAll(request);
    }

    @Override
    public Result<PageInfo<SysDictDTO>> list(@RequestBody SysDictQueryRequest request) {
        return sysDictFacade.list(request);
    }
}
