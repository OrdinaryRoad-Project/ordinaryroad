package top.ordinaryroad.system.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.ordinaryroad.commons.core.base.request.save.BaseSaveRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
@ApiModel
public class SysUserSaveRequest extends BaseSaveRequest {

    private static final long serialVersionUID = -1038286920582045172L;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    @Size(max = 20, message = "用户名长度不能超过20")
    private String username;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("出生日期")
    private LocalDateTime birthday;

    @ApiModelProperty("邮箱")
    @Size(max = 50, message = "邮箱长度不能超过50")
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "邮箱格式错误")
    private String email;

    @ApiModelProperty("手机号码")
    @Size(max = 11, message = "手机号码不能超过11位")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "手机号码格式错误")
    private String phone;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 16, message = "密码长度 8 - 16")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$", message = "密码必须包含大小写字母和数字，可以使用特殊字符")
    private String password;

    @ApiModelProperty("个人介绍")
    private String selfIntroduction;

    @ApiModelProperty("上一次登录时间")
    private LocalDateTime lastLoginTime;

}
