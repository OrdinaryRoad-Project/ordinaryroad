package top.ordinaryroad.system.service;

import org.springframework.lang.Nullable;
import top.ordinaryroad.commons.core.base.service.IBaseLogicDeleteService;
import top.ordinaryroad.system.entity.SysRole;

import javax.validation.constraints.NotNull;

/**
 * 角色服务接口
 *
 * @author mjz
 * @date 2021/10/12
 */
public interface ISysRoleService extends IBaseLogicDeleteService<SysRole> {

    /**
     * 根据角色名找到角色
     *
     * @param name 角色名
     * @return 实体
     */
    @Nullable
    SysRole findByName(@NotNull String name);

    /**
     * 根据角色code找到角色
     *
     * @param code 角色code
     * @return 实体
     */
    @Nullable
    SysRole findByCode(@NotNull String code);

    /**
     * 根据角色名和角色code找到角色
     *
     * @param name 角色名
     * @param code 角色code
     * @return 实体
     */
    @Nullable
    SysRole findByNameAndCode(@NotNull String name, @NotNull String code);

}
