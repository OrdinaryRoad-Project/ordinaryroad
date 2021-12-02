package tech.ordinaryroad.commons.core.base.request.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import tech.ordinaryroad.commons.core.base.request.BaseRequest;

import java.util.List;

/**
 * 查询请求抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
public class BaseQueryRequest extends BaseRequest {

    private static final long serialVersionUID = -3803036361359796031L;

    /**
     * 主键uuid
     */
    @ApiModelProperty("主键uuid")
    private String uuid;

    /**
     * 主键uuid列表
     */
    @ApiModelProperty("主键uuid列表")
    private List<String> uuids;

    /**
     * 个数
     */
    @ApiModelProperty("个数")
    private Integer limit = 20;

    /**
     * 偏移量
     */
    @ApiModelProperty("偏移量")
    private Integer offset = 0;

}
