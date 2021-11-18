package tech.ordinaryroad.commons.satoken;

import cn.dev33.satoken.stp.StpInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.api.ISysPermissionApi;
import tech.ordinaryroad.upms.api.ISysRoleApi;
import tech.ordinaryroad.upms.api.ISysUserApi;
import tech.ordinaryroad.upms.dto.SysPermissionDTO;
import tech.ordinaryroad.upms.dto.SysRoleDTO;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.SysPermissionQueryRequest;
import tech.ordinaryroad.upms.request.SysRoleQueryRequest;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 */
@RequiredArgsConstructor
@Component
public class StpInterfaceImpl implements StpInterface {

    private final ISysUserApi iSysUserApi;
    private final ISysRoleApi iSysRoleApi;
    private final ISysPermissionApi sysPermissionApi;

    /**
     * webflux异步
     * https://blog.csdn.net/qq_33811736/article/details/115865879
     */
    private final ExecutorService executorService = new ThreadPoolExecutor(
            3, 3, 60L,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), r -> {
        Thread thread = new Thread(r);
        thread.setName("sa token获取角色列表线程");
        return thread;
    });

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的权限列表
        String orNumber = (String) loginId;
        // 根据or帐号查询用户uuid
        SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
        sysUserQueryRequest.setOrNumber(orNumber);
        Result<SysUserDTO> byUniqueColumn = iSysUserApi.findByUniqueColumn(sysUserQueryRequest);
        if (byUniqueColumn.getSuccess()) {
            String userUuid = byUniqueColumn.getData().getUuid();
            SysPermissionQueryRequest request = new SysPermissionQueryRequest();
            request.setUserUuid(userUuid);
            Result<List<SysPermissionDTO>> allByUserUuid = sysPermissionApi.findAllByUserUuid(request);
            return allByUserUuid.getData().stream().map(SysPermissionDTO::getPermissionCode).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // TODO 增加缓存
        String orNumber = (String) loginId;

        try {
            // 先根据or帐号查询用户uuid
            SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
            sysUserQueryRequest.setOrNumber(orNumber);
            Future<Result<SysUserDTO>> byUniqueColumnFuture = executorService.submit(() -> iSysUserApi.findByUniqueColumn(sysUserQueryRequest));
            Result<SysUserDTO> byUniqueColumn = byUniqueColumnFuture.get();

            if (byUniqueColumn.getSuccess()) {
                String uuid = byUniqueColumn.getData().getUuid();
                // 再根据uuid查询用户所有角色
                SysRoleQueryRequest sysRoleQueryRequest = new SysRoleQueryRequest();
                sysRoleQueryRequest.setUserUuid(uuid);
                Future<Result<List<SysRoleDTO>>> byUuidFuture = executorService.submit(() -> iSysRoleApi.findAllByUserUuid(sysRoleQueryRequest));

                Result<List<SysRoleDTO>> rolesResult = byUuidFuture.get();
                List<SysRoleDTO> roles = rolesResult.getData();
                return roles.stream().map(SysRoleDTO::getRoleCode).collect(Collectors.toList());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}