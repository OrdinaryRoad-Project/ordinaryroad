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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.upms.dto.SysRequestPathDTO;
import tech.ordinaryroad.upms.mapstruct.SysPermissionMapStruct;

import java.util.Objects;

/**
 * 设置DTO字段服务类
 *
 * @author mjz
 * @date 2021/12/13
 */
@RequiredArgsConstructor
@Service
public class SysDtoService {

    private final SysPermissionService sysPermissionService;
    private final SysPermissionMapStruct sysPermissionMapStruct;

    public void setPermissionDTO(String permissionUuid, SysRequestPathDTO sysRequestPathDto) {
        if (Objects.isNull(sysRequestPathDto)) {
            return;
        }
        if (StrUtil.isNotBlank(permissionUuid)) {
            sysRequestPathDto.setPermission(sysPermissionMapStruct.transfer(sysPermissionService.findById(permissionUuid)));
        }
    }

}
