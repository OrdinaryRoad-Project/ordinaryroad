package top.ordinaryroad.system.service.impl;

import org.springframework.stereotype.Service;
import top.ordinaryroad.commons.core.biz.service.BaseLogicDeleteService;
import top.ordinaryroad.system.dao.SysRoleDao;
import top.ordinaryroad.system.entity.SysRole;
import top.ordinaryroad.system.service.ISysRoleService;

/**
 * 角色服务实现类
 *
 * @author mjz
 * @date 2021/10/12
 */
@Service
public class SysRoleServiceImpl extends BaseLogicDeleteService<SysRole, SysRoleDao>
        implements ISysRoleService {

    @Override
    public SysRole findByName(String name) {
        return dao.findByName(name).orElse(null);
    }

    @Override
    public SysRole findByCode(String code) {
        return dao.findByCode(code).orElse(null);
    }

    @Override
    public SysRole findByNameAndCode(String name, String code) {
        return dao.findByNameAndCode(name, code).orElse(null);
    }

}
