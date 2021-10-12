package top.ordinaryroad.system.api;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import top.ordinaryroad.commons.core.base.api.IBaseLogicBatchDeleteApi;
import top.ordinaryroad.commons.core.base.result.Result;
import top.ordinaryroad.system.domain.dto.SysUserDTO;
import top.ordinaryroad.system.domain.request.SysUserBatchDeleteRequest;
import top.ordinaryroad.system.domain.request.SysUserDeleteRequest;
import top.ordinaryroad.system.domain.request.SysUserQueryRequest;
import top.ordinaryroad.system.domain.request.SysUserSaveRequest;
import top.ordinaryroad.system.entity.SysUser;

/**
 * 用户API
 *
 * @author mjz
 * @date 2021/9/3
 */
@Api(value = "sys_user")
@RequestMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_VALUE})
public interface ISysUserApi extends IBaseLogicBatchDeleteApi<
        SysUserDTO,
        SysUserSaveRequest,
        SysUserDeleteRequest,
        SysUserBatchDeleteRequest,
        SysUserQueryRequest> {

    /**
     * 查询用户详情、角色、权限等
     *
     * @param username 用户名
     * @return Result
     */
    @GetMapping("info/{username}")
    Result<SysUser> getUserInfo(@PathVariable("username") String username);


    /**
     * 更新登录相关信息
     *
     * @param username 用户名
     * @return result
     */
    @PostMapping("updateLoginInfo")
    Result<SysUser> updateLoginInfo(@RequestParam String username);

}
