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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.dto.SysRequestPathDTO;
import tech.ordinaryroad.upms.entity.SysRequestPathDO;
import tech.ordinaryroad.upms.mapstruct.SysRequestPathMapStruct;
import tech.ordinaryroad.upms.request.SysRequestPathQueryRequest;
import tech.ordinaryroad.upms.request.SysRequestPathSaveRequest;
import tech.ordinaryroad.upms.service.SysDtoService;
import tech.ordinaryroad.upms.service.SysPermissionService;
import tech.ordinaryroad.upms.service.SysRequestPathService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/11/9
 */
@RequiredArgsConstructor
@RestController
public class SysRequestPathController {

    private final SysRequestPathService sysRequestPathService;
    private final SysRequestPathMapStruct objMapStruct;
    private final SysPermissionService sysPermissionService;
    private final SysDtoService sysDtoService;

    @PostMapping(value = "/request_path/create")
    public Result<SysRequestPathDTO> create(@Validated @RequestBody SysRequestPathSaveRequest request) {
        // 校验permissionUuid
        String permissionUuid = request.getPermissionUuid();
        if (StrUtil.isNotBlank(permissionUuid)) {
            if (Objects.isNull(sysPermissionService.findById(permissionUuid))) {
                return Result.fail(StatusCode.PERMISSION_NOT_EXIST);
            }
        }

        // 唯一性校验
        String path = request.getPath();
        Optional<SysRequestPathDO> byPath = sysRequestPathService.findByPath(path);
        if (byPath.isPresent()) {
            return Result.fail(StatusCode.PATH_ALREADY_EXIST);
        }
        String pathName = request.getPathName();
        Optional<SysRequestPathDO> byPathName = sysRequestPathService.findByPathName(pathName);
        if (byPathName.isPresent()) {
            return Result.fail(StatusCode.NAME_ALREADY_EXIST);
        }

        SysRequestPathDO sysRequestPathDO = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysRequestPathService.createSelective(sysRequestPathDO)));
    }

    @PostMapping(value = "/request_path/delete")
    public Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request) {
        return Result.success(sysRequestPathService.delete(request.getUuid()));
    }

    @PostMapping(value = "/request_path/update")
    public Result<SysRequestPathDTO> update(@Validated @RequestBody SysRequestPathSaveRequest request) {
        String uuid = request.getUuid();
        if (StrUtil.isBlank(uuid)) {
            return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
        }

        SysRequestPathDO byId = sysRequestPathService.findById(uuid);
        if (Objects.isNull(byId)) {
            return Result.fail(StatusCode.DATA_NOT_EXIST);
        }

        String newPath = request.getPath();
        String path = byId.getPath();
        if (!Objects.equals(newPath, path)) {
            if (sysRequestPathService.findByPath(newPath).isPresent()) {
                return Result.fail(StatusCode.PATH_ALREADY_EXIST);
            }
        }

        String newPathName = request.getPathName();
        String pathName = byId.getPathName();
        if (!Objects.equals(newPathName, pathName)) {
            if (sysRequestPathService.findByPathName(newPathName).isPresent()) {
                return Result.fail(StatusCode.PATH_NAME_ALREADY_EXIST);
            }
        }

        SysRequestPathDO transfer = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysRequestPathService.updateSelective(transfer)));
    }

    @PostMapping(value = "/request_path/find/id")
    public Result<SysRequestPathDTO> findById(@RequestBody SysRequestPathQueryRequest request) {
        SysRequestPathDO sysRequestPathDO = objMapStruct.transfer(request);
        SysRequestPathDO byId = sysRequestPathService.findById(sysRequestPathDO);
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @PostMapping(value = "/request_path/find/unique")
    public Result<SysRequestPathDTO> findByUniqueColumn(@RequestBody SysRequestPathQueryRequest request) {
        Optional<SysRequestPathDO> optional = Optional.empty();
        String path = request.getPath();
        String pathName = request.getPathName();
        if (StrUtil.isNotBlank(path)) {
            optional = sysRequestPathService.findByPath(path);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(pathName)) {
            optional = sysRequestPathService.findByPathName(pathName);
        }
        return optional.map(data -> Result.success(objMapStruct.transfer(data))).orElse(Result.fail(StatusCode.PATH_NOT_EXIST));
    }

    @PostMapping(value = "/request_path/find_all/permission_uuids")
    public Result<List<SysRequestPathDTO>> findAllByPermissionUuids(@RequestBody SysRequestPathQueryRequest request) {
        List<String> permissionUuids = request.getPermissionUuids();
        if (CollUtil.isEmpty(permissionUuids)) {
            return Result.fail(StatusCode.PARAM_IS_BLANK);
        }

        List<SysRequestPathDO> all = sysRequestPathService.findAllByPermissionUuids(permissionUuids);
        List<SysRequestPathDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @PostMapping(value = "/request_path/find_all/user_uuids")
    public Result<List<SysRequestPathDTO>> findAllByUserUuid(@RequestBody SysRequestPathQueryRequest request) {
        String userUuid = request.getUserUuid();
        if (StrUtil.isBlank(userUuid)) {
            return Result.fail(StatusCode.PARAM_IS_BLANK);
        }

        List<SysRequestPathDO> all = sysRequestPathService.findAllByUserUuid(userUuid);
        List<SysRequestPathDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @PostMapping(value = "/request_path/find_all/ids")
    public Result<List<SysRequestPathDTO>> findAllByIds(@RequestBody BaseQueryRequest request) {
        List<String> uuids = request.getUuids();
        if (CollUtil.isEmpty(uuids)) {
            return Result.success(Collections.emptyList());
        }
        List<SysRequestPathDO> all = sysRequestPathService.findIds(uuids);
        List<SysRequestPathDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());
        return Result.success(list);
    }

    @PostMapping(value = "/request_path/find_all")
    public Result<List<SysRequestPathDTO>> findAll(@RequestBody SysRequestPathQueryRequest request) {
        SysRequestPathDO sysRequestPathDO = objMapStruct.transfer(request);

        List<SysRequestPathDO> all = sysRequestPathService.findAll(sysRequestPathDO, request);
        List<SysRequestPathDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @PostMapping(value = "/request_path/list")
    public Result<PageInfo<SysRequestPathDTO>> list(@RequestBody SysRequestPathQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysRequestPathDO sysRequestPathDO = objMapStruct.transfer(request);
        Page<SysRequestPathDO> all = (Page<SysRequestPathDO>) sysRequestPathService.findAll(sysRequestPathDO, request);

        PageInfo<SysRequestPathDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, sysUserDO -> {
            SysRequestPathDTO sysRequestPathDto = objMapStruct.transfer(sysUserDO);
            sysDtoService.setPermissionDTO(sysUserDO.getPermissionUuid(), sysRequestPathDto);
            return sysRequestPathDto;
        });

        return Result.success(objectPageInfo);
    }
}
