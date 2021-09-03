package top.ordinaryroad.commons.core.base.request.query;

/**
 * 查询请求抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
public abstract class BaseQueryRequest implements IBaseQueryRequest {

    private static final long serialVersionUID = 6134687328637157875L;

    /**
     * 自增主键Id
     */
    private Long id;
    /**
     * 主键uuid
     */
    private String uuid;

}
