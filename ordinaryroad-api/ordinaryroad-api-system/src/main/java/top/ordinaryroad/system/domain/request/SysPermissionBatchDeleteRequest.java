package top.ordinaryroad.system.domain.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import top.ordinaryroad.commons.core.base.request.delete.BaseBatchDeleteRequest;

/**
 * @author mjz
 * @date 2021/10/12
 */
@Getter
@Setter
@ApiModel
public class SysPermissionBatchDeleteRequest extends BaseBatchDeleteRequest {

    private static final long serialVersionUID = -1350589983724389235L;

}
