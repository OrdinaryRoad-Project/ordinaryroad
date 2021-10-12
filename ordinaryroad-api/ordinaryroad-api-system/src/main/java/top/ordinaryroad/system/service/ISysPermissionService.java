package top.ordinaryroad.system.service;

import org.springframework.lang.Nullable;
import top.ordinaryroad.commons.core.base.service.IBaseLogicDeleteService;
import top.ordinaryroad.system.entity.SysPermission;

import javax.validation.constraints.NotNull;

/**
 * 权限服务接口
 *
 * @author mjz
 * @date 2021/10/12
 */
public interface ISysPermissionService extends IBaseLogicDeleteService<SysPermission> {

    /**
     * 根据权限名找到权限
     *
     * @param name 权限名
     * @return 实体
     */
    @Nullable
    SysPermission findByName(@NotNull String name);

    /**
     * 根据权限code找到权限
     *
     * @param code 权限code
     * @return 实体
     */
    @Nullable
    SysPermission findByCode(@NotNull String code);

    /**
     * 根据权限名和权限code找到权限
     *
     * @param name 权限名
     * @param code 权限code
     * @return 实体
     */
    @Nullable
    SysPermission findByNameAndCode(@NotNull String name, @NotNull String code);

}
