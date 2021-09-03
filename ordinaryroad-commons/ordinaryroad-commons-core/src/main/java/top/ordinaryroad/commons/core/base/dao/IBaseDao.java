package top.ordinaryroad.commons.core.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.ordinaryroad.commons.core.base.model.BaseDO;

/**
 * Dao基类
 *
 * @param <DO> DO类
 * @author mjz
 * @date 2021/9/3
 */
public interface IBaseDao<DO extends BaseDO> extends JpaRepository<DO, Long>, JpaSpecificationExecutor<DO> {

}
