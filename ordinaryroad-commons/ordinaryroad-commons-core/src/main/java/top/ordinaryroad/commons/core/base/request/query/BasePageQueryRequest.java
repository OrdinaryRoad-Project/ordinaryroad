package top.ordinaryroad.commons.core.base.request.query;

import org.springframework.data.domain.Pageable;

/**
 * 分页查询请求抽象类
 *
 * @author mjz
 * @date 2021/9/3
 */
public abstract class BasePageQueryRequest implements IBaseQueryRequest, Pageable {

    private static final long serialVersionUID = 81847343537158699L;

}