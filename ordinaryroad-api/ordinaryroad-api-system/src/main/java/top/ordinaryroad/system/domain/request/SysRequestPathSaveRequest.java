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
public class SysRequestPathSaveRequest extends BaseSaveRequest {

    private static final long serialVersionUID = -619320012193287774L;

    @ApiModelProperty("请求路径名")
    @NotBlank(message = "请求路径名不能为空")
    @Size(max = 20, message = "请求路径名长度不能超过20")
    private String name;

    @ApiModelProperty("请求路径url")
    @NotBlank(message = "请求路径url不能为空")
    @Size(max = 50, message = "请求路径url长度不能超过50")
    private String url;

}
