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

package tech.ordinaryroad.commons.mybatis.quarkus.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import tech.ordinaryroad.commons.core.quarkus.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.mybatis.quarkus.mapper.IBaseMapper;
import tech.ordinaryroad.commons.mybatis.quarkus.model.BaseDO;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * service 基类，不考虑逻辑删除
 *
 * @param <D> dao
 * @param <T> do
 * @author mjz
 * @date 2022/6/12
 */
@Slf4j
public class BaseService<D extends IBaseMapper<T>, T extends BaseDO> {

    public D getDao() {
        return this.dao;
    }

    @Inject
    protected D dao;
    @Inject
    protected IFillMetaFieldService fillMetaFieldService;

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
        Assert.notNull(t, "新增记录不能为空");

        fillMetaFieldsWhenCreate(t);

        return dao.insert(t) > 0;
    }

    /**
     * 更新记录
     *
     * @param t             要设置的新值，清空String类型需传入空字符串
     * @param updateWrapper Wrapper，用于构建Where条件
     * @return 返回修改的行数
     */
    public int update(T t, Wrapper<T> updateWrapper) {
        Assert.notNull(t, "更新记录不能为空");

        fillMetaFieldsWhenUpdate(t);

        return dao.update(t, updateWrapper);
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
        Assert.notNull(t, "更新记录不能为空");
        Assert.notNull(t.getUuid(), "uuid不能为空");

        fillMetaFieldsWhenUpdate(t);

        return dao.updateById(t) > 0;
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

            result += dao.insert(t);
        }
        return result;
    }

    /**
     * 按条件查询id列表
     */
    public List<T> findIds(Class<T> tClass, String[] ids) {
        return findIds(tClass, Arrays.asList(ids));
    }

    /**
     * 按条件查询id列表
     */
    public List<T> findIds(Class<T> tClass, List<String> idList) {
        if (CollUtil.isEmpty(idList)) {
            return Collections.emptyList();
        }
        return dao.selectBatchIds(idList);
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
        return dao.selectById(id);
    }

    /**
     * 查询所有记录
     */
    public List<T> findAll() {
        return dao.selectList(null);
    }

    public boolean delete(String id) {
        return dao.deleteById(id) != 0;
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
        return dao.deleteBatchIds(idList) != 0;
    }

    /**
     * 创建时填充字段
     *
     * @param t BaseDO
     */
    private void fillMetaFieldsWhenCreate(T t) {
        if (StrUtil.isBlank(t.getUuid())) {
            String uuid = IdUtil.fastSimpleUUID();
            log.debug("生成UUID：{}", uuid);
            t.setUuid(uuid);
        }
        try {
            // 填充createBy字段
            t.setCreateBy(fillMetaFieldService.generateCreateBy());
        } catch (Exception e) {
            // 如果有报错需要处理
            log.error("TODO fillMetaFieldsWhenCreate createBy failed, " + e);
        }
    }

    /**
     * 更新时填充字段
     *
     * @param t BaseDO
     */
    private void fillMetaFieldsWhenUpdate(T t) {
        t.setUpdateTime(LocalDateTime.now());
        try {
            // 填充updateBy字段
            t.setUpdateBy(fillMetaFieldService.generateUpdateBy());
        } catch (Exception e) {
            // 如果有报错需要处理
            log.error("TODO fillMetaFieldsWhenUpdate updateBy failed, " + e);
        }
    }

    /**
     * findAll自动增加排序创建时间条件
     *
     * @param baseQueryRequest BaseQueryRequest
     * @param wrapper          QueryChainWrapper<T>
     * @return List<T>
     */
    public List<T> findAll(BaseQueryRequest baseQueryRequest, QueryChainWrapper<T> wrapper) {
        updateQueryChainWrapper(baseQueryRequest, wrapper);
        return wrapper.list();
    }

    /**
     * 分页查询
     *
     * @param baseQueryRequest BaseQueryRequest
     * @param wrapper          QueryChainWrapper<T>
     * @return Page<T>
     */
    public Page<T> page(BaseQueryRequest baseQueryRequest, QueryChainWrapper<T> wrapper) {
        updateQueryChainWrapper(baseQueryRequest, wrapper);

        Long page = baseQueryRequest.getPage();
        Long size = baseQueryRequest.getSize();

        return wrapper.page(Page.of(page, size));
    }

    private void updateQueryChainWrapper(BaseQueryRequest baseQueryRequest, QueryChainWrapper<T> wrapper) {
        String createBy = baseQueryRequest.getCreateBy();
        wrapper.eq(StrUtil.isNotBlank(createBy), "create_by", createBy);

        String updateBy = baseQueryRequest.getUpdateBy();
        wrapper.eq(StrUtil.isNotBlank(updateBy), "update_by", updateBy);

        List<String> sortBy = baseQueryRequest.getSortBy();
        List<Boolean> sortDesc = baseQueryRequest.getSortDesc();
        if (ArrayUtil.isNotEmpty(sortBy)) {
            for (int i = 0; i < sortBy.size(); i++) {
                String columnName = StrUtil.toUnderlineCase(sortBy.get(i));
                boolean isDesc = BooleanUtil.isTrue(CollUtil.get(sortDesc, i));
                wrapper.orderBy(Boolean.TRUE, !isDesc, columnName);
            }
        }

        Long startTime = baseQueryRequest.getStartTime();
        if (Objects.nonNull(startTime)) {
            LocalDateTime localDateTime = LocalDateTimeUtil.of(startTime);
            wrapper.ge("created_time", localDateTime);
        }

        Long endTime = baseQueryRequest.getEndTime();
        if (Objects.nonNull(endTime)) {
            LocalDateTime localDateTime = LocalDateTimeUtil.of(endTime);
            wrapper.le("created_time", localDateTime);
        }
    }

}
