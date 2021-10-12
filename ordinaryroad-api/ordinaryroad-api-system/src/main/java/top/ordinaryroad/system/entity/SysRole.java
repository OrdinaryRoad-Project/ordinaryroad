package top.ordinaryroad.system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import top.ordinaryroad.commons.core.base.model.BaseLogicDeleteDO;
import top.ordinaryroad.system.listener.SysRoleEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import java.util.Objects;
import java.util.Set;

/**
 * 角色表(SysRole)实体类
 *
 * @author mjz
 * @date 2021/10/11
 */
@DynamicUpdate
@EntityListeners({SysRoleEntityListener.class})
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class SysRole extends BaseLogicDeleteDO {

    private static final long serialVersionUID = 5784303787988416775L;

    /**
     * 角色名
     */
    @Column(unique = true, nullable = false, length = 20)
    private String name;
    /**
     * 角色code
     */
    @Column(unique = true, nullable = false, length = 20)
    private String code;
    /**
     * 角色备注
     */
    @Column(length = 50)
    private String remark;

    @ManyToMany
    @ToString.Exclude
    private Set<SysUser> users;
    /**
     * 角色具有的权限
     */
    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private Set<SysPermission> permissions;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysRole sysRole = (SysRole) o;
        return code.equals(sysRole.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

}
