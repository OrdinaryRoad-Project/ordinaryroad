package top.ordinaryroad.system.dao;

import org.springframework.stereotype.Repository;
import top.ordinaryroad.commons.core.base.dao.IBaseLogicDeleteDao;
import top.ordinaryroad.system.entity.SysPermission;

import java.util.Optional;

/**
 * 权限实体数据库操作接口
 *
 * @author mjz
 * @date 2021/10/12
 */
@Repository
public interface SysPermissionDao extends IBaseLogicDeleteDao<SysPermission> {

    Optional<SysPermission> findByName(String name);

    Optional<SysPermission> findByCode(String code);

    Optional<SysPermission> findByNameAndCode(String name, String code);

}
