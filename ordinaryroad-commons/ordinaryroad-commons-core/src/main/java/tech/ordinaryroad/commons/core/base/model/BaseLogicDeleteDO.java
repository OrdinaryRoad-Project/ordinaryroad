package tech.ordinaryroad.commons.core.base.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 逻辑删除DO基类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
@ToString(callSuper = true)
@MappedSuperclass
public class BaseLogicDeleteDO extends BaseDO {

    private static final long serialVersionUID = 3039982679601477924L;

    /**
     * 逻辑删除字段
     */
    @JSONField(serialize = false)
    @LogicDelete
    @Column(columnDefinition = "bit(1) not null default 0")
    private Boolean deleted;

}
