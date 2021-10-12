package top.ordinaryroad.system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import top.ordinaryroad.commons.core.base.model.BaseLogicDeleteDO;
import top.ordinaryroad.system.listener.SysPermissionEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import java.util.Objects;
import java.util.Set;

/**
 * 权限表(SysPermission)实体类
 *
 * @author mjz
 * @date 2021/10/12
 */
@DynamicUpdate
@EntityListeners({SysPermissionEntityListener.class})
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class SysPermission extends BaseLogicDeleteDO {

    private static final long serialVersionUID = 6772936110005954183L;

    /**
     * 权限名
     */
    @Column(unique = true, nullable = false, length = 20)
    private String name;
    /**
     * 权限code
     */
    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @ManyToMany
    @ToString.Exclude
    private Set<SysRole> roles;
    @ManyToMany
    @ToString.Exclude
    private Set<SysRequestPath> requestPaths;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysPermission sysPermission = (SysPermission) o;
        return code.equals(sysPermission.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

}
