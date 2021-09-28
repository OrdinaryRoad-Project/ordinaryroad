package top.ordinaryroad.commons.core.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import top.ordinaryroad.commons.core.base.exception.BatchDeleteException;
import top.ordinaryroad.commons.core.base.exception.DeleteException;
import top.ordinaryroad.commons.core.base.model.BaseDO;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Dao基类
 *
 * @param <DO> DO类
 * @author mjz
 * @date 2021/9/3
 */
public interface IBaseDao<DO extends BaseDO> extends JpaRepository<DO, Long>, JpaSpecificationExecutor<DO> {

    /**
     * 根据Id删除
     *
     * @param id id主键
     */
    @Modifying
    @Transactional(rollbackOn = {DeleteException.class})
    @Query("DELETE FROM #{#entityName} t WHERE t.id = ?1")
    @Override
    void deleteById(@NotNull Long id);

    /**
     * 根据Id批量删除
     *
     * @param ids id主键列表
     */
    @Modifying
    @Transactional(rollbackOn = {BatchDeleteException.class})
    @Query("DELETE FROM #{#entityName} t WHERE t.id in (?1)")
    @Override
    void deleteAllById(@NotNull Iterable<? extends Long> ids);

    /**
     * 根据Uuid删除
     *
     * @param uuid 主键
     */
    @Modifying
    @Transactional(rollbackOn = {DeleteException.class})
    @Query("DELETE FROM #{#entityName} t WHERE t.uuid = ?1")
    void deleteByUuid(@NotNull String uuid);

    /**
     * 根据Uuid批量删除
     *
     * @param uuids uuid主键列表
     */
    @Modifying
    @Transactional(rollbackOn = {BatchDeleteException.class})
    @Query("DELETE FROM #{#entityName} t WHERE t.uuid in (?1)")
    void deleteAllByUuid(@NotNull List<String> uuids);

    /**
     * 根据Uuid查询
     *
     * @param uuid 主键
     * @return DO
     */
    @Query("FROM #{#entityName} t WHERE t.uuid = ?1")
    Optional<DO> findByUuid(@NotNull String uuid);

}
