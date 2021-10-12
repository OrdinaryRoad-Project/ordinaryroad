package top.ordinaryroad.system.domain.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import top.ordinaryroad.commons.core.base.request.delete.BaseBatchDeleteRequest;

/**
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
@ApiModel
public class SysUserBatchDeleteRequest extends BaseBatchDeleteRequest {

    private static final long serialVersionUID = -3369874658179605012L;

}
