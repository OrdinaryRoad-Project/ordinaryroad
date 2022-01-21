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
package tech.ordinaryroad.upms.facade.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.dto.SysDictDTO;
import tech.ordinaryroad.upms.entity.SysDictDO;
import tech.ordinaryroad.upms.facade.ISysDictFacade;
import tech.ordinaryroad.upms.mapstruct.SysDictMapStruct;
import tech.ordinaryroad.upms.request.SysDictQueryRequest;
import tech.ordinaryroad.upms.request.SysDictSaveRequest;
import tech.ordinaryroad.upms.service.SysDictService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2022/1/5
 */
@RequiredArgsConstructor
@Component
public class SysDictFacadeImpl implements ISysDictFacade {

    private final SysDictService sysDictService;
    private final SysDictMapStruct objMapStruct;

    @Override
    public Result<SysDictDTO> create(SysDictSaveRequest request) {
        // 唯一性校验
        String dictName = request.getDictName();
        Optional<SysDictDO> byDictName = sysDictService.findByDictName(dictName);
        if (byDictName.isPresent()) {
            return Result.fail(StatusCode.NAME_ALREADY_EXIST);
        }
        String dictCode = request.getDictCode();
        Optional<SysDictDO> byDictCode = sysDictService.findByDictCode(dictCode);
        if (byDictCode.isPresent()) {
            return Result.fail(StatusCode.CODE_ALREADY_EXIST);
        }

        SysDictDO sysDictDO = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysDictService.createSelective(sysDictDO)));
    }

    @Override
    public Result<Boolean> delete(BaseDeleteRequest request) {
        return Result.success(sysDictService.delete(request.getUuid()));
    }

    @Override
    public Result<SysDictDTO> update(SysDictSaveRequest request) {
        String uuid = request.getUuid();
        if (StrUtil.isBlank(uuid)) {
            return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
        }

        SysDictDO byId = sysDictService.findById(uuid);
        if (Objects.isNull(byId)) {
            return Result.fail(StatusCode.DATA_NOT_EXIST);
        }

        String newDictName = request.getDictName();
        String dictName = byId.getDictName();
        if (!Objects.equals(newDictName, dictName)) {
            if (sysDictService.findByDictName(newDictName).isPresent()) {
                return Result.fail(StatusCode.NAME_ALREADY_EXIST);
            }
        }
        String newDictCode = request.getDictCode();
        String dictCode = byId.getDictCode();
        if (!Objects.equals(newDictCode, dictCode)) {
            if (sysDictService.findByDictCode(newDictCode).isPresent()) {
                return Result.fail(StatusCode.CODE_ALREADY_EXIST);
            }
        }

        SysDictDO transfer = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysDictService.updateSelective(transfer)));
    }

    @Override
    public Result<SysDictDTO> findById(SysDictQueryRequest request) {
        SysDictDO sysDictDO = objMapStruct.transfer(request);
        SysDictDO byId = sysDictService.findById(sysDictDO);
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @Override
    public Result<SysDictDTO> findByUniqueColumn(SysDictQueryRequest request) {
        Optional<SysDictDO> optional = Optional.empty();
        String dictCode = request.getDictCode();
        String dictName = request.getDictName();
        if (StrUtil.isNotBlank(dictCode)) {
            optional = sysDictService.findByDictCode(dictCode);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(dictName)) {
            optional = sysDictService.findByDictName(dictName);
        }
        return optional.map(data -> Result.success(objMapStruct.transfer(data))).orElseGet(Result::fail);
    }

    @Override
    public Result<List<SysDictDTO>> findAll(SysDictQueryRequest request) {
        SysDictDO sysDictDO = objMapStruct.transfer(request);

        List<SysDictDO> all = sysDictService.findAll(sysDictDO, request.getOrderBy(), request.getOrderByDesc());
        List<SysDictDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<PageInfo<SysDictDTO>> list(SysDictQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysDictDO sysDictDO = objMapStruct.transfer(request);
        Page<SysDictDO> all = (Page<SysDictDO>) sysDictService.findAll(sysDictDO, request.getOrderBy(), request.getOrderByDesc());

        PageInfo<SysDictDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }
}
