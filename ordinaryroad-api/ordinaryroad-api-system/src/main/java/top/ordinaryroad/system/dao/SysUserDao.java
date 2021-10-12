package top.ordinaryroad.system.dao;

import org.springframework.stereotype.Repository;
import top.ordinaryroad.commons.core.base.dao.IBaseLogicDeleteDao;
import top.ordinaryroad.system.entity.SysUser;

import java.util.Optional;

@Repository
public interface SysUserDao extends IBaseLogicDeleteDao<SysUser> {

    Optional<SysUser> findByUsername(String username);

    Optional<SysUser> findByEmail(String email);

    Optional<SysUser> findByUsernameAndEmail(String username, String email);

}
