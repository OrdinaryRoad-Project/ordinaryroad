package top.ordinaryroad.system.domain.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import top.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;

/**
 * @author mjz
 * @date 2021/10/12
 */
@Getter
@Setter
@ApiModel
public class SysRoleQueryRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 3871002124264672693L;

}
