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

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.upms.dao.SysDictDAO;
import tech.ordinaryroad.upms.entity.SysDictDO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;
import java.util.Optional;

/**
 * @author mjz
 * @date 2022/1/5
 */
@Service
public class SysDictService extends BaseService<SysDictDAO, SysDictDO> {

    public Optional<SysDictDO> findByDictName(String dictName) {
        Example example = Example.builder(SysDictDO.class)
                .where(Sqls.custom().andEqualTo("dictName", dictName))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public Optional<SysDictDO> findByDictCode(String dictCode) {
        Example example = Example.builder(SysDictDO.class)
                .where(Sqls.custom().andEqualTo("dictCode", dictCode))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<SysDictDO> findAll(SysDictDO sysDictDO, String[] orderBy, String[] orderByDesc) {
        Sqls sqls = Sqls.custom();

        String dictName = sysDictDO.getDictName();
        if (StrUtil.isNotBlank(dictName)) {
            sqls.andLike("dictName", "%" + dictName + "%");
        }
        String dictCode = sysDictDO.getDictCode();
        if (StrUtil.isNotBlank(dictCode)) {
            sqls.andLike("dictCode", "%" + dictCode + "%");
        }
        String remark = sysDictDO.getRemark();
        if (StrUtil.isNotBlank(remark)) {
            sqls.andLike("remark", "%" + remark + "%");
        }

        Example.Builder exampleBuilder = Example.builder(SysDictDO.class).where(sqls);

        if (ArrayUtil.isNotEmpty(orderBy)) {
            exampleBuilder.orderBy(orderBy);
        }
        if (ArrayUtil.isNotEmpty(orderByDesc)) {
            exampleBuilder.orderByDesc(orderByDesc);
        }

        return super.dao.selectByExample(exampleBuilder.build());
    }

}
