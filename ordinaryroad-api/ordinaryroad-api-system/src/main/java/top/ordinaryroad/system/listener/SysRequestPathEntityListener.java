package top.ordinaryroad.system.listener;

import lombok.extern.slf4j.Slf4j;
import top.ordinaryroad.commons.core.base.cons.StatusCode;
import top.ordinaryroad.commons.core.base.exception.BaseException;
import top.ordinaryroad.commons.core.base.listener.BaseEntityListener;
import top.ordinaryroad.system.entity.SysRequestPath;
import top.ordinaryroad.system.service.ISysRequestPathService;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author mjz
 * @date 2021/10/12
 */
@Slf4j
public class SysRequestPathEntityListener extends BaseEntityListener<SysRequestPath, ISysRequestPathService> {

    @PrePersist
    public void prePersist(SysRequestPath sysRequestPath) {
        checkValid(sysRequestPath);
        String name = sysRequestPath.getName();
        if (Objects.nonNull(getService().findByName(name))) {
            throw new BaseException(StatusCode.NAME_ALREADY_EXIST);
        }
        String url = sysRequestPath.getUrl();
        if (Objects.nonNull(getService().findByUrl(url))) {
            throw new BaseException(StatusCode.URL_ALREADY_EXIST);
        }
        log.info("{}", sysRequestPath);
    }

    @PreUpdate
    public void preUpdate(Object o) {
        log.info("{}", o);
    }

    @PreRemove
    public void preRemove(Object o) {
        log.info("{}", o);
    }

    @PostLoad
    public void postLoad(Object o) {
    }

    @PostRemove
    public void postRemove(Object o) {
        log.info("{}", o);
    }

    @PostUpdate
    public void postUpdate(Object o) {
        log.info("{}", o);
    }

    @PostPersist
    public void postPersist(Object o) {
        log.info("{}", o);
    }

    @Override
    protected Class<ISysRequestPathService> getServiceClass() {
        return ISysRequestPathService.class;
    }

}
