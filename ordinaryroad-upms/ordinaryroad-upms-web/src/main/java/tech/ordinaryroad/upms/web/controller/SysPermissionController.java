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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.core.constant.CacheConstants;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.dto.SysPermissionDTO;
import tech.ordinaryroad.upms.entity.SysPermissionDO;
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
@RestController
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;
    private final SysPermissionMapStruct objMapStruct;

    @PostMapping(value = "/permission/create")
    public Result<SysPermissionDTO> create(@Validated @RequestBody SysPermissionSaveRequest request) {
        SysPermissionDO sysPermissionDO = objMapStruct.transfer(request);

        // 唯一性校验
        String permissionCode = request.getPermissionCode();
        Optional<SysPermissionDO> byPermissionCode = sysPermissionService.findByPermissionCode(permissionCode);
        if (byPermissionCode.isPresent()) {
            return Result.fail(StatusCode.CODE_ALREADY_EXIST);
        }

        return Result.success(objMapStruct.transfer(sysPermissionService.createSelective(sysPermissionDO)));
    }

    @Caching(evict = {
            /* 删除根据请求路径获取权限缓存 */
            @CacheEvict(cacheNames = CacheConstants.CACHEABLE_CACHE_NAME_PERMISSION_BY_REQUEST_PATH, allEntries = true, condition = "#result.data"),
            /* 删除根据请求路径Id获取权限缓存 */
            @CacheEvict(cacheNames = CacheConstants.CACHEABLE_CACHE_NAME_PERMISSION_BY_REQUEST_PATH_UUID, allEntries = true, condition = "#result.data"),
            /* 删除根据用户Id获取用户拥有的所有权限缓存 */
            @CacheEvict(cacheNames = CacheConstants.CACHEABLE_CACHE_NAME_PERMISSIONS_BY_USER_UUID, allEntries = true, condition = "#result.data"),
            /* 删除根据角色Id获取角色拥有的所有权限缓存 */
            @CacheEvict(cacheNames = CacheConstants.CACHEABLE_CACHE_NAME_PERMISSIONS_BY_ROLE_UUID, allEntries = true, condition = "#result.data"),
    })
    @PostMapping(value = "/permission/delete")
    public Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request) {
        return Result.success(sysPermissionService.delete(request.getUuid()));
    }

    @Caching(evict = {
            /* 删除根据请求路径获取权限缓存 */
            @CacheEvict(cacheNames = CacheConstants.CACHEABLE_CACHE_NAME_PERMISSION_BY_REQUEST_PATH, allEntries = true, condition = "#result.success"),
            /* 删除根据请求路径Id获取权限缓存 */
            @CacheEvict(cacheNames = CacheConstants.CACHEABLE_CACHE_NAME_PERMISSION_BY_REQUEST_PATH_UUID, allEntries = true, condition = "#result.success"),
            /* 删除根据用户Id获取用户拥有的所有权限缓存 */
            @CacheEvict(cacheNames = CacheConstants.CACHEABLE_CACHE_NAME_PERMISSIONS_BY_USER_UUID, allEntries = true, condition = "#result.success"),
            /* 删除根据角色Id获取角色拥有的所有权限缓存 */
            @CacheEvict(cacheNames = CacheConstants.CACHEABLE_CACHE_NAME_PERMISSIONS_BY_ROLE_UUID, allEntries = true, condition = "#result.success"),
    })
    @PostMapping(value = "/permission/update")
    public Result<SysPermissionDTO> update(@Validated @RequestBody SysPermissionSaveRequest request) {
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

    @PostMapping(value = "/permission/find/id")
    public Result<SysPermissionDTO> findById(@RequestBody SysPermissionQueryRequest request) {
        SysPermissionDO sysPermissionDO = objMapStruct.transfer(request);
        SysPermissionDO byId = sysPermissionService.findById(sysPermissionDO);
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @PostMapping(value = "/permission/find/foreign")
    public Result<SysPermissionDTO> findByForeignColumn(@RequestBody SysPermissionQueryRequest request) {
        Optional<SysPermissionDO> optional = Optional.empty();
        String requestRequestPathUuid = request.getRequestPathUuid();
        String requestPath = request.getRequestPath();

        if (StrUtil.isNotBlank(requestRequestPathUuid)) {
            optional = sysPermissionService.findByRequestPathUuid(requestRequestPathUuid);
        } else if (StrUtil.isNotBlank(requestPath)) {
            optional = sysPermissionService.findByRequestPath(requestPath);
        }

        return optional.map(data -> Result.success(objMapStruct.transfer(data))).orElse(Result.fail(StatusCode.PERMISSION_NOT_EXIST));
    }

    @PostMapping(value = "/permission/find_all/ids")
    public Result<List<SysPermissionDTO>> findAllByIds(@RequestBody BaseQueryRequest request) {
        List<String> uuids = request.getUuids();
        if (CollUtil.isEmpty(uuids)) {
            return Result.success(Collections.emptyList());
        }
        List<SysPermissionDO> all = sysPermissionService.findIds(uuids);
        List<SysPermissionDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());
        return Result.success(list);
    }

    @PostMapping(value = "/permission/find_all")
    public Result<List<SysPermissionDTO>> findAll(@RequestBody SysPermissionQueryRequest request) {
        SysPermissionDO sysPermissionDO = objMapStruct.transfer(request);

        List<SysPermissionDO> all = sysPermissionService.findAll(sysPermissionDO, request);
        List<SysPermissionDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @PostMapping(value = "/permission/find_all/foreign")
    public Result<List<SysPermissionDTO>> findAllByForeignColumn(@RequestBody SysPermissionQueryRequest request) {
        List<SysPermissionDO> all = Collections.emptyList();

        String userUuid = request.getUserUuid();
        String roleUuid = request.getRoleUuid();
        if (StrUtil.isNotBlank(userUuid)) {
            all = sysPermissionService.findAllByUserUuid(userUuid);
        } else if (StrUtil.isNotBlank(roleUuid)) {
            all = sysPermissionService.findAllByRoleUuid(roleUuid);
        }

        List<SysPermissionDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @PostMapping(value = "/permission/list")
    public Result<PageInfo<SysPermissionDTO>> list(@RequestBody SysPermissionQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysPermissionDO sysPermissionDO = objMapStruct.transfer(request);
        Page<SysPermissionDO> all = (Page<SysPermissionDO>) sysPermissionService.findAll(sysPermissionDO, request);

        PageInfo<SysPermissionDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }
}
