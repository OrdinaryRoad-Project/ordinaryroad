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
package tech.ordinaryroad.upms.facade.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.entity.SysUserDO;
import tech.ordinaryroad.upms.facade.ISysUserFacade;
import tech.ordinaryroad.upms.mapstruct.SysUserMapStruct;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;
import tech.ordinaryroad.upms.request.SysUserSaveRequest;
import tech.ordinaryroad.upms.request.SysUserUpdatePasswordRequest;
import tech.ordinaryroad.upms.request.SysUserUpdateUsernameRequest;
import tech.ordinaryroad.upms.service.SysUserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/10/27
 */
@Component
public class SysUserFacadeImpl implements ISysUserFacade {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserMapStruct objMapStruct;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<SysUserDTO> create(SysUserSaveRequest request) {
        // 校验用户名
        String username = request.getUsername();
        Optional<SysUserDO> byName = sysUserService.findByUsername(username);
        if (byName.isPresent()) {
            return Result.fail(StatusCode.USERNAME_ALREADY_EXIST);
        }

        SysUserDO sysUserDO = objMapStruct.transfer(request);
        if (StrUtil.isBlank(sysUserDO.getPassword())) {
            // TODO 默认密码
            sysUserDO.setPassword("123456");
        }
        return Result.success(objMapStruct.transfer(sysUserService.createSelective(sysUserDO)));
    }

    @Override
    public Result<SysUserDTO> findById(SysUserQueryRequest request) {
        SysUserDO sysUserDO = objMapStruct.transfer(request);
        SysUserDO byId = sysUserService.findById(sysUserDO);
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @Override
    public Result<List<SysUserDTO>> findAll(SysUserQueryRequest request) {
        SysUserDO sysUserDO = objMapStruct.transfer(request);

        List<SysUserDO> all = sysUserService.findAll(sysUserDO);
        List<SysUserDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<PageInfo<SysUserDTO>> list(SysUserQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysUserDO sysUserDO = objMapStruct.transfer(request);
        Page<SysUserDO> all = (Page<SysUserDO>) sysUserService.findAll(sysUserDO);

        PageInfo<SysUserDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }

    @Override
    public Result<SysUserDTO> update(SysUserSaveRequest request) {
        // TODO 更新，只允许管理员
        SysUserDO sysUserDO = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysUserService.updateSelective(sysUserDO)));
    }

    @Override
    public Result<Boolean> delete(BaseDeleteRequest request) {
        // TODO 删除，只允许管理员
        return Result.success(sysUserService.delete(request.getUuid()));
    }

    @Override
    public Result<Boolean> updateUsername(SysUserUpdateUsernameRequest request) {
        // TODO 获取当前登录用户
        SysUserDO sysUserDO = objMapStruct.transfer(request);
        return Result.success(sysUserService.doUpdateSelective(sysUserDO));
    }

    @Override
    public Result<Boolean> updatePassword(SysUserUpdatePasswordRequest request) {
        // TODO 获取当前登录用户
        // TODO 密码加密
        SysUserDO sysUserDO = objMapStruct.transfer(request);
        return Result.success(sysUserService.doUpdateSelective(sysUserDO));
    }

    @Override
    public Result<SysUserDTO> findByUniqueColumn(SysUserQueryRequest request) {
        Optional<SysUserDO> optional;
        String orNumber = request.getOrNumber();
        String username = request.getUsername();
        if (StrUtil.isNotBlank(orNumber)) {
            optional = sysUserService.findByOrNumber(orNumber);
        } else if (StrUtil.isNotBlank(username)) {
            optional = sysUserService.findByUsername(username);
        } else {
            return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
        }

        return optional.map(data -> Result.success(objMapStruct.transfer(data))).orElseGet(Result::fail);
    }

}
