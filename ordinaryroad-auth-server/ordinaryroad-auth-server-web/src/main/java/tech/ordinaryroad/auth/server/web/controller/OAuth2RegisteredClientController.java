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
package tech.ordinaryroad.auth.server.web.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.auth.server.api.IOAuth2RegisteredClientApi;
import tech.ordinaryroad.auth.server.dto.OAuth2RegisteredClientDTO;
import tech.ordinaryroad.auth.server.entity.OAuth2RegisteredClientDO;
import tech.ordinaryroad.auth.server.mapstruct.OAuth2RegisteredClientMapStruct;
import tech.ordinaryroad.auth.server.request.OAuth2RegisteredClientQueryRequest;
import tech.ordinaryroad.auth.server.request.OAuth2RegisteredClientSaveRequest;
import tech.ordinaryroad.auth.server.service.OAuth2RegisteredClientService;
import tech.ordinaryroad.commons.base.cons.StatusCode;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2022/1/14
 */
@RequiredArgsConstructor
@RestController
public class OAuth2RegisteredClientController implements IOAuth2RegisteredClientApi {

    private final OAuth2RegisteredClientService oAuth2RegisteredClientService;
    private final OAuth2RegisteredClientMapStruct objMapStruct;

    @Override
    public Result<OAuth2RegisteredClientDTO> create(@Validated @RequestBody OAuth2RegisteredClientSaveRequest request) {

        // 唯一性校验
        String clientId = request.getClientId();
        Optional<OAuth2RegisteredClientDO> byClientId = oAuth2RegisteredClientService.findByClientId(clientId);
        if (byClientId.isPresent()) {
            return Result.fail(StatusCode.CLIENT_ID_ALREADY_EXIST);
        }
        String clientName = request.getClientName();
        Optional<OAuth2RegisteredClientDO> byClientName = oAuth2RegisteredClientService.findByClientName(clientName);
        if (byClientName.isPresent()) {
            return Result.fail(StatusCode.CLIENT_NAME_ALREADY_EXIST);
        }

        OAuth2RegisteredClientDO oAuth2RegisteredClientDO = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(oAuth2RegisteredClientService.createSelective(oAuth2RegisteredClientDO)));
    }

    @Override
    public Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request) {
        return Result.success(oAuth2RegisteredClientService.delete(request.getUuid()));
    }

    @Override
    public Result<OAuth2RegisteredClientDTO> update(@Validated @RequestBody OAuth2RegisteredClientSaveRequest request) {
        String uuid = request.getUuid();
        if (StrUtil.isBlank(uuid)) {
            return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
        }

        OAuth2RegisteredClientDO byId = oAuth2RegisteredClientService.findById(uuid);
        if (Objects.isNull(byId)) {
            return Result.fail(StatusCode.DATA_NOT_EXIST);
        }

        String newClientName = request.getClientName();
        String clientName = byId.getClientName();
        if (!Objects.equals(newClientName, clientName)) {
            if (oAuth2RegisteredClientService.findByClientName(newClientName).isPresent()) {
                return Result.fail(StatusCode.CLIENT_NAME_ALREADY_EXIST);
            }
        }

        OAuth2RegisteredClientDO transfer = objMapStruct.transfer(request);

        // 不允许更新clientId
        transfer.setClientId(null);

        return Result.success(objMapStruct.transfer(oAuth2RegisteredClientService.updateSelective(transfer)));
    }

    @Override
    public Result<OAuth2RegisteredClientDTO> findById(@RequestBody OAuth2RegisteredClientQueryRequest request) {
        OAuth2RegisteredClientDO byId = oAuth2RegisteredClientService.findById(request.getUuid());
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @Override
    public Result<OAuth2RegisteredClientDTO> findByUniqueColumn(@RequestBody OAuth2RegisteredClientQueryRequest request) {
        Optional<OAuth2RegisteredClientDO> optional = Optional.empty();
        String clientId = request.getClientId();
        String clientName = request.getClientName();
        if (StrUtil.isNotBlank(clientId)) {
            optional = oAuth2RegisteredClientService.findByClientId(clientId);
        }
        if (!optional.isPresent() && StrUtil.isNotBlank(clientName)) {
            optional = oAuth2RegisteredClientService.findByClientName(clientName);
        }
        return optional.map(data -> Result.success(objMapStruct.transfer(data))).orElseGet(Result::fail);
    }

    @Override
    public Result<List<OAuth2RegisteredClientDTO>> findAll(@RequestBody OAuth2RegisteredClientQueryRequest request) {
        OAuth2RegisteredClientDO oAuth2RegisteredClientDO = objMapStruct.transfer(request);

        List<OAuth2RegisteredClientDO> all = oAuth2RegisteredClientService.findAll(oAuth2RegisteredClientDO, request);
        List<OAuth2RegisteredClientDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<PageInfo<OAuth2RegisteredClientDTO>> list(@RequestBody OAuth2RegisteredClientQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        OAuth2RegisteredClientDO oAuth2RegisteredClientDO = objMapStruct.transfer(request);
        Page<OAuth2RegisteredClientDO> all = (Page<OAuth2RegisteredClientDO>) oAuth2RegisteredClientService.findAll(oAuth2RegisteredClientDO, request);

        PageInfo<OAuth2RegisteredClientDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }

}
