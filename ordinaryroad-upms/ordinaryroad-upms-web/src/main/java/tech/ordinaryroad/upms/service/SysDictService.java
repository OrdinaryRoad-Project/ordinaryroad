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
package tech.ordinaryroad.upms.service;

import cn.hutool.core.util.StrUtil;
import io.mybatis.mapper.example.ExampleWrapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysDictDAO;
import tech.ordinaryroad.upms.entity.SysDictDO;

import java.util.List;
import java.util.Optional;

/**
 * @author mjz
 * @date 2022/1/5
 */
@Service
public class SysDictService extends BaseService<SysDictDAO, SysDictDO> {

    public Optional<SysDictDO> findByUniqueColumn(@NotNull SysDictDO sysDictDO) {
        Optional<SysDictDO> optional = Optional.empty();

        final String uuid = sysDictDO.getUuid();
        final String dictCode = sysDictDO.getDictCode();
        final String dictName = sysDictDO.getDictName();
        if (StrUtil.isNotBlank(uuid)) {
            optional = Optional.ofNullable(super.findById(uuid));
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(dictCode)) {
            optional = this.findByDictCode(dictCode);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(dictName)) {
            optional = this.findByDictName(dictName);
        }

        return optional;
    }

    public Optional<SysDictDO> findByDictName(String dictName) {
        if (dictName == null) {
            return Optional.empty();
        }
        return dao.wrapper()
                .eq(SysDictDO::getDictName, dictName)
                .one();
    }

    public Optional<SysDictDO> findByDictCode(String dictCode) {
        if (dictCode == null) {
            return Optional.empty();
        }
        return dao.wrapper()
                .eq(SysDictDO::getDictCode, dictCode)
                .one();
    }

    public List<SysDictDO> findAll(SysDictDO sysDictDO, BaseQueryRequest baseQueryRequest) {
        ExampleWrapper<SysDictDO, String> wrapper = dao.wrapper();

        String dictName = sysDictDO.getDictName();
        if (StrUtil.isNotBlank(dictName)) {
            wrapper.like(SysDictDO::getDictName, "%" + dictName + "%");
        }
        String dictCode = sysDictDO.getDictCode();
        if (StrUtil.isNotBlank(dictCode)) {
            wrapper.like(SysDictDO::getDictCode, "%" + dictCode + "%");
        }
        String remark = sysDictDO.getRemark();
        if (StrUtil.isNotBlank(remark)) {
            wrapper.like(SysDictDO::getRemark, "%" + remark + "%");
        }

        return super.findAll(baseQueryRequest, wrapper);
    }

}
