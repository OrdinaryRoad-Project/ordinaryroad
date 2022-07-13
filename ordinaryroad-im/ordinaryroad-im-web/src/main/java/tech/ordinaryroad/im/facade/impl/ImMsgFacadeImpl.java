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

package tech.ordinaryroad.im.facade.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.im.dto.ImMsgDTO;
import tech.ordinaryroad.im.entity.ImMsgDO;
import tech.ordinaryroad.im.facade.IImMsgFacade;
import tech.ordinaryroad.im.mapstruct.ImMsgMapStruct;
import tech.ordinaryroad.im.request.*;
import tech.ordinaryroad.im.service.ImMsgService;

import java.util.Objects;
import java.util.Optional;

/**
 * @author mjz
 * @date 2022/1/21
 */
@RequiredArgsConstructor
@Component
public class ImMsgFacadeImpl implements IImMsgFacade {

    private final ImMsgService imMsgService;
    private final ImMsgMapStruct objMapStruct;

    @Override
    public Result<ImMsgDTO> create(ImMsgSaveRequest request) {
        ImMsgDO imMsgDO = objMapStruct.transfer(request);
        imMsgDO.setRead(false);
        return Result.success(objMapStruct.transfer(imMsgService.createSelective(imMsgDO)));
    }

    @Override
    public Result<Boolean> delete(BaseDeleteRequest request) {
        return Result.success(imMsgService.delete(request.getUuid()));
    }

    @Override
    public Result<Boolean> read(ImMsgReadRequest request) {
        Optional<ImMsgDO> byMsgId = imMsgService.findByMsgId(request.getMsgId());
        if (!byMsgId.isPresent()) {
            return Result.fail(StatusCode.DATA_NOT_EXIST);
        }
        ImMsgDO imMsgDO = byMsgId.get();

        Boolean read = imMsgDO.getRead();
        if (BooleanUtil.isTrue(read)) {
            return Result.success(false);
        }

        ImMsgDO newImMsgDO = new ImMsgDO();
        newImMsgDO.setUuid(imMsgDO.getUuid());
        newImMsgDO.setRead(Boolean.TRUE);
        newImMsgDO.setUpdateBy(request.getUid());
        if (imMsgService.doUpdateSelective(newImMsgDO)) {
            return Result.success(true);
        } else {
            return Result.fail();
        }
    }

    @Override
    public Result<Boolean> recall(ImMsgRecallRequest request) {
        Optional<ImMsgDO> byMsgId = imMsgService.findByMsgId(request.getMsgId());
        if (!byMsgId.isPresent()) {
            return Result.fail(StatusCode.DATA_NOT_EXIST);
        }
        ImMsgDO imMsgDO = byMsgId.get();

        Boolean recalled = imMsgDO.getRecalled();
        if (BooleanUtil.isTrue(recalled)) {
            return Result.success(false);
        }

        ImMsgDO newImMsgDO = new ImMsgDO();
        newImMsgDO.setUuid(imMsgDO.getUuid());
        newImMsgDO.setRecalled(Boolean.TRUE);
        newImMsgDO.setUpdateBy(request.getUid());
        if (imMsgService.doUpdateSelective(newImMsgDO)) {
            return Result.success(true);
        } else {
            return Result.fail();
        }
    }

    @Override
    public Result<ImMsgDTO> findByUniqueColumn(ImMsgQueryRequest request) {
        Optional<ImMsgDO> optional = Optional.empty();
        String msgId = request.getMsgId();
        String uuid = request.getUuid();
        if (StrUtil.isNotBlank(msgId)) {
            optional = imMsgService.findByMsgId(msgId);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(uuid)) {
            optional = Optional.ofNullable(imMsgService.findById(uuid));
        }

        return optional.map(data -> Result.success(objMapStruct.transfer(data))).orElseGet(Result::fail);
    }

    @Override
    public Result<ImMsgDTO> findById(ImMsgQueryRequest request) {
        ImMsgDO byId = imMsgService.findById(request.getUuid());
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @Override
    public Result<PageInfo<ImMsgDTO>> history(ImMsgHistoryRequest request) {
        String orNumber = StpUtil.getLoginIdAsString();
        String chatPartnerOrNumber = request.getToOrNumber();

        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        ImMsgDO imMsgDO = objMapStruct.transfer(request);
        Page<ImMsgDO> all = (Page<ImMsgDO>) imMsgService.history(imMsgDO, orNumber, chatPartnerOrNumber);

        PageInfo<ImMsgDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }

    @Override
    public Result<PageInfo<ImMsgDTO>> list(ImMsgQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        ImMsgDO imMsgDO = objMapStruct.transfer(request);
        Page<ImMsgDO> all = (Page<ImMsgDO>) imMsgService.findAll(imMsgDO, request);

        PageInfo<ImMsgDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }
}
