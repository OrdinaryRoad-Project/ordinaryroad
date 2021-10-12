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
import top.ordinaryroad.system.api.ISysRequestPathApi;
import top.ordinaryroad.system.domain.dto.SysRequestPathDTO;
import top.ordinaryroad.system.domain.request.SysRequestPathBatchDeleteRequest;
import top.ordinaryroad.system.domain.request.SysRequestPathDeleteRequest;
import top.ordinaryroad.system.domain.request.SysRequestPathQueryRequest;
import top.ordinaryroad.system.domain.request.SysRequestPathSaveRequest;
import top.ordinaryroad.system.entity.SysRequestPath;
import top.ordinaryroad.system.service.ISysRequestPathService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限Controller
 *
 * @author mjz
 * @date 2021/10/12
 */
@RestController
public class SysRequestPathController implements ISysRequestPathApi {

    @Autowired
    private ISysRequestPathService sysRequestPathService;

    @Log(businessType = BusinessType.INSERT)
    @Override
    public Result<?> insert(SysRequestPathSaveRequest saveRequest) {
        SysRequestPath sysRequestPath = BeanUtils.transfer(saveRequest, SysRequestPath.class);

        SysRequestPath insert = sysRequestPathService.insert(sysRequestPath);

        SysRequestPathDTO sysRequestPathDTO = BeanUtils.transfer(insert, SysRequestPathDTO.class);

        return Result.success(sysRequestPathDTO);
    }

    @Log(businessType = BusinessType.DELETE)
    @Override
    public Result<?> delete(SysRequestPathDeleteRequest deleteRequest) {
        Long id = deleteRequest.getId();
        String uuid = deleteRequest.getUuid();

        if (Argument.isPositive(id)) {
            return Result.success(sysRequestPathService.delete(id));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(sysRequestPathService.delete(uuid));
        }

        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Log(businessType = BusinessType.UPDATE)
    @Override
    public Result<?> update(SysRequestPathSaveRequest saveRequest) {
        Long id = saveRequest.getId();
        if (Argument.isNotPositive(id)) {
            return Result.fail(StatusCode.PARAM_NOT_VALID);
        }

        SysRequestPath sysRequestPath = BeanUtils.transfer(saveRequest, SysRequestPath.class);
        SysRequestPath update = sysRequestPathService.update(sysRequestPath);
        SysRequestPathDTO sysRequestPathDTO = BeanUtils.transfer(update, SysRequestPathDTO.class);
        return Result.success(sysRequestPathDTO);
    }

    @Override
    public Result<?> find(SysRequestPathQueryRequest queryRequest) {
        Long id = queryRequest.getId();
        String uuid = queryRequest.getUuid();

        if (Argument.isPositive(id)) {
            return Result.success(BeanUtils.transfer(sysRequestPathService.find(id), SysRequestPathDTO.class));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(BeanUtils.transfer(sysRequestPathService.find(uuid), SysRequestPathDTO.class));
        }

        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Override
    public Result<Page<SysRequestPathDTO>> findAll(SysRequestPathQueryRequest queryRequest) {
        SysRequestPath sysRequestPath = BeanUtils.transfer(queryRequest, SysRequestPath.class);

        assert sysRequestPath != null;
        Example<SysRequestPath> example = Example.of(sysRequestPath, ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        Page<SysRequestPath> all = sysRequestPathService.findAll(example, queryRequest.getOffset(), queryRequest.getLimit());

        Page<SysRequestPathDTO> p = new PageImpl<>(
                all.stream()
                        .map(o -> BeanUtils.transfer(o, SysRequestPathDTO.class))
                        .collect(Collectors.toList())
        );
        return Result.success(p);
    }

    @Log(businessType = BusinessType.BATCH_DELETE)
    @Override
    public Result<?> batchDelete(SysRequestPathBatchDeleteRequest baseBatchDeleteRequest) {
        List<Long> ids = baseBatchDeleteRequest.getIds();
        List<String> uuids = baseBatchDeleteRequest.getUuids();

        if (Argument.isNotEmpty(ids)) {
            return Result.success(sysRequestPathService.batchDelete(ids));
        } else if (Argument.isNotEmpty(uuids)) {
            return Result.success(sysRequestPathService.batchDelete(uuids));
        }

        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Log(businessType = BusinessType.RESTORE)
    @Override
    public Result<?> restore(SysRequestPathDeleteRequest deleteRequest) {
        Long id = deleteRequest.getId();
        String uuid = deleteRequest.getUuid();
        if (Argument.isPositive(id)) {
            return Result.success(sysRequestPathService.restore(id));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(sysRequestPathService.restore(uuid));
        }
        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Override
    public Result<?> query(SysRequestPathQueryRequest queryRequest) {
        Long id = queryRequest.getId();
        String uuid = queryRequest.getUuid();
        if (Argument.isPositive(id)) {
            return Result.success(BeanUtils.transfer(sysRequestPathService.query(id), SysRequestPathDTO.class));
        } else if (Argument.isNotBlank(uuid)) {
            return Result.success(BeanUtils.transfer(sysRequestPathService.query(uuid), SysRequestPathDTO.class));
        }
        return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
    }

    @Override
    public Result<Page<SysRequestPathDTO>> list(SysRequestPathQueryRequest queryRequest) {
        SysRequestPath sysRequestPath = BeanUtils.transfer(queryRequest, SysRequestPath.class);

        assert sysRequestPath != null;
        Example<SysRequestPath> example = Example.of(sysRequestPath, ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        Page<SysRequestPath> all = sysRequestPathService.list(example, queryRequest.getOffset(), queryRequest.getLimit());

        Page<SysRequestPathDTO> p = new PageImpl<>(
                all.stream().map(o -> BeanUtils.transfer(o, SysRequestPathDTO.class))
                        .collect(Collectors.toList())
        );
        return Result.success(p);
    }

}