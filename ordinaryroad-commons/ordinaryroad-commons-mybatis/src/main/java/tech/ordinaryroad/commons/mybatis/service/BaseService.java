/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.ordinaryroad.commons.mybatis.service;

import cn.dev33.satoken.context.SaHolder;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import io.mybatis.mapper.example.ExampleWrapper;
import io.mybatis.mapper.fn.Fn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.constant.PathConstants;
import tech.ordinaryroad.commons.mybatis.mapper.IBaseMapper;
import tech.ordinaryroad.commons.mybatis.model.BaseDO;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * service 基类，不考虑逻辑删除
 *
 * @param <D> dao
 * @param <T> do
 * @author mjz
 * @date 2021/8/2
 */
@Slf4j
public abstract class BaseService<D extends IBaseMapper<T>, T extends BaseDO> {

    @Autowired
    protected D dao;
    @Autowired
    protected IFillMetaFieldService<T> fillMetaFieldService;

    /**
     * 新增记录
     *
     * @return 返回已创建的记录
     */
    public T create(T t) {
        return create(t, this::findById);
    }

    /**
     * @param function 更新成功后的回调
     */
    public <R> R create(T t, Function<T, R> function) {
        if (doCreate(t)) {
            return function.apply(t);
        }
        return null;
    }

    /**
     * @return 返回是否创建成功
     */
    private boolean doCreate(T t) {
        return createRecord(t, false);
    }

    /**
     * 新增记录
     *
     * @param t           实体
     * @param isSelective true不保存null值
     * @return boolean
     */
    public boolean createRecord(T t, boolean isSelective) {
        Assert.notNull(t, "新增记录不能为空");

        fillMetaFieldsWhenCreate(t);

        if (isSelective) {
            return dao.insertSelective(t) > 0;
        }
        return dao.insert(t) > 0;
    }

    /**
     * 新增记录，插入非空数据
     *
     * @return 返回已创建的记录
     */
    public T createSelective(T t) {
        return createSelective(t, this::findById);
    }

    /**
     * @param function 更新成功后的回调
     */
    public <R> R createSelective(T t, Function<T, R> function) {
        if (doCreateSelective(t)) {
            return function.apply(t);
        }
        return null;
    }

    /**
     * @return 返回是否创建成功
     */
    private boolean doCreateSelective(T t) {
        return createRecord(t, true);
    }

    /**
     * @return 返回已创建的记录
     */
    public T update(T t) {
        return update(t, t1 -> findById(t));
    }

    /**
     * 更新记录
     *
     * @param function 更新成功后的回调
     */
    public <R> R update(T t, Function<T, R> function) {
        if (doUpdate(t)) {
            return function.apply(t);
        }
        return null;
    }

    /**
     * @return 是否更新成功
     */
    private boolean doUpdate(T t) {
        return updateRecord(t, false);
    }

    /**
     * @return 返回已创建的记录
     */
    public T updateSelective(T t) {
        return updateSelective(t, t1 -> findById(t));
    }

    /**
     * 更新记录
     *
     * @param function 更新成功后的回调
     */
    public <R> R updateSelective(T t, Function<T, R> function) {
        if (doUpdateSelective(t)) {
            return function.apply(t);
        }
        return null;
    }

    /**
     * @return 是否更新成功
     */
    public boolean doUpdateSelective(T t) {
        return updateRecord(t, true);
    }

    public boolean updateRecord(T t, boolean isSelective) {
        Assert.notNull(t, "更新记录不能为空");
        Assert.notNull(t.getUuid(), "uuid不能为空");

        fillMetaFieldsWhenUpdate(t);

        if (isSelective) {
            return dao.updateByPrimaryKeySelective(t) > 0;
        }
        return dao.updateByPrimaryKey(t) > 0;
    }

    public T save(T t) {
        return save(t, null, t1 -> findById(t));
    }

    /**
     * 保存记录（有id的情况更新，无id的情况插入）
     *
     * @return 返回已创建的记录
     */
    public <R> R save(T t, Function<T, R> function) {
        return save(t, null, function);
    }

    public T save(T t, Predicate<T> predicate) {
        if (predicate.test(t)) {
            return create(t);
        }
        return update(t);
    }

    public <R> R save(T t, Predicate<T> predicate, Function<T, R> function) {
        if (predicate == null) {
            predicate = t1 -> t1.getUuid() == null;
        }

        if (predicate.test(t)) {
            return create(t, function);
        }
        return update(t, function);
    }

    /**
     * 保存记录（有id的情况更新，无id的情况插入）,排除非空记录
     *
     * @return 返回已创建的记录
     */
    public <R> R saveSelective(T t, Function<T, R> function) {
        return saveSelective(t, null, function);
    }

    public <R> R saveSelective(T t, Predicate<T> predicate, Function<T, R> function) {
        if (predicate == null) {
            predicate = t1 -> t1.getUuid() == null;
        }

        if (predicate.test(t)) {
            return createSelective(t, function);
        }
        return updateSelective(t, function);
    }

    private boolean doSave(T t, Predicate<T> predicate) {
        Assert.notNull(t, "保存记录不能为空");
        if (predicate.test(t)) {
            return doCreate(t);
        }
        return doUpdate(t);
    }

    /**
     * @return 是否保存成功
     */
    private boolean doSave(T t) {
        return doSave(t, t1 -> t1.getUuid() == null);
    }

    /**
     * 批量插入数据
     *
     * @param list 批量记录
     * @return int
     */
    public int insertList(List<T> list) {
        int result = 0;
        for (T t : list) {

            fillMetaFieldsWhenCreate(t);

            result += dao.insertSelective(t);
        }
        return result;
    }

    /**
     * 按条件查询id列表
     */
    public List<T> findIds(String[] ids) {
        return findIds(Arrays.asList(ids));
    }

    /**
     * 按条件查询id列表
     */
    public List<T> findIds(List<String> idList) {
        if (CollUtil.isEmpty(idList)) {
            return Collections.emptyList();
        }
        return dao.wrapper()
                .in(Fn.column(getEntityClass(), "uuid"), idList)
                .list();
    }

    /**
     * 根据id查询
     *
     * @return T
     */
    public T findById(T t) {
        return findById(t.getUuid());
    }

    public T findById(String id) {
        Assert.notNull(id, "id不能为空");
        return dao.selectByPrimaryKey(id).orElse(null);
    }

    /**
     * 查询所有记录
     */
    public List<T> findAll() {
        return dao.wrapper().list();
    }

    public boolean delete(String id) {
        return dao.deleteByPrimaryKey(id) != 0;
    }

    public boolean delete(T t) {
        return this.delete(t.getUuid());
    }

    /**
     * 通过idList 批量删除
     * 逻辑删除
     *
     * @return boolean
     */
    public boolean deleteByIdList(List<String> idList) {
        if (CollUtil.isEmpty(idList)) {
            return true;
        }
        return dao.wrapper()
                .in(Fn.column(getEntityClass(), "uuid"), idList)
                .delete() != 0;
    }

    /**
     * 创建时填充字段
     *
     * @param t BaseDO
     */
    private void fillMetaFieldsWhenCreate(T t) {
        t.setCreatedTime(LocalDateTime.now());

        // 填充uuid字段
        if (StrUtil.isBlank(t.getUuid())) {
            try {
                String uuid = fillMetaFieldService.generateUuid(t);
                log.debug("生成UUID：{}", uuid);
                t.setUuid(uuid);
            } catch (Exception e) {
                // 如果有报错需要处理
                log.error("TODO fillMetaFieldsWhenCreate uuid failed, " + e);
                fillMetaFieldService.generateUuidFailed(t, e);
            }
        }

        // 填充createBy字段，跳过免登接口
        String requestPath = SaHolder.getRequest().getRequestPath();
        if (PathConstants.NO_LOGIN_CREATE_PATHS.contains(requestPath)) {
            // ignore
            return;
        }
        try {
            t.setCreateBy(fillMetaFieldService.generateCreateBy(t));
        } catch (Exception e) {
            // 如果有报错需要处理
            log.error("TODO fillMetaFieldsWhenCreate createBy failed, " + requestPath, e);
            fillMetaFieldService.generateCreateByFailed(t, e);
        }
    }

    /**
     * 更新时填充字段
     *
     * @param t BaseDO
     */
    private void fillMetaFieldsWhenUpdate(T t) {
        t.setUpdateTime(LocalDateTime.now());

        // 填充updateBy字段，跳过免登接口
        String requestPath = SaHolder.getRequest().getRequestPath();
        if (PathConstants.NO_LOGIN_UPDATE_PATHS.contains(requestPath)) {
            // ignore
            return;
        }
        try {
            t.setUpdateBy(fillMetaFieldService.generateUpdateBy(t));
        } catch (Exception e) {
            // 如果有报错需要处理
            log.error("TODO fillMetaFieldsWhenUpdate updateBy failed, " + requestPath, e);
            fillMetaFieldService.generateUpdateByFailed(t, e);
        }
    }

    /**
     * findAll自动增加排序创建时间条件
     *
     * @param baseQueryRequest BaseQueryRequest
     * @param wrapper          ExampleWrapper
     * @return List<T>
     */
    public List<T> findAll(BaseQueryRequest baseQueryRequest, ExampleWrapper<T, String> wrapper) {
        String[] sortBy = baseQueryRequest.getSortBy();
        Boolean[] sortDesc = baseQueryRequest.getSortDesc();
        if (ArrayUtil.isNotEmpty(sortBy)) {
            for (int i = 0; i < sortBy.length; i++) {
                String columnName = sortBy[i];
                boolean isDesc = BooleanUtil.isTrue(ArrayUtil.get(sortDesc, i));
                if (isDesc) {
                    wrapper.orderByDesc(Fn.column(getEntityClass(), columnName));
                } else {
                    wrapper.orderByAsc(Fn.column(getEntityClass(), columnName));
                }
            }
        }

        LocalDateTime startTime = baseQueryRequest.getStartTime();
        if (Objects.nonNull(startTime)) {
            wrapper.ge(T::getCreatedTime, startTime);
        }
        LocalDateTime endTime = baseQueryRequest.getEndTime();
        if (Objects.nonNull(endTime)) {
            wrapper.le(T::getCreatedTime, endTime);
        }

        return wrapper.list();
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return (Class<T>) actualTypeArguments[1];
    }

    public D getDao() {
        return dao;
    }
}
