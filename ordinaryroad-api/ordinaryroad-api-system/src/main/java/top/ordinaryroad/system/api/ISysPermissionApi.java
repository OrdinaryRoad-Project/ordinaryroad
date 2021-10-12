package top.ordinaryroad.system.api;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import top.ordinaryroad.commons.core.base.api.IBaseLogicBatchDeleteApi;
import top.ordinaryroad.system.domain.dto.SysPermissionDTO;
import top.ordinaryroad.system.domain.request.SysPermissionBatchDeleteRequest;
import top.ordinaryroad.system.domain.request.SysPermissionDeleteRequest;
import top.ordinaryroad.system.domain.request.SysPermissionQueryRequest;
import top.ordinaryroad.system.domain.request.SysPermissionSaveRequest;

/**
 * 权限API
 *
 * @author mjz
 * @date 2021/10/12
 */
@Api(value = "sys_permission")
@RequestMapping(value = "/permission", produces = {MediaType.APPLICATION_JSON_VALUE})
public interface ISysPermissionApi extends IBaseLogicBatchDeleteApi<
        SysPermissionDTO,
        SysPermissionSaveRequest,
        SysPermissionDeleteRequest,
        SysPermissionBatchDeleteRequest,
        SysPermissionQueryRequest> {

}
