package top.ordinaryroad.system.dao;

import org.springframework.stereotype.Repository;
import top.ordinaryroad.commons.core.base.dao.IBaseLogicDeleteDao;
import top.ordinaryroad.system.entity.SysRole;

import java.util.Optional;

/**
 * 角色实体数据库操作接口
 *
 * @author mjz
 * @date 2021/10/12
 */
@Repository
public interface SysRoleDao extends IBaseLogicDeleteDao<SysRole> {

    Optional<SysRole> findByName(String name);

    Optional<SysRole> findByCode(String code);

    Optional<SysRole> findByNameAndCode(String name, String code);

}
