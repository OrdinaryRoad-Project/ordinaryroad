package top.ordinaryroad.commons.core.base.request.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询请求抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
@ApiModel("查询请求")
public abstract class BaseQueryRequest implements IBaseQueryRequest {

    private static final long serialVersionUID = 6134687328637157875L;

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

    /**
     * 个数
     */
    @ApiModelProperty("个数")
    private Integer limit;

    /**
     * 偏移量
     */
    @ApiModelProperty("偏移量")
    private Integer offset;

}
