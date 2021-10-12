package top.ordinaryroad.system.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import top.ordinaryroad.commons.core.base.cons.StatusCode;
import top.ordinaryroad.commons.core.base.exception.BaseException;
import top.ordinaryroad.commons.core.base.listener.BaseEntityListener;
import top.ordinaryroad.commons.core.lang.Argument;
import top.ordinaryroad.system.entity.SysUser;
import top.ordinaryroad.system.service.ISysUserService;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author mjz
 * @date 2021/9/9
 */
@Slf4j
public class SysUserEntityListener extends BaseEntityListener<SysUser, ISysUserService> {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PrePersist
    public void prePersist(SysUser sysUser) {
        checkValid(sysUser);
        sysUser.setPassword("{bcrypt}" + bCryptPasswordEncoder.encode(sysUser.getPassword()));
        if (Objects.isNull(sysUser.getEnabled())) {
            sysUser.setEnabled(true);
        }
        if (Objects.isNull(sysUser.getNotExpired())) {
            sysUser.setNotExpired(true);
        }
        if (Objects.isNull(sysUser.getNotLocked())) {
            sysUser.setNotLocked(true);
        }
        if (Objects.isNull(sysUser.getPasswordNotExpired())) {
            sysUser.setPasswordNotExpired(true);
        }
        String username = sysUser.getUsername();
        if (Objects.nonNull(getService().findByUsername(username))) {
            throw new BaseException(StatusCode.USERNAME_ALREADY_EXIST);
        }
        String email = sysUser.getEmail();
        if (Argument.isNotNull(email)) {
            if (Objects.nonNull(getService().findByEmail(email))) {
                throw new BaseException(StatusCode.EMAIL_ALREADY_EXIST);
            }
        }
        log.info("{}", sysUser);
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
    protected Class<ISysUserService> getServiceClass() {
        return ISysUserService.class;
    }

}
