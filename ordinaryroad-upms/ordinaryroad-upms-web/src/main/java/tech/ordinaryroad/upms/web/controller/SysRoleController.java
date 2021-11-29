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
import tech.ordinaryroad.upms.api.ISysRoleApi;
import tech.ordinaryroad.upms.dto.SysRoleDTO;
import tech.ordinaryroad.upms.facade.ISysRoleFacade;
import tech.ordinaryroad.upms.request.SysRoleQueryRequest;
import tech.ordinaryroad.upms.request.SysRoleSaveRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2021/11/4
 */
@RequiredArgsConstructor
@RestController
public class SysRoleController implements ISysRoleApi {

    private final ISysRoleFacade sysRoleFacade;

    @Override
    public Result<SysRoleDTO> create(@RequestBody @Validated SysRoleSaveRequest request) {
        return sysRoleFacade.create(request);
    }

    @Override
    public Result<Boolean> delete(@RequestBody @Validated BaseDeleteRequest request) {
        return sysRoleFacade.delete(request);
    }

    @Override
    public Result<SysRoleDTO> update(@RequestBody @Validated SysRoleSaveRequest request) {
        return sysRoleFacade.update(request);
    }

    @Override
    public Result<SysRoleDTO> findById(@RequestBody SysRoleQueryRequest request) {
        return sysRoleFacade.findById(request);
    }

    @Override
    public Result<List<SysRoleDTO>> findAllByUserUuid(@RequestBody SysRoleQueryRequest request) {
        return sysRoleFacade.findAllByUserUuid(request);
    }

    @Override
    public Result<List<SysRoleDTO>> findAllByIds(@RequestBody BaseQueryRequest request) {
        return sysRoleFacade.findAllByIds(request);
    }

    @Override
    public Result<List<SysRoleDTO>> findAll(@RequestBody SysRoleQueryRequest request) {
        return sysRoleFacade.findAll(request);
    }

    @Override
    public Result<PageInfo<SysRoleDTO>> list(@RequestBody SysRoleQueryRequest request) {
        return sysRoleFacade.list(request);
    }
}
