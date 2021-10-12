package top.ordinaryroad.system;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.ordinaryroad.commons.core.base.result.Result;
import top.ordinaryroad.commons.core.constant.ServiceNameConstants;
import top.ordinaryroad.system.entity.SysUser;
import top.ordinaryroad.system.factory.RemoteUserFallbackFactory;

/**
 * 用户服务
 *
 * @author ruoyi
 */
@FeignClient(
        value = ServiceNameConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteUserFallbackFactory.class,
        url = "localhost:9401"
)
public interface RemoteUserService {

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 结果
     */
    @GetMapping("/user/info/{username}")
    Result<SysUser> getUserInfo(@PathVariable("username") String username);

    /**
     * 更新登录相关信息
     *
     * @param username 用户名
     * @return result
     */
    @PostMapping(value = "/user/updateLoginInfo")
    Result<SysUser> updateLoginInfo(@RequestParam("username") String username);

}
