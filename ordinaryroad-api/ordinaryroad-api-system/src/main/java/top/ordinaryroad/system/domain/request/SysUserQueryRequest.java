package top.ordinaryroad.system.domain.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import top.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;

/**
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
@ApiModel
public class SysUserQueryRequest extends BaseQueryRequest {

    private static final long serialVersionUID = -2864923916395679134L;

}
