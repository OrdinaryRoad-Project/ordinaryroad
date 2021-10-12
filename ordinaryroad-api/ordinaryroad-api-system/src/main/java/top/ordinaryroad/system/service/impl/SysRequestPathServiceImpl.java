package top.ordinaryroad.system.service.impl;

import org.springframework.stereotype.Service;
import top.ordinaryroad.commons.core.biz.service.BaseLogicDeleteService;
import top.ordinaryroad.system.dao.SysRequestPathDao;
import top.ordinaryroad.system.entity.SysRequestPath;
import top.ordinaryroad.system.service.ISysRequestPathService;

/**
 * 请求路径服务实现类
 *
 * @author mjz
 * @date 2021/10/12
 */
@Service
public class SysRequestPathServiceImpl extends BaseLogicDeleteService<SysRequestPath, SysRequestPathDao>
        implements ISysRequestPathService {

    @Override
    public SysRequestPath findByName(String name) {
        return dao.findByName(name).orElse(null);
    }

    @Override
    public SysRequestPath findByUrl(String url) {
        return dao.findByUrl(url).orElse(null);
    }

    @Override
    public SysRequestPath findByNameAndUrl(String name, String url) {
        return dao.findByNameAndUrl(name, url).orElse(null);
    }

}
