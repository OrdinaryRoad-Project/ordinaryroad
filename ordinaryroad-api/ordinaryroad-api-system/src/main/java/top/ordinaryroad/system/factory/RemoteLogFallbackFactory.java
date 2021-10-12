package top.ordinaryroad.system.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import top.ordinaryroad.commons.core.base.result.Result;
import top.ordinaryroad.system.RemoteLogService;
import top.ordinaryroad.system.entity.SysLoginInfoDO;
import top.ordinaryroad.system.entity.SysOperLogDO;

/**
 * 日志服务降级处理
 *
 * @author ruoyi
 */
@Slf4j
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService> {

    @Override
    public RemoteLogService create(Throwable throwable) {
        log.error("日志服务调用失败:{}", throwable.getMessage());
        return new RemoteLogService() {
            @Override
            public Result<Boolean> saveLog(SysOperLogDO sysOperLog, String source) {
                return null;
            }

            @Override
            public Result<Boolean> saveLogininfor(SysLoginInfoDO sysLogininforDO, String source) {
                return null;
            }
        };

    }
}
