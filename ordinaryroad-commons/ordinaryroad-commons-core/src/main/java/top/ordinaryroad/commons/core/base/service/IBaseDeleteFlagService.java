package top.ordinaryroad.commons.core.base.service;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import top.ordinaryroad.commons.core.base.model.BaseDO;

import javax.validation.constraints.NotNull;

/**
 * 通用增删改查Service，逻辑删除
 *
 * @param <DO> DO类
 * @author mjz
 * @date 2021/9/3
 */
public interface IBaseDeleteFlagService<DO extends BaseDO> extends IBaseService<DO> {

    /**
     * 恢复
     *
     * @param id 主键
     * @return 是否成功
     */
    @NotNull
    Boolean restore(@NotNull Long id);

    /**
     * 根据主键查询deleted=false的数据
     *
     * @param id 主键
     * @return 实体
     */
    @Nullable
    DO query(@NotNull Long id);

    /**
     * 分页查询所有deleted=false的数据
     *
     * @param offset 偏移量，从0开始
     * @param size   多少条数据，默认为10
     * @return 分页的所有数据
     */
    Page<DO> list(@NotNull Integer offset, @NotNull Integer size);

}
