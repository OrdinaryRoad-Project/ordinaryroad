package tech.ordinaryroad.commons.core.base.request.delete;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.core.base.request.BaseRequest;

import javax.validation.constraints.NotBlank;

/**
 * 删除请求抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
public class BaseDeleteRequest extends BaseRequest {

    private static final long serialVersionUID = 3183898936938983687L;

    /**
     * 主键uuid
     */
    @ApiModelProperty("主键uuid")
    @NotBlank(message = "主键uuid不能为空")
    private String uuid;

}
