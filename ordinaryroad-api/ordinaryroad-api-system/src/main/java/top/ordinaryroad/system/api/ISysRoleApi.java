package top.ordinaryroad.system.api;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import top.ordinaryroad.commons.core.base.api.IBaseLogicBatchDeleteApi;
import top.ordinaryroad.system.domain.dto.SysRoleDTO;
import top.ordinaryroad.system.domain.request.SysRoleBatchDeleteRequest;
import top.ordinaryroad.system.domain.request.SysRoleDeleteRequest;
import top.ordinaryroad.system.domain.request.SysRoleQueryRequest;
import top.ordinaryroad.system.domain.request.SysRoleSaveRequest;

/**
 * 角色API
 *
 * @author mjz
 * @date 2021/10/12
 */
@Api(value = "sys_role")
@RequestMapping(value = "/role", produces = {MediaType.APPLICATION_JSON_VALUE})
public interface ISysRoleApi extends IBaseLogicBatchDeleteApi<
        SysRoleDTO,
        SysRoleSaveRequest,
        SysRoleDeleteRequest,
        SysRoleBatchDeleteRequest,
        SysRoleQueryRequest> {

}
