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

package tech.ordinaryroad.im.mapstruct;

import org.mapstruct.Mapper;
import tech.ordinaryroad.im.dto.ImMsgDTO;
import tech.ordinaryroad.im.entity.ImMsgDO;
import tech.ordinaryroad.im.request.ImMsgHistoryRequest;
import tech.ordinaryroad.im.request.ImMsgQueryRequest;
import tech.ordinaryroad.im.request.ImMsgSaveRequest;

/**
 * @author mjz
 * @date 2022/1/21
 */
@Mapper(componentModel = "spring")
public interface ImMsgMapStruct {

    ImMsgDO transfer(ImMsgSaveRequest request);

    ImMsgDTO transfer(ImMsgDO imMsgDO);

    ImMsgDO transfer(ImMsgQueryRequest request);

    ImMsgDO transfer(ImMsgHistoryRequest request);

    ImMsgSaveRequest transfer(ImMsgDTO imMsgDTO);
}
