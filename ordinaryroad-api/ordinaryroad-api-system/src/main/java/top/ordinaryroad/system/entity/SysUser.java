
package top.ordinaryroad.system.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import top.ordinaryroad.commons.core.base.model.BaseLogicDeleteDO;
import top.ordinaryroad.system.listener.SysUserEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * 用户表(SysUser)实体类
 *
 * @author mjz
 * @date 2021/9/3
 */
@DynamicUpdate
@EntityListeners({SysUserEntityListener.class})
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class SysUser extends BaseLogicDeleteDO {

    private static final long serialVersionUID = -8661112854678500165L;

    /**
     * 用户名
     */
    @Column(unique = true, nullable = false, length = 20)
    private String username;
    /**
     * 性别
     */
    @Column(length = 2)
    private Integer sex;
    /**
     * 出生日期
     */
    private LocalDateTime birthday;
    /**
     * 邮箱
     */
    @Column(unique = true, length = 50)
    private String email;
    /**
     * 手机号码
     */
    @Column(unique = true, length = 11)
    private String phone;
    /**
     * 用户密码
     */
    @Column(nullable = false, length = 128)
    private String password;
    /**
     * 个人介绍
     */
    private String selfIntroduction;
    /**
     * 上一次登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 账号是否可用。默认为1（可用）
     */
    @Column(columnDefinition = "bit(1) not null default 1")
    private Boolean enabled;
    /**
     * 是否过期。默认为1（没有过期）
     */
    @Column(columnDefinition = "bit(1) not null default 1")
    private Boolean notExpired;
    /**
     * 账号是否锁定。默认为1（没有锁定）
     */
    @Column(columnDefinition = "bit(1) not null default 1")
    private Boolean notLocked;
    /**
     * 密码是否过期。默认为1（没有过期）
     */
    @Column(columnDefinition = "bit(1) not null default 1")
    private Boolean passwordNotExpired;

    /**
     * 用户具有的角色
     */
    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    private Set<SysRole> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysUser sysUser = (SysUser) o;
        return username.equals(sysUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}