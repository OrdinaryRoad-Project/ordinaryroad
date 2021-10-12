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
import top.ordinaryroad.system.api.ISysUserApi;
import top.ordinaryroad.system.domain.dto.SysUserDTO;
import top.ordinaryroad.system.domain.request.SysUserBatchDeleteRequest;
import top.ordinaryroad.system.domain.request.SysUserDeleteRequest;
import top.ordinaryroad.system.domain.request.SysUserQueryRequest;
import top.ordinaryroad.system.domain.request.SysUserSaveRequest;
import top.ordinaryroad.system.entity.SysUser;
import top.ordinaryroad.system.service.ISysUserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户Controller
 *
 * @author mjz
 * @date 2021/9/3
 */
@RestController
public class SysUserController implements ISysUserApi {

    @Autowired
    private ISysUserService sysUserService;

    @Log(businessType = BusinessType.INSERT)
    @Override
    public Result<?> insert(SysUserSaveRequest saveRequest) {
        SysUser sysUser = BeanUtils.transfer(saveRequest, SysUser.class);

        SysUser insert = sysUserService.insert(sysUser);

        SysUserDTO sysUserDTO = BeanUtils.transfer(insert, SysUserDTO.class);

        return Result.success(sysUserDTO);
    }

    @Log(businessType = BusinessType.DELETE)
    @Override
    public Result<?> delete(SysUserDeleteRequest deleteRequest) {
        Long id = deleteRequest.getId();
        String uuid = deleteRequest.getUuid();

        if (Argument.isPositive(id)) {
            return Result.success(sysUserService.delete(id));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(sysUserService.delete(uuid));
        }

        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Log(businessType = BusinessType.UPDATE)
    @Override
    public Result<?> update(SysUserSaveRequest saveRequest) {
        Long id = saveRequest.getId();
        if (Argument.isNotPositive(id)) {
            return Result.fail(StatusCode.PARAM_NOT_VALID);
        }

        SysUser sysUser = BeanUtils.transfer(saveRequest, SysUser.class);
        SysUser update = sysUserService.update(sysUser);
        SysUserDTO sysUserDTO = BeanUtils.transfer(update, SysUserDTO.class);
        return Result.success(sysUserDTO);
    }

    @Override
    public Result<?> find(SysUserQueryRequest queryRequest) {
        Long id = queryRequest.getId();
        String uuid = queryRequest.getUuid();

        if (Argument.isPositive(id)) {
            return Result.success(BeanUtils.transfer(sysUserService.find(id), SysUserDTO.class));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(BeanUtils.transfer(sysUserService.find(uuid), SysUserDTO.class));
        }

        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Override
    public Result<Page<SysUserDTO>> findAll(SysUserQueryRequest queryRequest) {
        SysUser sysUser = BeanUtils.transfer(queryRequest, SysUser.class);

        assert sysUser != null;
        Example<SysUser> example = Example.of(sysUser, ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        Page<SysUser> all = sysUserService.findAll(example, queryRequest.getOffset(), queryRequest.getLimit());

        Page<SysUserDTO> p = new PageImpl<>(
                all.stream()
                        .map(o -> BeanUtils.transfer(o, SysUserDTO.class))
                        .collect(Collectors.toList())
        );
        return Result.success(p);
    }

    @Log(businessType = BusinessType.BATCH_DELETE)
    @Override
    public Result<?> batchDelete(SysUserBatchDeleteRequest baseBatchDeleteRequest) {
        List<Long> ids = baseBatchDeleteRequest.getIds();
        List<String> uuids = baseBatchDeleteRequest.getUuids();

        if (Argument.isNotEmpty(ids)) {
            return Result.success(sysUserService.batchDelete(ids));
        } else if (Argument.isNotEmpty(uuids)) {
            return Result.success(sysUserService.batchDelete(uuids));
        }

        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Log(businessType = BusinessType.RESTORE)
    @Override
    public Result<?> restore(SysUserDeleteRequest deleteRequest) {
        Long id = deleteRequest.getId();
        String uuid = deleteRequest.getUuid();
        if (Argument.isPositive(id)) {
            return Result.success(sysUserService.restore(id));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(sysUserService.restore(uuid));
        }
        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Override
    public Result<?> query(SysUserQueryRequest queryRequest) {
        Long id = queryRequest.getId();
        String uuid = queryRequest.getUuid();
        if (Argument.isPositive(id)) {
            return Result.success(BeanUtils.transfer(sysUserService.query(id), SysUserDTO.class));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(BeanUtils.transfer(sysUserService.query(uuid), SysUserDTO.class));
        }
        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Override
    public Result<Page<SysUserDTO>> list(SysUserQueryRequest queryRequest) {
        SysUser sysUser = BeanUtils.transfer(queryRequest, SysUser.class);

        assert sysUser != null;
        Example<SysUser> example = Example.of(sysUser, ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        Page<SysUser> all = sysUserService.list(example, queryRequest.getOffset(), queryRequest.getLimit());

        Page<SysUserDTO> p = new PageImpl<>(
                all.stream().map(o -> BeanUtils.transfer(o, SysUserDTO.class))
                        .collect(Collectors.toList())
        );
        return Result.success(p);
    }

    @Override
    public Result<SysUser> getUserInfo(String username) {
        if (Argument.isBlank(username)) {
            return Result.fail(StatusCode.PARAM_NOT_VALID);
        }
        SysUser sysUser = sysUserService.findByUsername(username);
        return Result.success(sysUser);
    }

    @Log(businessType = BusinessType.UPDATE)
    @Override
    public Result<SysUser> updateLoginInfo(String username) {
        if (Argument.isBlank(username)) {
            return Result.fail(StatusCode.PARAM_NOT_VALID);
        }
        SysUser sysUser = sysUserService.updateLoginInfo(username);
        return Result.success(sysUser);
    }

}