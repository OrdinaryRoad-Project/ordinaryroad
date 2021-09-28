package top.ordinaryroad.commons.core.biz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import top.ordinaryroad.commons.core.base.dao.IBaseLogicDeleteDao;
import top.ordinaryroad.commons.core.base.model.BaseLogicDeleteDO;
import top.ordinaryroad.commons.core.base.request.query.OffsetBasedPageRequest;
import top.ordinaryroad.commons.core.base.service.IBaseLogicDeleteService;

import javax.validation.constraints.NotNull;

/**
 * 逻辑删除Service抽象基类
 *
 * @author mjz
 * @date 2021/9/6
 */
@Slf4j
public abstract class BaseLogicDeleteService<DO extends BaseLogicDeleteDO, DAO extends IBaseLogicDeleteDao<DO>>
        extends BaseService<DO, DAO>
        implements IBaseLogicDeleteService<DO> {

    @Override
    public <KEY> Boolean restore(@NotNull KEY key) {
        if (key instanceof Long) {
            return restore((Long) key);
        } else if (key instanceof String) {
            return restore((String) key);
        } else {
            return false;
        }
    }

    @Override
    public <KEY> DO query(@NotNull KEY key) {
        if (key instanceof Long) {
            return query((Long) key);
        } else if (key instanceof String) {
            return query((String) key);
        } else {
            return null;
        }
    }

    @Override
    public Page<DO> list(@NotNull Example<DO> exampleDO, @NotNull Integer offset, @NotNull Integer size) {
        exampleDO.getProbe().setId(null);
        exampleDO.getProbe().setDeleted(false);
        return dao.findAll(exampleDO, OffsetBasedPageRequest.of(offset, size));
    }

    /**
     * 根据Id恢复
     *
     * @param id 主键
     * @return 是否成功
     */
    private Boolean restore(@NotNull Long id) {
        try {
            dao.restore(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据Uuid恢复
     *
     * @param uuid 主键
     * @return 是否成功
     */
    private Boolean restore(@NotNull String uuid) {
        try {
            dao.restore(uuid);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据Id查询未被删除的数据
     *
     * @param id 主键
     * @return DO
     */
    private DO query(@NotNull Long id) {
        DO aDo = find(id);
        if (aDo == null || aDo.getDeleted()) {
            return null;
        }
        return aDo;
    }

    /**
     * 根据Uuid查询未被删除的数据
     *
     * @param uuid 主键
     * @return DO
     */
    private DO query(@NotNull String uuid) {
        DO aDo = find(uuid);
        if (aDo == null || aDo.getDeleted()) {
            return null;
        }
        return aDo;
    }

}
