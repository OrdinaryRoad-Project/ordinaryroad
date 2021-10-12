package top.ordinaryroad.system.domain.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import top.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;

/**
 * @author mjz
 * @date 2021/10/12
 */
@Getter
@Setter
@ApiModel
public class SysPermissionDeleteRequest extends BaseDeleteRequest {

    private static final long serialVersionUID = -5082207986251104529L;

}
