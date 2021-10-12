package top.ordinaryroad.system.listener;

import lombok.extern.slf4j.Slf4j;
import top.ordinaryroad.commons.core.base.cons.StatusCode;
import top.ordinaryroad.commons.core.base.exception.BaseException;
import top.ordinaryroad.commons.core.base.listener.BaseEntityListener;
import top.ordinaryroad.system.entity.SysPermission;
import top.ordinaryroad.system.service.ISysPermissionService;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author mjz
 * @date 2021/10/12
 */
@Slf4j
public class SysPermissionEntityListener extends BaseEntityListener<SysPermission, ISysPermissionService> {

    @PrePersist
    public void prePersist(SysPermission sysPermission) {
        checkValid(sysPermission);
        String name = sysPermission.getName();
        if (Objects.nonNull(getService().findByName(name))) {
            throw new BaseException(StatusCode.NAME_ALREADY_EXIST);
        }
        String code = sysPermission.getCode();
        if (Objects.nonNull(getService().findByCode(code))) {
            throw new BaseException(StatusCode.CODE_ALREADY_EXIST);
        }
        log.info("{}", sysPermission);
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
    protected Class<ISysPermissionService> getServiceClass() {
        return ISysPermissionService.class;
    }

}
