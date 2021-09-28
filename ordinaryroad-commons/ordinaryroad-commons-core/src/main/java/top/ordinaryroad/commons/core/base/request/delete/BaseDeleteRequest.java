package top.ordinaryroad.commons.core.base.request.delete;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 删除请求抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
@ApiModel("删除请求")
public abstract class BaseDeleteRequest implements IBaseDeleteRequest {

    private static final long serialVersionUID = -1429115501643362949L;

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
