package top.ordinaryroad.commons.core.base.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import top.ordinaryroad.commons.core.base.model.BaseDO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 通用增删改查Service
 *
 * @param <DO> DO类
 * @author mjz
 * @date 2021/9/3
 */
public interface IBaseService<DO extends BaseDO> {

    /**
     * 插入
     *
     * @param entity 实体
     * @return 插入到数据库后的实体
     */
    @NotNull
    DO insert(@NotNull DO entity);

    /**
     * 删除
     *
     * @param key 主键
     * @return 是否成功
     */
    @NotNull <KEY> Boolean delete(@NotNull KEY key);

    /**
     * 批量删除
     *
     * @param list 主键列表，可以是Long或者String
     * @return 是否成功
     */
    @NotNull <T> Boolean batchDelete(@NotNull List<T> list);

    /**
     * 更新
     *
     * @param entity 实体
     * @return 更新后的实体
     */
    @NotNull
    DO update(@NotNull DO entity);

    /**
     * 根据主键找到数据
     *
     * @param key 主键
     * @return 实体
     */
    @Nullable
    <KEY> DO find(@NotNull KEY key);

    /**
     * 分页找到所有数据
     *
     * @param exampleDo 查询参数
     * @param offset    偏移量，从0开始
     * @param size      多少条数据，默认为10
     * @return 分页的所有数据
     */
    Page<DO> findAll(@NotNull Example<DO> exampleDo, @NotNull Integer offset, @NotNull Integer size);

}
