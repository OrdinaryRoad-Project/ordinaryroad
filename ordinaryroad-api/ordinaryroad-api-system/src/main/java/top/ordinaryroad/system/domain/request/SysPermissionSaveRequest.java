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
public class SysPermissionSaveRequest extends BaseSaveRequest {

    private static final long serialVersionUID = 6627960875281777597L;

    @ApiModelProperty("权限名")
    @NotBlank(message = "权限名不能为空")
    @Size(max = 20, message = "权限名长度不能超过20")
    private String name;

    @ApiModelProperty("权限code")
    @NotBlank(message = "权限code不能为空")
    @Size(max = 20, message = "权限code长度不能超过20")
    private String code;

}
