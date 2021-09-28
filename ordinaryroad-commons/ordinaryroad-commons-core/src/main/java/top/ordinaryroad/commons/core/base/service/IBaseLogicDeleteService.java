package top.ordinaryroad.commons.core.base.service;

import org.springframework.data.domain.Example;
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
public interface IBaseLogicDeleteService<DO extends BaseDO> extends IBaseService<DO> {

    /**
     * 恢复
     *
     * @param key 主键Long或String
     * @return 是否成功
     */
    <KEY> Boolean restore(@NotNull KEY key);

    /**
     * 根据主键查询未被删除的数据
     *
     * @param key 主键
     * @return 实体
     */
    @Nullable
    <KEY> DO query(@NotNull KEY key);

    /**
     * 分页查询所有deleted=false的数据
     *
     * @param exampleDo 查询参数
     * @param offset    偏移量，从0开始
     * @param size      多少条数据，默认为10
     * @return 分页的所有数据
     */
    Page<DO> list(@NotNull Example<DO> exampleDo, @NotNull Integer offset, @NotNull Integer size);

}
