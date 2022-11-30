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
package tech.ordinaryroad.commons.log.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.log.dao.OperationLogDAO;
import tech.ordinaryroad.commons.log.entity.OperationLogDO;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;
import java.util.Objects;

/**
 * OR操作日志服务类
 *
 * @author mjz
 * @date 2022/11/28
 */
@Service
public class OperationLogService extends BaseService<OperationLogDAO, OperationLogDO> {

    public List<OperationLogDO> findAll(OperationLogDO operationLogDO, BaseQueryRequest baseQueryRequest) {
        WeekendSqls<OperationLogDO> sqls = WeekendSqls.custom();

        Integer type = operationLogDO.getType();
        if (Objects.nonNull(type)) {
            sqls.andEqualTo(OperationLogDO::getType, type);
        }
        String method = operationLogDO.getMethod();
        if (StrUtil.isNotBlank(method)) {
            sqls.andEqualTo(OperationLogDO::getMethod, method);
        }
        String status = operationLogDO.getStatus();
        if (StrUtil.isNotBlank(status)) {
            sqls.andEqualTo(OperationLogDO::getStatus, status);
        }

        Example.Builder exampleBuilder = Example.builder(OperationLogDO.class).where(sqls);

        return super.findAll(baseQueryRequest, sqls, exampleBuilder);
    }

}
