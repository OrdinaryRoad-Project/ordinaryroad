package top.ordinaryroad.system.api;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import top.ordinaryroad.commons.core.base.api.IBaseLogicBatchDeleteApi;
import top.ordinaryroad.system.domain.dto.SysRequestPathDTO;
import top.ordinaryroad.system.domain.request.SysRequestPathBatchDeleteRequest;
import top.ordinaryroad.system.domain.request.SysRequestPathDeleteRequest;
import top.ordinaryroad.system.domain.request.SysRequestPathQueryRequest;
import top.ordinaryroad.system.domain.request.SysRequestPathSaveRequest;

/**
 * 请求路径API
 *
 * @author mjz
 * @date 2021/10/12
 */
@Api(value = "sys_request_path")
@RequestMapping(value = "/request_path", produces = {MediaType.APPLICATION_JSON_VALUE})
public interface ISysRequestPathApi extends IBaseLogicBatchDeleteApi<
        SysRequestPathDTO,
        SysRequestPathSaveRequest,
        SysRequestPathDeleteRequest,
        SysRequestPathBatchDeleteRequest,
        SysRequestPathQueryRequest> {

}
