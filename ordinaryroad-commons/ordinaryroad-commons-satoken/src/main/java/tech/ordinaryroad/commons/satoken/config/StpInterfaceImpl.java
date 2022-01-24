/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.ordinaryroad.commons.satoken.config;

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
import java.util.Objects;
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
            4, 8, 24L,
            TimeUnit.HOURS, new ArrayBlockingQueue<>(8), r -> {
        Thread thread = new Thread(r);
        thread.setName("commons-satoken 获取用户角色和权限列表线程");
        return thread;
    });

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的权限列表
        String orNumber = (String) loginId;
        // 根据or帐号查询用户uuid
        SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
        sysUserQueryRequest.setOrNumber(orNumber);
        Future<Result<SysUserDTO>> future = executorService.submit(() -> iSysUserApi.findByUniqueColumn(sysUserQueryRequest));
        Result<SysUserDTO> byUniqueColumn = null;
        try {
            byUniqueColumn = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (Objects.isNull(byUniqueColumn) || !byUniqueColumn.getSuccess()) {
            return Collections.emptyList();
        }

        String userUuid = byUniqueColumn.getData().getUuid();
        SysPermissionQueryRequest request = new SysPermissionQueryRequest();
        request.setUserUuid(userUuid);
        Future<Result<List<SysPermissionDTO>>> submit = executorService.submit(() -> sysPermissionApi.findAllByForeignColumn(request));
        Result<List<SysPermissionDTO>> allByUserUuid = null;
        try {
            allByUserUuid = submit.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (Objects.isNull(allByUserUuid) || !allByUserUuid.getSuccess()) {
            return Collections.emptyList();
        }
        List<SysPermissionDTO> all = allByUserUuid.getData();
        return all.stream().map(SysPermissionDTO::getPermissionCode).collect(Collectors.toList());
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
