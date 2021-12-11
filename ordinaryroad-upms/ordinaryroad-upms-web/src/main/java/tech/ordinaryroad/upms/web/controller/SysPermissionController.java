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
import tech.ordinaryroad.upms.api.ISysPermissionApi;
import tech.ordinaryroad.upms.dto.SysPermissionDTO;
import tech.ordinaryroad.upms.facade.ISysPermissionFacade;
import tech.ordinaryroad.upms.request.SysPermissionQueryRequest;
import tech.ordinaryroad.upms.request.SysPermissionSaveRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2021/11/8
 */
@RequiredArgsConstructor
@RestController
public class SysPermissionController implements ISysPermissionApi {

    private final ISysPermissionFacade sysPermissionFacade;

    @Override
    public Result<SysPermissionDTO> create(@Validated @RequestBody SysPermissionSaveRequest request) {
        return sysPermissionFacade.create(request);
    }

    @Override
    public Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request) {
        return sysPermissionFacade.delete(request);
    }

    @Override
    public Result<SysPermissionDTO> update(@Validated @RequestBody SysPermissionSaveRequest request) {
        return sysPermissionFacade.update(request);
    }

    @Override
    public Result<SysPermissionDTO> findById(@RequestBody SysPermissionQueryRequest request) {
        return sysPermissionFacade.findById(request);
    }

    @Override
    public Result<SysPermissionDTO> findByForeignColumn(@RequestBody SysPermissionQueryRequest request) {
        return sysPermissionFacade.findByForeignColumn(request);
    }

    @Override
    public Result<List<SysPermissionDTO>> findAllByIds(@RequestBody BaseQueryRequest request) {
        return sysPermissionFacade.findAllByIds(request);
    }

    @Override
    public Result<List<SysPermissionDTO>> findAll(@RequestBody SysPermissionQueryRequest request) {
        return sysPermissionFacade.findAll(request);
    }

    @Override
    public Result<List<SysPermissionDTO>> findAllByForeignColumn(@RequestBody SysPermissionQueryRequest request) {
        return sysPermissionFacade.findAllByForeignColumn(request);
    }

    @Override
    public Result<PageInfo<SysPermissionDTO>> list(@RequestBody SysPermissionQueryRequest request) {
        return sysPermissionFacade.list(request);
    }
}
