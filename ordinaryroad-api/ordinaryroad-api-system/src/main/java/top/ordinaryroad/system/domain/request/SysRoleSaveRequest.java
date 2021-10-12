package top.ordinaryroad.system.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.ordinaryroad.commons.core.base.request.save.BaseSaveRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author mjz
 * @date 2021/10/12
 */
@Getter
@Setter
@ApiModel
public class SysRoleSaveRequest extends BaseSaveRequest {

    private static final long serialVersionUID = 4673207417159830679L;

    @ApiModelProperty("角色名")
    @NotBlank(message = "角色名不能为空")
    @Size(max = 20, message = "角色名长度不能超过20")
    private String name;

    @ApiModelProperty("角色code")
    @NotBlank(message = "角色code不能为空")
    @Size(max = 20, message = "角色code长度不能超过20")
    private String code;

    @ApiModelProperty("角色备注")
    @Size(max = 50, message = "角色备注长度不能超过50")
    private String remark;

}
