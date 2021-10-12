package top.ordinaryroad.system.dao;

import org.springframework.stereotype.Repository;
import top.ordinaryroad.commons.core.base.dao.IBaseLogicDeleteDao;
import top.ordinaryroad.system.entity.SysRequestPath;

import java.util.Optional;

/**
 * 请求路径实体数据库操作接口
 *
 * @author mjz
 * @date 2021/10/12
 */
@Repository
public interface SysRequestPathDao extends IBaseLogicDeleteDao<SysRequestPath> {

    Optional<SysRequestPath> findByName(String name);

    Optional<SysRequestPath> findByUrl(String url);

    Optional<SysRequestPath> findByNameAndUrl(String name, String url);

}
