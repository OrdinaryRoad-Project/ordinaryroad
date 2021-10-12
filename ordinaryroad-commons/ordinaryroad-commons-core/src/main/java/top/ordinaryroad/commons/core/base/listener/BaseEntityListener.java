package top.ordinaryroad.commons.core.base.listener;

import top.ordinaryroad.commons.core.base.cons.StatusCode;
import top.ordinaryroad.commons.core.base.exception.BaseException;
import top.ordinaryroad.commons.core.base.model.BaseDO;
import top.ordinaryroad.commons.core.base.service.IBaseService;
import top.ordinaryroad.commons.core.lang.Argument;
import top.ordinaryroad.commons.core.utils.SpringUtils;

import java.util.Objects;

/**
 * @author mjz
 * @date 2021/10/12
 */
public abstract class BaseEntityListener<DO extends BaseDO, IService extends IBaseService<DO>> {

    private volatile IService service;

    protected void checkValid(DO baseDO) {
        String uuid = baseDO.getUuid();
        if (Objects.nonNull(getService().find(uuid))) {
            throw new BaseException(StatusCode.UUID_ALREADY_EXIST);
        }

        Long id = baseDO.getId();
        if (Argument.isPositive(id)) {
            // 更新校验
        } else {
            // 新增校验
        }

    }

    /**
     * 获取Service的Class，用于getBean
     *
     * @return Class
     */
    protected abstract Class<IService> getServiceClass();

    public IService getService() {
        if (service == null) {
            synchronized (BaseEntityListener.this) {
                if (service == null) {
                    service = SpringUtils.getBean(getServiceClass());
                }
                return service;
            }
        }
        return service;
    }

}
