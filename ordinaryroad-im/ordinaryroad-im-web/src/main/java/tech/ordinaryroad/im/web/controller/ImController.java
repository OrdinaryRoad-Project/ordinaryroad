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

package tech.ordinaryroad.im.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.im.api.IImApi;
import tech.ordinaryroad.im.constant.MimcConstant;
import tech.ordinaryroad.im.dto.ImInfoDTO;
import tech.ordinaryroad.upms.api.ISysDictItemApi;
import tech.ordinaryroad.upms.dto.SysDictItemDTO;
import tech.ordinaryroad.upms.request.SysDictItemQueryRequest;

import java.util.List;

/**
 * Controller
 *
 * @author mjz
 * @date 2022/3/14
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class ImController implements IImApi {

    private final ISysDictItemApi sysDictItemApi;

    @Override
    public Result<ImInfoDTO> init() {
        final ImInfoDTO imInfoDTO = new ImInfoDTO();

        // 获取聊天拍一拍字典
        final SysDictItemQueryRequest sysDictItemQueryRequest = new SysDictItemQueryRequest();
        sysDictItemQueryRequest.setDictCode(MimcConstant.DICT_CODE_IM_MSG_DOUBLE_CLICK_AVATAR_PAYLOAD);
        final Result<List<SysDictItemDTO>> dictItemsResult = sysDictItemApi.findAllByForeignColumn(sysDictItemQueryRequest);
        if (!dictItemsResult.getSuccess()) {
            return Result.fail(dictItemsResult.getMsg());
        }
        imInfoDTO.setDoubleClickAvatarPayloadDictItems(dictItemsResult.getData());

        return Result.success(imInfoDTO);
    }
}
