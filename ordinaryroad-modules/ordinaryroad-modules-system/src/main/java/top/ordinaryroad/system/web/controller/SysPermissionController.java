package top.ordinaryroad.system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RestController;
import top.ordinaryroad.commons.core.base.cons.StatusCode;
import top.ordinaryroad.commons.core.base.result.Result;
import top.ordinaryroad.commons.core.lang.Argument;
import top.ordinaryroad.commons.core.utils.bean.BeanUtils;
import top.ordinaryroad.commons.log.annotation.Log;
import top.ordinaryroad.commons.log.enums.BusinessType;
import top.ordinaryroad.system.api.ISysPermissionApi;
import top.ordinaryroad.system.domain.dto.SysPermissionDTO;
import top.ordinaryroad.system.domain.request.SysPermissionBatchDeleteRequest;
import top.ordinaryroad.system.domain.request.SysPermissionDeleteRequest;
import top.ordinaryroad.system.domain.request.SysPermissionQueryRequest;
import top.ordinaryroad.system.domain.request.SysPermissionSaveRequest;
import top.ordinaryroad.system.entity.SysPermission;
import top.ordinaryroad.system.service.ISysPermissionService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限Controller
 *
 * @author mjz
 * @date 2021/10/12
 */
@RestController
public class SysPermissionController implements ISysPermissionApi {

    @Autowired
    private ISysPermissionService sysPermissionService;

    @Log(businessType = BusinessType.INSERT)
    @Override
    public Result<?> insert(SysPermissionSaveRequest saveRequest) {
        SysPermission sysPermission = BeanUtils.transfer(saveRequest, SysPermission.class);

        SysPermission insert = sysPermissionService.insert(sysPermission);

        SysPermissionDTO sysPermissionDTO = BeanUtils.transfer(insert, SysPermissionDTO.class);

        return Result.success(sysPermissionDTO);
    }

    @Log(businessType = BusinessType.DELETE)
    @Override
    public Result<?> delete(SysPermissionDeleteRequest deleteRequest) {
        Long id = deleteRequest.getId();
        String uuid = deleteRequest.getUuid();

        if (Argument.isPositive(id)) {
            return Result.success(sysPermissionService.delete(id));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(sysPermissionService.delete(uuid));
        }

        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Log(businessType = BusinessType.UPDATE)
    @Override
    public Result<?> update(SysPermissionSaveRequest saveRequest) {
        Long id = saveRequest.getId();
        if (Argument.isNotPositive(id)) {
            return Result.fail(StatusCode.PARAM_NOT_VALID);
        }

        SysPermission sysPermission = BeanUtils.transfer(saveRequest, SysPermission.class);
        SysPermission update = sysPermissionService.update(sysPermission);
        SysPermissionDTO sysPermissionDTO = BeanUtils.transfer(update, SysPermissionDTO.class);
        return Result.success(sysPermissionDTO);
    }

    @Override
    public Result<?> find(SysPermissionQueryRequest queryRequest) {
        Long id = queryRequest.getId();
        String uuid = queryRequest.getUuid();

        if (Argument.isPositive(id)) {
            return Result.success(BeanUtils.transfer(sysPermissionService.find(id), SysPermissionDTO.class));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(BeanUtils.transfer(sysPermissionService.find(uuid), SysPermissionDTO.class));
        }

        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Override
    public Result<Page<SysPermissionDTO>> findAll(SysPermissionQueryRequest queryRequest) {
        SysPermission sysPermission = BeanUtils.transfer(queryRequest, SysPermission.class);

        assert sysPermission != null;
        Example<SysPermission> example = Example.of(sysPermission, ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        Page<SysPermission> all = sysPermissionService.findAll(example, queryRequest.getOffset(), queryRequest.getLimit());

        Page<SysPermissionDTO> p = new PageImpl<>(
                all.stream()
                        .map(o -> BeanUtils.transfer(o, SysPermissionDTO.class))
                        .collect(Collectors.toList())
        );
        return Result.success(p);
    }

    @Log(businessType = BusinessType.BATCH_DELETE)
    @Override
    public Result<?> batchDelete(SysPermissionBatchDeleteRequest baseBatchDeleteRequest) {
        List<Long> ids = baseBatchDeleteRequest.getIds();
        List<String> uuids = baseBatchDeleteRequest.getUuids();

        if (Argument.isNotEmpty(ids)) {
            return Result.success(sysPermissionService.batchDelete(ids));
        } else if (Argument.isNotEmpty(uuids)) {
            return Result.success(sysPermissionService.batchDelete(uuids));
        }

        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Log(businessType = BusinessType.RESTORE)
    @Override
    public Result<?> restore(SysPermissionDeleteRequest deleteRequest) {
        Long id = deleteRequest.getId();
        String uuid = deleteRequest.getUuid();
        if (Argument.isPositive(id)) {
            return Result.success(sysPermissionService.restore(id));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(sysPermissionService.restore(uuid));
        }
        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Override
    public Result<?> query(SysPermissionQueryRequest queryRequest) {
        Long id = queryRequest.getId();
        String uuid = queryRequest.getUuid();
        if (Argument.isPositive(id)) {
            return Result.success(BeanUtils.transfer(sysPermissionService.query(id), SysPermissionDTO.class));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(BeanUtils.transfer(sysPermissionService.query(uuid), SysPermissionDTO.class));
        }
        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Override
    public Result<Page<SysPermissionDTO>> list(SysPermissionQueryRequest queryRequest) {
        SysPermission sysPermission = BeanUtils.transfer(queryRequest, SysPermission.class);

        assert sysPermission != null;
        Example<SysPermission> example = Example.of(sysPermission, ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        Page<SysPermission> all = sysPermissionService.list(example, queryRequest.getOffset(), queryRequest.getLimit());

        Page<SysPermissionDTO> p = new PageImpl<>(
                all.stream().map(o -> BeanUtils.transfer(o, SysPermissionDTO.class))
                        .collect(Collectors.toList())
        );
        return Result.success(p);
    }

}