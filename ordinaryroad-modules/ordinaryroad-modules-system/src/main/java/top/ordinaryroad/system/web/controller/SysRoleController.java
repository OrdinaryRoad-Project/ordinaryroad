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
import top.ordinaryroad.system.api.ISysRoleApi;
import top.ordinaryroad.system.domain.dto.SysRoleDTO;
import top.ordinaryroad.system.domain.request.SysRoleBatchDeleteRequest;
import top.ordinaryroad.system.domain.request.SysRoleDeleteRequest;
import top.ordinaryroad.system.domain.request.SysRoleQueryRequest;
import top.ordinaryroad.system.domain.request.SysRoleSaveRequest;
import top.ordinaryroad.system.entity.SysRole;
import top.ordinaryroad.system.service.ISysRoleService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色Controller
 *
 * @author mjz
 * @date 2021/10/12
 */
@RestController
public class SysRoleController implements ISysRoleApi {

    @Autowired
    private ISysRoleService sysRoleService;

    @Log(businessType = BusinessType.INSERT)
    @Override
    public Result<?> insert(SysRoleSaveRequest saveRequest) {
        SysRole sysRole = BeanUtils.transfer(saveRequest, SysRole.class);

        SysRole insert = sysRoleService.insert(sysRole);

        SysRoleDTO sysRoleDTO = BeanUtils.transfer(insert, SysRoleDTO.class);

        return Result.success(sysRoleDTO);
    }

    @Log(businessType = BusinessType.DELETE)
    @Override
    public Result<?> delete(SysRoleDeleteRequest deleteRequest) {
        Long id = deleteRequest.getId();
        String uuid = deleteRequest.getUuid();

        if (Argument.isPositive(id)) {
            return Result.success(sysRoleService.delete(id));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(sysRoleService.delete(uuid));
        }

        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Log(businessType = BusinessType.UPDATE)
    @Override
    public Result<?> update(SysRoleSaveRequest saveRequest) {
        Long id = saveRequest.getId();
        if (Argument.isNotPositive(id)) {
            return Result.fail(StatusCode.PARAM_NOT_VALID);
        }

        SysRole sysRole = BeanUtils.transfer(saveRequest, SysRole.class);
        SysRole update = sysRoleService.update(sysRole);
        SysRoleDTO sysRoleDTO = BeanUtils.transfer(update, SysRoleDTO.class);
        return Result.success(sysRoleDTO);
    }

    @Override
    public Result<?> find(SysRoleQueryRequest queryRequest) {
        Long id = queryRequest.getId();
        String uuid = queryRequest.getUuid();

        if (Argument.isPositive(id)) {
            return Result.success(BeanUtils.transfer(sysRoleService.find(id), SysRoleDTO.class));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(BeanUtils.transfer(sysRoleService.find(uuid), SysRoleDTO.class));
        }

        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Override
    public Result<Page<SysRoleDTO>> findAll(SysRoleQueryRequest queryRequest) {
        SysRole sysRole = BeanUtils.transfer(queryRequest, SysRole.class);

        assert sysRole != null;
        Example<SysRole> example = Example.of(sysRole, ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        Page<SysRole> all = sysRoleService.findAll(example, queryRequest.getOffset(), queryRequest.getLimit());

        Page<SysRoleDTO> p = new PageImpl<>(
                all.stream()
                        .map(o -> BeanUtils.transfer(o, SysRoleDTO.class))
                        .collect(Collectors.toList())
        );
        return Result.success(p);
    }

    @Log(businessType = BusinessType.BATCH_DELETE)
    @Override
    public Result<?> batchDelete(SysRoleBatchDeleteRequest baseBatchDeleteRequest) {
        List<Long> ids = baseBatchDeleteRequest.getIds();
        List<String> uuids = baseBatchDeleteRequest.getUuids();

        if (Argument.isNotEmpty(ids)) {
            return Result.success(sysRoleService.batchDelete(ids));
        } else if (Argument.isNotEmpty(uuids)) {
            return Result.success(sysRoleService.batchDelete(uuids));
        }

        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Log(businessType = BusinessType.RESTORE)
    @Override
    public Result<?> restore(SysRoleDeleteRequest deleteRequest) {
        Long id = deleteRequest.getId();
        String uuid = deleteRequest.getUuid();
        if (Argument.isPositive(id)) {
            return Result.success(sysRoleService.restore(id));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(sysRoleService.restore(uuid));
        }
        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Override
    public Result<?> query(SysRoleQueryRequest queryRequest) {
        Long id = queryRequest.getId();
        String uuid = queryRequest.getUuid();
        if (Argument.isPositive(id)) {
            return Result.success(BeanUtils.transfer(sysRoleService.query(id), SysRoleDTO.class));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(BeanUtils.transfer(sysRoleService.query(uuid), SysRoleDTO.class));
        }
        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Override
    public Result<Page<SysRoleDTO>> list(SysRoleQueryRequest queryRequest) {
        SysRole sysRole = BeanUtils.transfer(queryRequest, SysRole.class);

        assert sysRole != null;
        Example<SysRole> example = Example.of(sysRole, ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        Page<SysRole> all = sysRoleService.list(example, queryRequest.getOffset(), queryRequest.getLimit());

        Page<SysRoleDTO> p = new PageImpl<>(
                all.stream().map(o -> BeanUtils.transfer(o, SysRoleDTO.class))
                        .collect(Collectors.toList())
        );
        return Result.success(p);
    }

}