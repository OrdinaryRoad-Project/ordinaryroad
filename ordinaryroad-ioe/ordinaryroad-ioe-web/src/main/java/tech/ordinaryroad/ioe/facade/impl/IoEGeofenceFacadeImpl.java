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
package tech.ordinaryroad.ioe.facade.impl;

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
import tech.ordinaryroad.ioe.api.dto.IoEGeofenceDTO;
import tech.ordinaryroad.ioe.api.request.IoEGeofenceQueryRequest;
import tech.ordinaryroad.ioe.api.request.IoEGeofenceSaveRequest;
import tech.ordinaryroad.ioe.entity.IoEGeofenceDO;
import tech.ordinaryroad.ioe.facade.IIoEGeofenceFacade;
import tech.ordinaryroad.ioe.mapstruct.IoEGeofenceMapStruct;
import tech.ordinaryroad.ioe.service.IoEGeofenceService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.Objects;

/**
 * @author mjz
 * @date 2022/4/5
 */
@RequiredArgsConstructor
@Component
public class IoEGeofenceFacadeImpl implements IIoEGeofenceFacade {

    private final IoEGeofenceService ioEGeofenceService;
    private final IoEGeofenceMapStruct objMapStruct;

    @Override
    public Result<IoEGeofenceDTO> create(IoEGeofenceSaveRequest request) {
        IoEGeofenceDO ioEGeofenceDO = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(ioEGeofenceService.createSelective(ioEGeofenceDO)));
    }

    @Override
    public Result<Boolean> delete(BaseDeleteRequest request) {
        return Result.success(ioEGeofenceService.delete(request.getUuid()));
    }

    @Override
    public Result<IoEGeofenceDTO> update(IoEGeofenceSaveRequest request) {
        String uuid = request.getUuid();
        if (StrUtil.isBlank(uuid)) {
            return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
        }

        IoEGeofenceDO byId = ioEGeofenceService.findById(uuid);
        if (Objects.isNull(byId)) {
            return Result.fail(StatusCode.DATA_NOT_EXIST);
        }

        IoEGeofenceDO transfer = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(ioEGeofenceService.updateSelective(transfer)));
    }

    @Override
    public Result<PageInfo<IoEGeofenceDTO>> list(IoEGeofenceQueryRequest request) {
        WeekendSqls<IoEGeofenceDO> sqls = WeekendSqls.custom();

        final String deviceId = request.getDeviceId();
        sqls.andEqualTo(IoEGeofenceDO::getDeviceId, deviceId);

        Example.Builder exampleBuilder = Example.builder(IoEGeofenceDO.class).where(sqls);

        PageHelper.offsetPage(request.getOffset(), request.getLimit());
        final Page<IoEGeofenceDO> all = (Page<IoEGeofenceDO>) ioEGeofenceService.findAll(request, sqls, exampleBuilder);
        PageInfo<IoEGeofenceDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }

}
