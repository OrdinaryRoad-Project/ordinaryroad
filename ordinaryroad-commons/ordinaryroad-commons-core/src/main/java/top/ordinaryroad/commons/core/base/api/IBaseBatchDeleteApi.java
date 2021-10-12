package top.ordinaryroad.commons.core.base.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.ordinaryroad.commons.core.base.dto.BaseDTO;
import top.ordinaryroad.commons.core.base.request.delete.BaseBatchDeleteRequest;
import top.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import top.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import top.ordinaryroad.commons.core.base.request.save.BaseSaveRequest;
import top.ordinaryroad.commons.core.base.result.Result;

import javax.validation.Valid;

/**
 * 增删改查控制器，带批量删除
 *
 * @param <DTO> DTO
 * @author qq1962247851
 * @date 2020/6/16 23:05
 */
public interface IBaseBatchDeleteApi<
        DTO extends BaseDTO,
        SR extends BaseSaveRequest,
        DR extends BaseDeleteRequest,
        BDR extends BaseBatchDeleteRequest,
        QR extends BaseQueryRequest>
        extends IBaseApi<DTO, SR, DR, QR> {

    /**
     * 批量删除
     *
     * @param baseBatchDeleteRequest BaseBatchDeleteRequest
     * @return result
     */
    @ApiOperation("批量删除")
    @PostMapping("batchDelete")
    Result<DTO> batchDelete(@RequestBody @Valid BDR baseBatchDeleteRequest);

}
