package top.ordinaryroad.commons.core.base.service;


import org.springframework.data.domain.Page;
import top.ordinaryroad.commons.core.base.model.BaseDO;

import javax.validation.constraints.NotNull;


/**
 * 通用增删改查Service，带有外键关联
 *
 * @param <DO> DO类
 * @param <F>  外键
 * @author qq1962247851
 * @date 2020/6/16 22:51
 */
public interface IDoubleForeignKeyFlagLogicDeleteService<DO extends BaseDO, F> extends IBaseLogicDeleteService<DO> {

    /**
     * 根据fromForeignKey分页查询所有isDeleted=false的数据
     *
     * @param foreignKey 外键
     * @param offset     偏移量，从0开始
     * @param size       多少条数据，默认为10
     * @return 分页的所有数据
     */
    Page<DO> listFrom(@NotNull F foreignKey, @NotNull Integer offset, @NotNull Integer size);

    /**
     * 根据fromForeignKey分页查询所有isDeleted=false的数据
     *
     * @param foreignKey 外键
     * @param offset     偏移量，从0开始
     * @param size       多少条数据，默认为10
     * @return 分页的所有数据
     */
    Page<DO> listTo(@NotNull F foreignKey, @NotNull Integer offset, @NotNull Integer size);

}
