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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.dto.SysDictItemDTO;
import tech.ordinaryroad.upms.entity.SysDictDO;
import tech.ordinaryroad.upms.entity.SysDictItemDO;
import tech.ordinaryroad.upms.facade.ISysDictItemFacade;
import tech.ordinaryroad.upms.mapstruct.SysDictItemMapStruct;
import tech.ordinaryroad.upms.request.SysDictItemQueryRequest;
import tech.ordinaryroad.upms.request.SysDictItemSaveRequest;
import tech.ordinaryroad.upms.service.SysDictItemService;
import tech.ordinaryroad.upms.service.SysDictService;

import java.util.Collections;
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
public class SysDictItemFacadeImpl implements ISysDictItemFacade {

    private final SysDictService sysDictService;
    private final SysDictItemService sysDictItemService;
    private final SysDictItemMapStruct objMapStruct;

    @Override
    public Result<SysDictItemDTO> create(SysDictItemSaveRequest request) {
        // 唯一性校验
        String dictUuid = request.getDictUuid();
        SysDictDO dictById = sysDictService.findById(dictUuid);
        if (Objects.isNull(dictById)) {
            return Result.fail(StatusCode.DICT_NOT_EXIST);
        }

        if (sysDictItemService.findByDictUuidAndLabel(dictUuid, request.getLabel()).isPresent()) {
            return Result.fail(StatusCode.LABEL_ALREADY_EXIST);
        }
        if (sysDictItemService.findByDictUuidAndValue(dictUuid, request.getValue()).isPresent()) {
            return Result.fail(StatusCode.VALUE_ALREADY_EXIST);
        }

        SysDictItemDO sysDictItemDO = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysDictItemService.createSelective(sysDictItemDO)));
    }

    @Override
    public Result<Boolean> delete(BaseDeleteRequest request) {
        return Result.success(sysDictItemService.delete(request.getUuid()));
    }

    @Override
    public Result<SysDictItemDTO> update(SysDictItemSaveRequest request) {
        String uuid = request.getUuid();
        if (StrUtil.isBlank(uuid)) {
            return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
        }

        String dictUuid = request.getDictUuid();
        SysDictDO byDictUuid = sysDictService.findById(dictUuid);
        if (Objects.isNull(byDictUuid)) {
            return Result.fail(StatusCode.DICT_NOT_EXIST);
        }

        SysDictItemDO byId = sysDictItemService.findById(uuid);
        if (Objects.isNull(byId)) {
            return Result.fail(StatusCode.DATA_NOT_EXIST);
        }

        String newLabel = request.getLabel();
        String label = byId.getLabel();
        if (!Objects.equals(newLabel, label)) {
            if (sysDictItemService.findByDictUuidAndLabel(dictUuid, newLabel).isPresent()) {
                return Result.fail(StatusCode.LABEL_ALREADY_EXIST);
            }
        }
        String newValue = request.getValue();
        String value = byId.getValue();
        if (!Objects.equals(newValue, value)) {
            if (sysDictItemService.findByDictUuidAndValue(dictUuid, newValue).isPresent()) {
                return Result.fail(StatusCode.VALUE_ALREADY_EXIST);
            }
        }

        SysDictItemDO transfer = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(sysDictItemService.updateSelective(transfer)));
    }

    @Override
    public Result<SysDictItemDTO> findById(SysDictItemQueryRequest request) {
        SysDictItemDO sysDictDO = objMapStruct.transfer(request);
        SysDictItemDO byId = sysDictItemService.findById(sysDictDO);
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @Override
    public Result<SysDictItemDTO> detail(SysDictItemQueryRequest request) {
        final SysDictDO sysDictDO = new SysDictDO();
        sysDictDO.setUuid(request.getDictUuid());
        sysDictDO.setDictCode(request.getDictCode());
        sysDictDO.setDictName(request.getDictName());
        final Optional<SysDictDO> optionalDict = sysDictService.findByUniqueColumn(sysDictDO);
        if (!optionalDict.isPresent()) {
            return Result.fail(StatusCode.DICT_NOT_EXIST);
        }

        request.setDictUuid(optionalDict.get().getUuid());

        SysDictItemDO sysDictItemDO = objMapStruct.transfer(request);
        Optional<SysDictItemDO> byDictIdAndId = sysDictItemService.findByDictIdAndId(sysDictItemDO);
        return byDictIdAndId.map(dictItemDO -> Result.success(objMapStruct.transfer(dictItemDO))).orElseGet(() -> Result.fail(StatusCode.DATA_NOT_EXIST));
    }

    @Override
    public Result<List<SysDictItemDTO>> findAll(SysDictItemQueryRequest request) {
        SysDictItemDO sysDictDO = objMapStruct.transfer(request);

        List<SysDictItemDO> all = sysDictItemService.findAll(sysDictDO, request);
        List<SysDictItemDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<List<SysDictItemDTO>> findAllByIds(BaseQueryRequest request) {
        List<String> uuids = request.getUuids();
        if (CollUtil.isEmpty(uuids)) {
            return Result.success(Collections.emptyList());
        }
        List<SysDictItemDO> all = sysDictItemService.findIds(uuids);
        List<SysDictItemDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());
        return Result.success(list);
    }

    @Override
    public Result<List<SysDictItemDTO>> findAllByForeignColumn(SysDictItemQueryRequest request) {
        List<SysDictItemDO> all = Collections.emptyList();

        String dictUuid = request.getDictUuid();
        if (StrUtil.isBlank(dictUuid)) {
            final SysDictDO sysDictDO = new SysDictDO();
            sysDictDO.setUuid(request.getDictUuid());
            sysDictDO.setDictCode(request.getDictCode());
            sysDictDO.setDictName(request.getDictName());
            final Optional<SysDictDO> optionalSysDictDO = sysDictService.findByUniqueColumn(sysDictDO);
            if (optionalSysDictDO.isPresent()) {
                dictUuid = optionalSysDictDO.get().getUuid();
            }
        }

        if (StrUtil.isNotBlank(dictUuid)) {
            all = sysDictItemService.findAllByDictUuid(dictUuid);
        }

        List<SysDictItemDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<PageInfo<SysDictItemDTO>> list(SysDictItemQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysDictItemDO sysDictDO = objMapStruct.transfer(request);
        Page<SysDictItemDO> all = (Page<SysDictItemDO>) sysDictItemService.findAll(sysDictDO, request);

        PageInfo<SysDictItemDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }
}
