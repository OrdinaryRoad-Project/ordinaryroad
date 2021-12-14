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
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.api.ISysRequestPathApi;
import tech.ordinaryroad.upms.dto.SysRequestPathDTO;
import tech.ordinaryroad.upms.facade.ISysRequestPathFacade;
import tech.ordinaryroad.upms.request.SysRequestPathQueryRequest;
import tech.ordinaryroad.upms.request.SysRequestPathSaveRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2021/11/9
 */
@RequiredArgsConstructor
@RestController
public class SysRequestPathController implements ISysRequestPathApi {

    private final ISysRequestPathFacade sysRequestPathFacade;

    @Override
    public Result<SysRequestPathDTO> create(@Validated @RequestBody SysRequestPathSaveRequest request) {
        return sysRequestPathFacade.create(request);
    }

    @Override
    public Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request) {
        return sysRequestPathFacade.delete(request);
    }

    @Override
    public Result<SysRequestPathDTO> update(@Validated @RequestBody SysRequestPathSaveRequest request) {
        return sysRequestPathFacade.update(request);
    }

    @Override
    public Result<SysRequestPathDTO> findById(@RequestBody SysRequestPathQueryRequest request) {
        return sysRequestPathFacade.findById(request);
    }

    @Override
    public Result<SysRequestPathDTO> findByUniqueColumn(@RequestBody SysRequestPathQueryRequest request) {
        return sysRequestPathFacade.findByUniqueColumn(request);
    }

    @Override
    public Result<List<SysRequestPathDTO>> findAllByPermissionUuids(@RequestBody SysRequestPathQueryRequest request) {
        return sysRequestPathFacade.findAllByPermissionUuids(request);
    }

    @Override
    public Result<List<SysRequestPathDTO>> findAllByUserUuid(@RequestBody SysRequestPathQueryRequest request) {
        return sysRequestPathFacade.findAllByUserUuid(request);
    }

    @Override
    public Result<List<SysRequestPathDTO>> findAllByIds(@RequestBody BaseQueryRequest request) {
        return sysRequestPathFacade.findAllByIds(request);
    }

    @Override
    public Result<List<SysRequestPathDTO>> findAll(@RequestBody SysRequestPathQueryRequest request) {
        return sysRequestPathFacade.findAll(request);
    }

    @Override
    public Result<PageInfo<SysRequestPathDTO>> list(@RequestBody SysRequestPathQueryRequest request) {
        return sysRequestPathFacade.list(request);
    }
}
