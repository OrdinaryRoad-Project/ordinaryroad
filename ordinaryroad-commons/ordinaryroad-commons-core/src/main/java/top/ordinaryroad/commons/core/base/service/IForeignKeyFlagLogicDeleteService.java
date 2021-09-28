package top.ordinaryroad.commons.core.base.service;


import org.springframework.data.domain.Page;
import top.ordinaryroad.commons.core.base.model.BaseDO;

import javax.validation.constraints.NotNull;


/**
 * 通用增删改查Service，带有外键关联，实体类需要带有deleted字段
 *
 * @param <DO> DO类
 * @param <F>  外键
 * @author qq1962247851
 * @date 2020/6/16 22:51
 */
public interface IForeignKeyFlagLogicDeleteService<DO extends BaseDO, F> extends IBaseLogicDeleteService<DO> {

    /**
     * 根据关联的外键分页查询所有deleted=false的数据
     *
     * @param foreignKey 外键
     * @param offset     偏移量，从0开始
     * @param size       多少条数据，默认为10
     * @return 分页的所有数据
     */
    Page<DO> listByForeignKey(@NotNull F foreignKey, @NotNull Integer offset, @NotNull Integer size);

}
