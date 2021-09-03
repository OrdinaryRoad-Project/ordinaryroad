package top.ordinaryroad.commons.core.base.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import top.ordinaryroad.commons.core.base.IBase;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * DO基类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
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
    @Column(unique = true, updatable = false, nullable = false)
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
    private Boolean deleted;

}
