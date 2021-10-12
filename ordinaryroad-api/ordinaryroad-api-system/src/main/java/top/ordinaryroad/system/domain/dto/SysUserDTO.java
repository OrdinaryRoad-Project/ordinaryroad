package top.ordinaryroad.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.ordinaryroad.commons.core.base.dto.BaseDTO;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * SysUserDTO
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
@ToString(callSuper = true)
@ApiModel
public class SysUserDTO extends BaseDTO {

    private static final long serialVersionUID = -3132379699984785393L;

    @ApiModelProperty("自增主键Id")
    private Long id;

    @ApiModelProperty("主键Uuid")
    private String uuid;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("出生日期")
    private LocalDateTime birthday;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("个人介绍")
    private String selfIntroduction;

    @ApiModelProperty("上次登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("角色")
    private Set<SysRoleDTO> roles;

}
