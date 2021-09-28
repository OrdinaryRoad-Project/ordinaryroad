package top.ordinaryroad.commons.core.base.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import top.ordinaryroad.commons.core.base.exception.BatchDeleteException;
import top.ordinaryroad.commons.core.base.exception.LogicDeleteException;
import top.ordinaryroad.commons.core.base.exception.LogicRestoreException;
import top.ordinaryroad.commons.core.base.model.BaseDO;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * 逻辑删除Dao基类
 *
 * @param <DO> DO类
 * @author mjz
 * @date 2021/9/3
 */
public interface IBaseLogicDeleteDao<DO extends BaseDO> extends IBaseDao<DO> {

    /**
     * 逻辑删除
     *
     * @param id 主键
     */
    @Modifying
    @Transactional(rollbackOn = {LogicDeleteException.class})
    @Query("UPDATE #{#entityName} t SET t.deleted = TRUE WHERE t.id = ?1")
    @Override
    void deleteById(@NotNull Long id);

    /**
     * 根据Id批量逻辑删除
     *
     * @param ids id主键列表
     */
    @Modifying
    @Transactional(rollbackOn = {BatchDeleteException.class})
    @Query("UPDATE #{#entityName} t SET t.deleted = TRUE WHERE t.id in (?1)")
    @Override
    void deleteAllById(@NotNull Iterable<? extends Long> ids);

    /**
     * 逻辑删除
     *
     * @param uuid 主键
     */
    @Modifying
    @Transactional(rollbackOn = {LogicDeleteException.class})
    @Query("UPDATE #{#entityName} t SET t.deleted = TRUE WHERE t.uuid = ?1")
    @Override
    void deleteByUuid(@NotNull String uuid);

    /**
     * 根据Uuid批量逻辑删除
     *
     * @param uuids uuid主键列表
     */
    @Modifying
    @Transactional(rollbackOn = {BatchDeleteException.class})
    @Query("UPDATE #{#entityName} t SET t.deleted = TRUE WHERE t.uuid in (?1)")
    @Override
    void deleteAllByUuid(@NotNull List<String> uuids);

    /**
     * 恢复
     *
     * @param id 主键
     */
    @Modifying
    @Transactional(rollbackOn = {LogicRestoreException.class})
    @Query("UPDATE #{#entityName} t SET t.deleted = FALSE WHERE t.id = ?1")
    void restore(@NotNull Long id);

    /**
     * 恢复
     *
     * @param uuid 主键
     */
    @Modifying
    @Transactional(rollbackOn = {LogicRestoreException.class})
    @Query("UPDATE #{#entityName} t SET t.deleted = FALSE WHERE t.uuid = ?1")
    void restore(@NotNull String uuid);

    /**
     * 根据主键查询未被删除的数据
     *
     * @param id 主键
     * @return 实体
     */
    @Query("FROM #{#entityName} t WHERE t.id = ?1 AND t.deleted = FALSE")
    @NotNull
    Optional<DO> query(@NotNull Long id);

}
