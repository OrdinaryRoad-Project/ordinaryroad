package tech.ordinaryroad.commons.core.base.mapper;


import tech.ordinaryroad.commons.core.base.model.BaseDO;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.DeleteByIdsMapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;

/**
 * Dao基类
 *
 * @param <DO> DO类
 * @author mjz
 * @date 2021/9/3
 */
public interface IBaseMapper<DO extends BaseDO> extends Mapper<DO>,
        DeleteByIdsMapper<DO>, SelectByIdsMapper<DO> {

}
