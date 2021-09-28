package top.ordinaryroad.commons.core.base.service;


import org.springframework.data.domain.Page;
import top.ordinaryroad.commons.core.base.model.BaseDO;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 通用增删改查Service，带有多个外键关联
 *
 * @param <DO> DO类
 * @param <F>  外键
 * @author qq1962247851
 * @date 2020/6/16 22:51
 */
public interface IMultipleForeignKeyLogicDeleteService<DO extends BaseDO, F> extends IBaseLogicDeleteService<DO> {

    /**
     * 根据fromForeignKey分页查询所有isDeleted=false的数据
     *
     * @param foreignKeyList 外键列表
     * @param offset         偏移量，从0开始
     * @param size           多少条数据，默认为10
     * @return 分页的所有数据
     */
    Page<DO> listFrom(@NotNull List<F> foreignKeyList, @NotNull Integer offset, @NotNull Integer size);

    /**
     * 根据fromForeignKey分页查询所有isDeleted=false的数据
     *
     * @param foreignKeyList 外键列表
     * @param offset         偏移量，从0开始
     * @param size           多少条数据，默认为10
     * @return 分页的所有数据
     */
    Page<DO> listTo(@NotNull List<F> foreignKeyList, @NotNull Integer offset, @NotNull Integer size);

}
