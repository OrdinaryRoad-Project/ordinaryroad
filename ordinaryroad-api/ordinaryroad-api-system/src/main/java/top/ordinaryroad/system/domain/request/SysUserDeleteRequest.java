package top.ordinaryroad.system.domain.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import top.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;

/**
 * @author mjz
 * @date 2021/9/3
 */
@Getter
@Setter
@ApiModel
public class SysUserDeleteRequest extends BaseDeleteRequest {

    private static final long serialVersionUID = -8050945774434556696L;

}
