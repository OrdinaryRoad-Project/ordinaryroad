package top.ordinaryroad.commons.core.base.request.save;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 新增/更新请求抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
@ApiModel("新增/更新请求")
public abstract class BaseSaveRequest implements IBaseSaveRequest {

    private static final long serialVersionUID = 3638692699646721249L;

    /**
     * 自增主键Id
     */
    @ApiModelProperty("自增主键Id")
    private Long id;

    /**
     * 主键uuid
     */
    @ApiModelProperty("主键uuid")
    private String uuid;

}
