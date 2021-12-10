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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.dto.SysPermissionDTO;
import tech.ordinaryroad.upms.entity.SysPermissionDO;
import tech.ordinaryroad.upms.facade.ISysPermissionFacade;
import tech.ordinaryroad.upms.mapstruct.SysPermissionMapStruct;
import tech.ordinaryroad.upms.request.SysPermissionQueryRequest;
import tech.ordinaryroad.upms.request.SysPermissionSaveRequest;
import tech.ordinaryroad.upms.service.SysPermissionService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/11/8
 */
@RequiredArgsConstructor
@Component
public class SysPermissionFacadeImpl implements ISysPermissionFacade {

    private final SysPermissionService sysPermissionService;
    private final SysPermissionMapStruct objMapStruct;

    @Override
    public Result<SysPermissionDTO> create(SysPermissionSaveRequest request) {
        SysPermissionDO sysPermissionDO = objMapStruct.transfer(request);

        // 唯一性校验
        String permissionCode = request.getPermissionCode();
        Optional<SysPermissionDO> byPermissionCode = sysPermissionService.findByPermissionCode(permissionCode);
        if (byPermissionCode.isPresent()) {
            return Result.fail(StatusCode.CODE_ALREADY_EXIST);
        }

        return Result.success(objMapStruct.transfer(sysPermissionService.createSelective(sysPermissionDO)));
    }

    @Override
    public Result<Boolean> delete(BaseDeleteRequest request) {
        return Result.success(sysPermissionService.delete(request.getUuid()));
    }

    @Override
    public Result<SysPermissionDTO> update(SysPermissionSaveRequest request) {
        String uuid = request.getUuid();
        if (StrUtil.isBlank(uuid)) {
            return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
        }

        SysPermissionDO byId = sysPermissionService.findById(uuid);
        if (Objects.isNull(byId)) {
            return Result.fail(StatusCode.DATA_NOT_EXIST);
        }

        String newPermissionCode = request.getPermissionCode();
        String permissionCode = byId.getPermissionCode();
        if (!Objects.equals(newPermissionCode, permissionCode)) {
            if (sysPermissionService.findByPermissionCode(newPermissionCode).isPresent()) {
                return Result.fail(StatusCode.CODE_ALREADY_EXIST);
            }
        }

        SysPermissionDO transfer = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysPermissionService.updateSelective(transfer)));
    }

    @Override
    public Result<SysPermissionDTO> findById(SysPermissionQueryRequest request) {
        SysPermissionDO sysPermissionDO = objMapStruct.transfer(request);
        SysPermissionDO byId = sysPermissionService.findById(sysPermissionDO);
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @Override
    public Result<List<SysPermissionDTO>> findAllByIds(BaseQueryRequest request) {
        List<String> uuids = request.getUuids();
        if (CollUtil.isEmpty(uuids)) {
            return Result.success(Collections.emptyList());
        }
        List<SysPermissionDO> all = sysPermissionService.findIds(SysPermissionDO.class, uuids);
        List<SysPermissionDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());
        return Result.success(list);
    }

    @Override
    public Result<List<SysPermissionDTO>> findAll(SysPermissionQueryRequest request) {
        SysPermissionDO sysPermissionDO = objMapStruct.transfer(request);

        List<SysPermissionDO> all = sysPermissionService.findAll(sysPermissionDO);
        List<SysPermissionDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<PageInfo<SysPermissionDTO>> list(SysPermissionQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysPermissionDO sysPermissionDO = objMapStruct.transfer(request);
        Page<SysPermissionDO> all = (Page<SysPermissionDO>) sysPermissionService.findAll(sysPermissionDO);

        PageInfo<SysPermissionDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }

    @Override
    public Result<List<SysPermissionDTO>> findAllByUserUuid(SysPermissionQueryRequest request) {
        String userUuid = request.getUserUuid();
        if (StrUtil.isBlank(userUuid)) {
            return Result.fail(StatusCode.PARAM_IS_BLANK);
        }

        List<SysPermissionDO> byIds = sysPermissionService.findAllByUserUuid(userUuid);
        List<SysPermissionDTO> collect = byIds.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(collect);
    }

    @Override
    public Result<List<SysPermissionDTO>> findAllByRoleUuid(SysPermissionQueryRequest request) {
        String roleUuid = request.getRoleUuid();
        if (StrUtil.isBlank(roleUuid)) {
            return Result.fail(StatusCode.PARAM_IS_BLANK);
        }

        List<SysPermissionDO> byIds = sysPermissionService.findAllByRoleUuid(roleUuid);
        List<SysPermissionDTO> collect = byIds.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(collect);
    }
}
