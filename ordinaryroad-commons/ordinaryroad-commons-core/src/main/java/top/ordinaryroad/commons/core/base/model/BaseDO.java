package top.ordinaryroad.commons.core.base.model;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import top.ordinaryroad.commons.core.base.IBase;
import top.ordinaryroad.commons.core.base.model.listener.BaseEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * DO基类
 *
 * @author mjz
 * @date 2021/9/3
 */
@DynamicUpdate
@EntityListeners({BaseEntityListener.class, AuditingEntityListener.class})
@Getter
@Setter
@MappedSuperclass
public class BaseDO implements IBase {

    private static final long serialVersionUID = -1648098683103489271L;

    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false, nullable = false)
    private Long id;

    /**
     * uuid主键
     */
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    @Column(unique = true, updatable = false, nullable = false, length = 32)
    private String uuid;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime updateTime;

    /**
     * 逻辑删除字段
     */
    @Column(columnDefinition = "bit(1) not null default 0")
    private Boolean deleted;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
