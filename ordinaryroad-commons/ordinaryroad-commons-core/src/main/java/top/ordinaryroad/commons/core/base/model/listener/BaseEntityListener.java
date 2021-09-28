package top.ordinaryroad.commons.core.base.model.listener;

import lombok.extern.slf4j.Slf4j;
import top.ordinaryroad.commons.core.base.model.BaseDO;
import top.ordinaryroad.commons.core.lang.Argument;
import top.ordinaryroad.commons.core.utils.IdUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.Objects;

/**
 * 相当于BaseDO的增删改查监听器
 *
 * @author mjz
 * @date 2021/9/9
 */
@Slf4j
public class BaseEntityListener {

    @PrePersist
    public void prePersist(Object object) {
        if (object instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) object;
            log.info("BaseDO 保存前的Id：{}", baseDO.getId());
            baseDO.setId(null);
            if (Objects.isNull(baseDO.getUuid())) {
                baseDO.setUuid(IdUtils.fastSimpleUUID());
            }
            if (Objects.isNull(baseDO.getDeleted())) {
                baseDO.setDeleted(false);
            }
            // @CreatedDate无效，在Entity的@EntityListeners加上AuditingEntityListener.class
            // @CreationTimestamp有效
            // baseDO.setCreatedTime(LocalDateTime.now());
            log.info("BaseDO 保存前：{}", object);
        }
    }

    @PreRemove
    public void preRemove(Object object) {
        if (object instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) object;
            log.info("BaseDO 删除前：{}", baseDO);
        }
    }

    @PreUpdate
    public void preUpdate(Object object) {
        if (object instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) object;
            Long id = baseDO.getId();
            log.info("BaseDO 更新前的Id：{}", id);
            if (Argument.isNotPositive(id)) {
                return;
            }
//            baseDO.setUpdateTime(LocalDateTime.now());
            log.info("BaseDO 更新前：{}", object);
        }
    }

}
