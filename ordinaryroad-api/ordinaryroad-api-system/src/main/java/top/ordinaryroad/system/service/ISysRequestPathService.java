package top.ordinaryroad.system.service;

import org.springframework.lang.Nullable;
import top.ordinaryroad.commons.core.base.service.IBaseLogicDeleteService;
import top.ordinaryroad.system.entity.SysRequestPath;

import javax.validation.constraints.NotNull;

/**
 * 请求路径服务接口
 *
 * @author mjz
 * @date 2021/10/12
 */
public interface ISysRequestPathService extends IBaseLogicDeleteService<SysRequestPath> {

    /**
     * 根据请求路径名找到请求路径
     *
     * @param name 请求路径名
     * @return 实体
     */
    @Nullable
    SysRequestPath findByName(@NotNull String name);

    /**
     * 根据请求路径url找到请求路径
     *
     * @param url 请求路径url
     * @return 实体
     */
    @Nullable
    SysRequestPath findByUrl(@NotNull String url);

    /**
     * 根据请求路径名和请求路径url找到请求路径
     *
     * @param name 请求路径名
     * @param url  请求路径url
     * @return 实体
     */
    @Nullable
    SysRequestPath findByNameAndUrl(@NotNull String name, @NotNull String url);

}
