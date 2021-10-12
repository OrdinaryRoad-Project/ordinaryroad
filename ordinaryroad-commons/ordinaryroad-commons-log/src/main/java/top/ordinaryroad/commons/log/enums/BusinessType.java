package top.ordinaryroad.commons.log.enums;

/**
 * 业务操作类型
 *
 * @author ruoyi
 */
public enum BusinessType {
    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 恢复
     */
    RESTORE,

    /**
     * 批量删除
     */
    BATCH_DELETE,

    /**
     * 批量恢复
     */
    BATCH_RESTORE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,

    /**
     * 清空数据
     */
    CLEAN,
}
