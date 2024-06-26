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
package tech.ordinaryroad.upms.web.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.log.entity.OperationLogDO;
import tech.ordinaryroad.commons.log.service.OperationLogService;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.dto.OperationLogDTO;
import tech.ordinaryroad.upms.dto.OperationLogRequestPathDTO;
import tech.ordinaryroad.upms.dto.OperationLogTypeDTO;
import tech.ordinaryroad.upms.entity.SysRequestPathDO;
import tech.ordinaryroad.upms.mapstruct.OperationLogMapStruct;
import tech.ordinaryroad.upms.request.OperationLogQueryRequest;
import tech.ordinaryroad.upms.service.SysRequestPathService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 操作日志Controller
 *
 * @author mjz
 * @date 2022/11/30
 */
@RequiredArgsConstructor
@RestController
public class OperationLogController {

    private final OperationLogService operationLogService;

    private final OperationLogMapStruct objMapStruct;
    private final SysRequestPathService sysRequestPathService;

    @PostMapping("/operation_log/list")
    public Result<PageInfo<OperationLogDTO>> list(@RequestBody OperationLogQueryRequest request) {
        OperationLogDO operationLogDO = objMapStruct.transfer(request);

        Page<OperationLogDO> all = PageHelper.offsetPage(request.getOffset(), request.getLimit()).doSelectPage(() -> {
            operationLogService.findAll(operationLogDO, request);
        });

        PageInfo<OperationLogDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }

    @DeleteMapping(value = "/operation_log/delete")
    public Result<Boolean> delete(@RequestParam String id) {
        return Result.success(operationLogService.delete(id));
    }

    @GetMapping(value = "/operation_log/all/types")
    public Result<List<OperationLogTypeDTO>> findAllTypes() {
        AtomicReference<OperationLogTypeDTO> operationLogTypeDTO = new AtomicReference<>(new OperationLogTypeDTO());
        return Result.success(Arrays.stream(StatusCode.values()).map(statusCode -> {
                    operationLogTypeDTO.set(operationLogTypeDTO.get().clone());

                    operationLogTypeDTO.getAndUpdate(t -> {
                        t.setCode(statusCode.getCode());
                        t.setDescription(statusCode.name());
                        return t;
                    });

                    return operationLogTypeDTO.get();
                }
        ).collect(Collectors.toList()));
    }

    public static void main(String[] args) {
        String path = "/1121";
        int secondIndex = StrUtil.length(path);
        if (StrUtil.indexOf(path, '/', 1) != -1) {
            secondIndex = StrUtil.indexOf(path, '/', 1);
        }
        System.out.println(StrUtil.subSuf(path, secondIndex));
    }

    @GetMapping(value = "/operation_log/all/paths")
    Result<List<OperationLogRequestPathDTO>> findAllPaths() {
        List<OperationLogRequestPathDTO> operationLogRequestPathDTOList = new ArrayList<>();

        List<SysRequestPathDO> allRequestPathList = sysRequestPathService.findAll();
        if (CollUtil.isNotEmpty(allRequestPathList)) {
            OperationLogRequestPathDTO operationLogRequestPathDTO = new OperationLogRequestPathDTO();
            for (SysRequestPathDO sysRequestPathDO : allRequestPathList) {
                // 去除网关的前缀
                String path = sysRequestPathDO.getPath();
                int secondIndex = StrUtil.length(path);
                if (StrUtil.indexOf(path, '/', 1) != -1) {
                    secondIndex = StrUtil.indexOf(path, '/', 1);
                }
                sysRequestPathDO.setPath(StrUtil.subSuf(path, secondIndex));

                operationLogRequestPathDTO = operationLogRequestPathDTO.clone();
                operationLogRequestPathDTOList.add(objMapStruct.transfer(sysRequestPathDO));
            }
        }

        return Result.success(operationLogRequestPathDTOList);
    }

    @GetMapping(value = "/operation_log/all/status")
    public Result<List<String>> findAllStatus() {
        return Result.success(operationLogService.getDao().selectDistinctStatus());
    }

}
