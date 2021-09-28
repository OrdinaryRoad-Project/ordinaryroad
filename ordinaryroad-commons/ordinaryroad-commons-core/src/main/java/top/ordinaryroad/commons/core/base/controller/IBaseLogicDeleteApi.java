package top.ordinaryroad.commons.core.base.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.ordinaryroad.commons.core.base.dto.BaseDTO;
import top.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import top.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import top.ordinaryroad.commons.core.base.request.save.BaseSaveRequest;
import top.ordinaryroad.commons.core.base.result.Result;

import javax.validation.Valid;

/**
 * 逻辑删除，增删改查控制器
 *
 * @param <DTO> DTO
 * @author qq1962247851
 * @date 2020/6/16 23:05
 */
public interface IBaseLogicDeleteApi<
        DTO extends BaseDTO,
        SR extends BaseSaveRequest,
        DR extends BaseDeleteRequest,
        QR extends BaseQueryRequest>
        extends IBaseApi<DTO, SR, DR, QR> {

    /**
     * 删除
     *
     * @param deleteRequest BaseDeleteRequest
     * @return result
     */
    @ApiOperation("恢复")
    @PostMapping("restore")
    Result<?> restore(@RequestBody @Valid DR deleteRequest);


    /**
     * 根据主键找到未被删除的数据
     *
     * @param queryRequest BaseQueryRequest
     * @return result
     */
    @ApiOperation("根据主键找到未被删除的数据")
    @PostMapping("query")
    Result<?> query(@RequestBody QR queryRequest);

    /**
     * 分页找到所有未被删除的数据
     *
     * @param queryRequest BaseQueryRequest
     * @return result
     */
    @ApiOperation("分页找到所有未被删除的数据")
    @PostMapping("list")
    Result<Page<DTO>> list(@RequestBody QR queryRequest);

}
