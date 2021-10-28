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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.api.ISysUserApi;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.facade.ISysUserFacade;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;
import tech.ordinaryroad.upms.request.SysUserSaveRequest;
import tech.ordinaryroad.upms.request.SysUserUpdatePasswordRequest;
import tech.ordinaryroad.upms.request.SysUserUpdateUsernameRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2021/10/27
 */
@RestController
public class SysUserController implements ISysUserApi {

    @Autowired
    private ISysUserFacade sysUserFacade;

    @Override
    public Result<SysUserDTO> create(@RequestBody @Validated SysUserSaveRequest saveRequest) {
        return sysUserFacade.create(saveRequest);
    }

    @Override
    public Result<SysUserDTO> findById(@RequestBody @Validated SysUserQueryRequest request) {
        return sysUserFacade.findById(request);
    }

    @Override
    public Result<List<SysUserDTO>> findAll(@RequestBody @Validated SysUserQueryRequest request) {
        return sysUserFacade.selectAll(request);
    }

    @Override
    public Result<PageInfo<SysUserDTO>> list(@RequestBody @Validated SysUserQueryRequest request) {
        return sysUserFacade.list(request);
    }

    @Override
    public Result<SysUserDTO> update(@RequestBody @Validated SysUserSaveRequest request) {
        return sysUserFacade.update(request);
    }

    @Override
    public Result<Boolean> delete(@RequestBody @Validated BaseDeleteRequest request) {
        return sysUserFacade.delete(request);
    }

    @Override
    public Result<Boolean> updateUsername(@RequestBody @Validated SysUserUpdateUsernameRequest request) {
        return sysUserFacade.updateUsername(request);
    }

    @Override
    public Result<Boolean> updatePassword(@RequestBody @Validated SysUserUpdatePasswordRequest request) {
        return sysUserFacade.updatePassword(request);
    }

}
