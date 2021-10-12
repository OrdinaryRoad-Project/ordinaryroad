package top.ordinaryroad.system;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import top.ordinaryroad.commons.core.base.result.Result;
import top.ordinaryroad.commons.core.constant.SecurityConstants;
import top.ordinaryroad.commons.core.constant.ServiceNameConstants;
import top.ordinaryroad.system.entity.SysLoginInfoDO;
import top.ordinaryroad.system.entity.SysOperLogDO;
import top.ordinaryroad.system.factory.RemoteLogFallbackFactory;

/**
 * 日志服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {

    /**
     * 保存系统日志
     *
     * @param sysOperLog 日志实体
     * @param source     请求来源
     * @return 结果
     */
    @PostMapping("/operlog")
    Result<Boolean> saveLog(@RequestBody SysOperLogDO sysOperLog, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 保存访问记录
     *
     * @param sysLogininforDO 访问实体
     * @param source        请求来源
     * @return 结果
     */
    @PostMapping("/logininfor")
    Result<Boolean> saveLogininfor(@RequestBody SysLoginInfoDO sysLogininforDO, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
