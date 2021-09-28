package top.ordinaryroad.commons.core.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import top.ordinaryroad.commons.core.base.dao.IBaseDao;
import top.ordinaryroad.commons.core.base.model.BaseDO;
import top.ordinaryroad.commons.core.base.request.query.OffsetBasedPageRequest;
import top.ordinaryroad.commons.core.base.service.IBaseService;
import top.ordinaryroad.commons.core.utils.bean.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service抽象基类
 *
 * @author mjz
 * @date 2021/9/6
 */
public abstract class BaseService<DO extends BaseDO, DAO extends IBaseDao<DO>> implements IBaseService<DO> {

    @Autowired
    protected DAO dao;

    @Override
    public DO insert(@NotNull DO entity) {
        entity.setId(null);
        return dao.save(entity);
    }

    @Override
    public <KEY> Boolean delete(@NotNull KEY key) {
        if (key instanceof Long) {
            return delete((Long) key);
        } else if (key instanceof String) {
            return delete((String) key);
        } else {
            return false;
        }
    }

    @Override
    public <T> Boolean batchDelete(@NotNull List<T> list) {
        if (!list.isEmpty()) {
            T t = list.get(0);
            try {
                if (t instanceof Long) {
                    dao.deleteAllById(list.stream().map(t1 -> (Long) t1).collect(Collectors.toList()));
                } else if (t instanceof String) {
                    dao.deleteAllByUuid(list.stream().map(t1 -> (String) t1).collect(Collectors.toList()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public DO update(@NotNull DO entity) {
        // 不允许更新Uuid
        entity.setUuid(null);
        DO aDo = find(entity.getId());
        BeanUtils.copyBeanNotNullProp(aDo, entity);
        return dao.saveAndFlush(aDo);
    }

    @Override
    public <KEY> DO find(@NotNull KEY key) {
        if (key instanceof Long) {
            return find((Long) key);
        } else if (key instanceof String) {
            return find((String) key);
        } else {
            return null;
        }
    }

    @Override
    public Page<DO> findAll(@NotNull Example<DO> exampleDO, @NotNull Integer offset, @NotNull Integer size) {
        exampleDO.getProbe().setId(null);
        return dao.findAll(exampleDO, OffsetBasedPageRequest.of(offset, size));
    }

    /**
     * 根据Id删除
     *
     * @param id 主键
     * @return 是否成功
     */
    private Boolean delete(@NotNull Long id) {
        try {
            dao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据Uuid删除
     *
     * @param uuid 主键
     * @return 是否成功
     */
    private Boolean delete(@NotNull String uuid) {
        try {
            dao.deleteByUuid(uuid);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据Id查找
     *
     * @param id 主键
     * @return DO
     */
    private DO find(@NotNull Long id) {
        return dao.findById(id).orElse(null);
    }

    /**
     * 根据Uuid查找
     *
     * @param uuid 主键
     * @return DO
     */
    private DO find(@NotNull String uuid) {
        return dao.findByUuid(uuid).orElse(null);
    }

}
