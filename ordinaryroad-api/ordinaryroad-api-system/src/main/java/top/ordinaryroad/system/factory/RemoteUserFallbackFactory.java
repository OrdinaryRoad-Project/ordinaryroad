package top.ordinaryroad.system.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.ordinaryroad.commons.core.base.result.Result;
import top.ordinaryroad.system.RemoteUserService;
import top.ordinaryroad.system.entity.SysUser;

/**
 * 用户服务降级处理
 *
 * @author ruoyi
 */
@Slf4j
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable throwable) {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserService() {

            @GetMapping("/user/info/{username}")
            @Override
            public Result<SysUser> getUserInfo(@PathVariable String username) {
                return Result.fail("获取用户 " + username + " 失败:" + throwable.getMessage());
            }

            @PostMapping("/user/updateLoginInfo")
            @Override
            public Result<SysUser> updateLoginInfo(@RequestParam String username) {
                return Result.fail("更新用户登录相关信息失败:" + throwable.getMessage());
            }

        };
    }
}
