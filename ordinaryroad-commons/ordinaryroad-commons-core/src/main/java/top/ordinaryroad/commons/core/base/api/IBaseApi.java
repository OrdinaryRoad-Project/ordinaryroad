package top.ordinaryroad.commons.core.base.api;

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
 * 增删改查控制器
 *
 * @param <DTO> DTO
 * @author qq1962247851
 * @date 2020/6/16 23:05
 */
public interface IBaseApi<
        DTO extends BaseDTO,
        SR extends BaseSaveRequest,
        DR extends BaseDeleteRequest,
        QR extends BaseQueryRequest> {

    /**
     * 新增
     *
     * @param saveRequest BaseSaveRequest
     * @return result
     */
    @ApiOperation("新增")
    @PostMapping("insert")
    Result<?> insert(@RequestBody @Valid SR saveRequest);

    /**
     * 删除
     *
     * @param deleteRequest BaseDeleteRequest
     * @return result
     */
    @ApiOperation("删除")
    @PostMapping("delete")
    Result<?> delete(@RequestBody @Valid DR deleteRequest);

    /**
     * 更新
     *
     * @param saveRequest BaseSaveRequest
     * @return result
     */
    @ApiOperation("更新")
    @PostMapping("update")
    Result<?> update(@RequestBody @Valid SR saveRequest);

    /**
     * 根据主键找到数据
     *
     * @param queryRequest BaseQueryRequest
     * @return result
     */
    @ApiOperation("根据主键找到数据")
    @PostMapping("find")
    Result<?> find(@RequestBody QR queryRequest);

    /**
     * 分页找到所有数据
     *
     * @param queryRequest BaseQueryRequest
     * @return result
     */
    @ApiOperation("分页找到所有数据")
    @PostMapping("findAll")
    Result<Page<DTO>> findAll(@RequestBody QR queryRequest);

}
