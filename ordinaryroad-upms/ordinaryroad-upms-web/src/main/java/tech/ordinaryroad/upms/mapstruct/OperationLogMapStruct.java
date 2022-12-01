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
package tech.ordinaryroad.upms.mapstruct;

import cn.hutool.core.util.EnumUtil;
import org.mapstruct.Mapper;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.log.entity.OperationLogDO;
import tech.ordinaryroad.upms.dto.OperationLogDTO;
import tech.ordinaryroad.upms.dto.OperationLogTypeDTO;
import tech.ordinaryroad.upms.request.OperationLogQueryRequest;

import java.util.Objects;

/**
 * @author mjz
 * @date 2022/11/30
 */
@Mapper(componentModel = "spring")
public interface OperationLogMapStruct {

    OperationLogDTO transfer(OperationLogDO operationLogDO);

    OperationLogDO transfer(OperationLogQueryRequest request);

    default OperationLogTypeDTO map(Integer type) {
        StatusCode statusCode = EnumUtil.getBy(StatusCode::getCode, type);
        if (Objects.isNull(statusCode)) {
            return null;
        }

        OperationLogTypeDTO operationLogTypeDTO = new OperationLogTypeDTO();
        
        operationLogTypeDTO.setDescription(statusCode.name());
        operationLogTypeDTO.setCode(statusCode.getCode());

        return operationLogTypeDTO;
    }
}
