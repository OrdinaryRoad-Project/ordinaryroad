package top.ordinaryroad.system.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import top.ordinaryroad.commons.core.base.model.BaseLogicDeleteDO;
import top.ordinaryroad.system.listener.SysRequestPathEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToMany;
import java.util.Objects;
import java.util.Set;

/**
 * 请求路径表(SysRequestPath)实体类
 *
 * @author mjz
 * @date 2021/10/12
 */
@DynamicUpdate
@EntityListeners({SysRequestPathEntityListener.class})
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class SysRequestPath extends BaseLogicDeleteDO {

    private static final long serialVersionUID = 6772936110005954183L;

    /**
     * 请求路径名
     */
    @Column(unique = true, nullable = false, length = 20)
    private String name;
    /**
     * 请求路径url
     */
    @Column(unique = true, nullable = false, length = 50)
    private String url;

    /**
     * 请求路径需要的权限
     */
    @ManyToMany(mappedBy = "requestPaths")
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
        SysRequestPath sysRequestPath = (SysRequestPath) o;
        return url.equals(sysRequestPath.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

}
