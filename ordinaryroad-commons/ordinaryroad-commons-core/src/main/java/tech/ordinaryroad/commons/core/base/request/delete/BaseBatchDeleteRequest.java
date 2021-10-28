package tech.ordinaryroad.commons.core.base.request.delete;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 批量删除请求抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
public class BaseBatchDeleteRequest implements IBaseDeleteRequest {

    private static final long serialVersionUID = -1429115501643362949L;

    /**
     * 主键uuid列表
     */
    @ApiModelProperty("主键uuid列表")
    private List<String> uuids;

}
