package tech.ordinaryroad.commons.mybatis.model;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DO基类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
@MappedSuperclass
public class BaseDO implements Serializable {

    private static final long serialVersionUID = -7515911796792892668L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * uuid主键
     */
    @Id
    private String uuid;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 创建者uuid
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新者uuid
     */
    private String updateBy;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
