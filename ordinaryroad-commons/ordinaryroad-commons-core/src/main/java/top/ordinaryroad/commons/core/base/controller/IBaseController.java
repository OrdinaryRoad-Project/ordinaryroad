package top.ordinaryroad.commons.core.base.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import top.ordinaryroad.commons.core.base.dto.BaseDTO;
import top.ordinaryroad.commons.core.base.request.delete.BaseBatchDeleteRequest;
import top.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import top.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import top.ordinaryroad.commons.core.base.request.query.OffsetBasedPageRequest;
import top.ordinaryroad.commons.core.base.request.save.BaseSaveRequest;
import top.ordinaryroad.commons.core.base.result.Result;

/**
 * 增删改查控制器
 *
 * @param <DTO> DTO
 * @author qq1962247851
 * @date 2020/6/16 23:05
 */
public interface IBaseController<
        DTO extends BaseDTO,
        SR extends BaseSaveRequest,
        DR extends BaseDeleteRequest,
        BDR extends BaseBatchDeleteRequest,
        QR extends BaseQueryRequest> {

    /**
     * 新增
     *
     * @param saveRequest BaseSaveRequest
     * @return result
     */
    @PostMapping("insert")
    Result<DTO> insert(SR saveRequest);

    /**
     * 删除
     *
     * @param deleteRequest BaseDeleteRequest
     * @return result
     */
    @DeleteMapping("delete")
    Result<DTO> delete(DR deleteRequest);

    /**
     * 批量删除
     *
     * @param baseBatchDeleteRequest BaseBatchDeleteRequest
     * @return result
     */
    @DeleteMapping("batchDelete")
    Result<DTO> batchDelete(BDR baseBatchDeleteRequest);

    /**
     * 更新
     *
     * @param saveRequest BaseSaveRequest
     * @return result
     */
    @PostMapping("update")
    Result<DTO> update(SR saveRequest);

    /**
     * 根据主键找到数据
     *
     * @param queryRequest BaseQueryRequest
     * @return result
     */
    @GetMapping("find")
    Result<DTO> find(QR queryRequest);

    /**
     * 分页找到所有数据
     *
     * @param queryRequest           BaseQueryRequest
     * @param offsetBasedPageRequest OffsetBasedPageRequest
     * @return result
     */
    @GetMapping("findAll")
    Result<Page<DTO>> findAll(QR queryRequest, OffsetBasedPageRequest offsetBasedPageRequest);

}
