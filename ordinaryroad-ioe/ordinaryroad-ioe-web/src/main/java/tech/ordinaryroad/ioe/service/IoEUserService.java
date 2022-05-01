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
package tech.ordinaryroad.ioe.service;

import cn.hutool.core.util.StrUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.ioe.dao.IoEUserDAO;
import tech.ordinaryroad.ioe.entity.IoEUserDO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.Optional;

/**
 * @author mjz
 * @date 2022/3/25
 */
@Service
public class IoEUserService extends BaseService<IoEUserDAO, IoEUserDO> {

    public Optional<IoEUserDO> findByUniqueColumn(@NotNull IoEUserDO sysDictDO) {
        Optional<IoEUserDO> optional = Optional.empty();

        final String uuid = sysDictDO.getUuid();
        final String orNumber = sysDictDO.getOrNumber();
        final String openid = sysDictDO.getOpenid();
        final String customerId = sysDictDO.getCustomerId();
        final String userId = sysDictDO.getUserId();
        if (StrUtil.isNotBlank(uuid)) {
            optional = Optional.ofNullable(super.findById(uuid));
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(orNumber)) {
            optional = this.findByOrNumber(orNumber);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(openid)) {
            optional = this.findByOpenid(openid);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(customerId)) {
            optional = this.findByCustomerId(customerId);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(userId)) {
            optional = this.findByUserId(userId);
        }

        return optional;
    }

    public Optional<IoEUserDO> findByOrNumber(String orNumber) {
        Optional<IoEUserDO> optional = Optional.empty();
        if (StrUtil.isNotBlank(orNumber)) {
            WeekendSqls<IoEUserDO> sql = WeekendSqls.custom();

            final Example build = Example.builder(IoEUserDO.class)
                    .where(sql.andEqualTo(IoEUserDO::getOrNumber, orNumber))
                    .build();
            optional = Optional.ofNullable(super.dao.selectOneByExample(build));
        }
        return optional;
    }

    public Optional<IoEUserDO> findByOpenid(String openid) {
        Optional<IoEUserDO> optional = Optional.empty();
        if (StrUtil.isNotBlank(openid)) {
            WeekendSqls<IoEUserDO> sql = WeekendSqls.custom();

            final Example build = Example.builder(IoEUserDO.class)
                    .where(sql.andEqualTo(IoEUserDO::getOpenid, openid))
                    .build();
            optional = Optional.ofNullable(super.dao.selectOneByExample(build));
        }
        return optional;
    }

    public Optional<IoEUserDO> findByCustomerId(String customerId) {
        Optional<IoEUserDO> optional = Optional.empty();
        if (StrUtil.isNotBlank(customerId)) {
            WeekendSqls<IoEUserDO> sql = WeekendSqls.custom();

            final Example build = Example.builder(IoEUserDO.class)
                    .where(sql.andEqualTo(IoEUserDO::getCustomerId, customerId))
                    .build();
            optional = Optional.ofNullable(super.dao.selectOneByExample(build));
        }
        return optional;
    }

    public Optional<IoEUserDO> findByUserId(String userId) {
        Optional<IoEUserDO> optional = Optional.empty();
        if (StrUtil.isNotBlank(userId)) {
            WeekendSqls<IoEUserDO> sql = WeekendSqls.custom();

            final Example build = Example.builder(IoEUserDO.class)
                    .where(sql.andEqualTo(IoEUserDO::getUserId, userId))
                    .build();
            optional = Optional.ofNullable(super.dao.selectOneByExample(build));
        }
        return optional;
    }

}
