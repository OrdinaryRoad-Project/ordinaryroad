package top.ordinaryroad.system.service.impl;

import org.springframework.stereotype.Service;
import top.ordinaryroad.commons.core.biz.service.BaseLogicDeleteService;
import top.ordinaryroad.system.dao.SysPermissionDao;
import top.ordinaryroad.system.entity.SysPermission;
import top.ordinaryroad.system.service.ISysPermissionService;

/**
 * 权限服务实现类
 *
 * @author mjz
 * @date 2021/10/12
 */
@Service
public class SysPermissionServiceImpl extends BaseLogicDeleteService<SysPermission, SysPermissionDao>
        implements ISysPermissionService {

    @Override
    public SysPermission findByName(String name) {
        return dao.findByName(name).orElse(null);
    }

    @Override
    public SysPermission findByCode(String code) {
        return dao.findByCode(code).orElse(null);
    }

    @Override
    public SysPermission findByNameAndCode(String name, String code) {
        return dao.findByNameAndCode(name, code).orElse(null);
    }

}
