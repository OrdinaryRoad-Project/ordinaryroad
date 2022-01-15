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
package tech.ordinaryroad.auth.server.facade.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.auth.server.dto.OAuth2OpenidDTO;
import tech.ordinaryroad.auth.server.entity.OAuth2OpenidDO;
import tech.ordinaryroad.auth.server.facade.IOAuth2OpenidFacade;
import tech.ordinaryroad.auth.server.mapstruct.OAuth2OpenidMapStruct;
import tech.ordinaryroad.auth.server.request.OAuth2OpenidQueryRequest;
import tech.ordinaryroad.auth.server.request.OAuth2OpenidSaveRequest;
import tech.ordinaryroad.auth.server.service.OAuth2OpenidService;
import tech.ordinaryroad.commons.core.base.cons.StatusCode;
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
@Component
public class OAuth2OpenidFacadeImpl implements IOAuth2OpenidFacade {

    private final OAuth2OpenidService oAuth2OpenidService;
    private final OAuth2OpenidMapStruct objMapStruct;

    @Override
    public Result<OAuth2OpenidDTO> create(OAuth2OpenidSaveRequest request) {
        // 唯一性校验
        String openid = request.getOpenid();
        Optional<OAuth2OpenidDO> byOpenid = oAuth2OpenidService.findByOpenid(openid);
        if (byOpenid.isPresent()) {
            return Result.fail(StatusCode.OPENID_ALREADY_EXIST);
        }
        String orNumber = request.getOrNumber();
        String clientId = request.getClientId();
        Optional<OAuth2OpenidDO> byClientIdAndOrNumber = oAuth2OpenidService.findByClientIdAndOrNumber(clientId, orNumber);
        if (byClientIdAndOrNumber.isPresent()) {
            return Result.fail(StatusCode.DATA_ALREADY_EXIST);
        }

        OAuth2OpenidDO oAuth2OpenidDO = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(oAuth2OpenidService.createSelective(oAuth2OpenidDO)));
    }

    @Override
    public Result<Boolean> delete(BaseDeleteRequest request) {
        return Result.success(oAuth2OpenidService.delete(request.getUuid()));
    }

    @Override
    public Result<OAuth2OpenidDTO> update(OAuth2OpenidSaveRequest request) {
        String uuid = request.getUuid();
        if (StrUtil.isBlank(uuid)) {
            return Result.fail(StatusCode.PARAM_NOT_COMPLETE);
        }

        OAuth2OpenidDO byId = oAuth2OpenidService.findById(uuid);
        if (Objects.isNull(byId)) {
            return Result.fail(StatusCode.DATA_NOT_EXIST);
        }

        String newOpenid = request.getOpenid();
        String openid = byId.getOpenid();
        if (!Objects.equals(newOpenid, openid)) {
            if (oAuth2OpenidService.findByOpenid(newOpenid).isPresent()) {
                return Result.fail(StatusCode.OPENID_ALREADY_EXIST);
            }
        }
        String newClientId = request.getClientId();
        String newOrNumber = request.getOrNumber();
        String clientId = byId.getClientId();
        String orNumber = byId.getOrNumber();
        if (!Objects.equals(newOrNumber, orNumber) || !Objects.equals(newClientId, clientId)) {
            if (oAuth2OpenidService.findByClientIdAndOrNumber(newClientId, newOrNumber).isPresent()) {
                return Result.fail(StatusCode.DATA_ALREADY_EXIST);
            }
        }

        OAuth2OpenidDO transfer = objMapStruct.transfer(request);
        return Result.success(objMapStruct.transfer(oAuth2OpenidService.updateSelective(transfer)));
    }

    @Override
    public Result<OAuth2OpenidDTO> findById(OAuth2OpenidQueryRequest request) {
        OAuth2OpenidDO byId = oAuth2OpenidService.findById(request.getUuid());
        if (Objects.nonNull(byId)) {
            return Result.success(objMapStruct.transfer(byId));
        }
        return Result.fail(StatusCode.DATA_NOT_EXIST);
    }

    @Override
    public Result<OAuth2OpenidDTO> findByClientIdAndOrNumber(OAuth2OpenidQueryRequest request) {
        Optional<OAuth2OpenidDO> byClientIdAndOrNumber = oAuth2OpenidService.findByClientIdAndOrNumber(request.getClientId(), request.getOrNumber());
        return byClientIdAndOrNumber.map(auth2OpenidDO -> Result.success(objMapStruct.transfer(auth2OpenidDO))).orElseGet(() -> Result.fail(StatusCode.DATA_NOT_EXIST));
    }

    @Override
    public Result<OAuth2OpenidDTO> findByClientIdAndOpenid(OAuth2OpenidQueryRequest request) {
        Optional<OAuth2OpenidDO> byClientIdAndOrNumber = oAuth2OpenidService.findByClientIdAndOpenid(request.getClientId(), request.getOpenid());
        return byClientIdAndOrNumber.map(auth2OpenidDO -> Result.success(objMapStruct.transfer(auth2OpenidDO))).orElseGet(() -> Result.fail(StatusCode.DATA_NOT_EXIST));
    }

    @Override
    public Result<List<OAuth2OpenidDTO>> findAll(OAuth2OpenidQueryRequest request) {
        OAuth2OpenidDO oAuth2OpenidDO = objMapStruct.transfer(request);

        List<OAuth2OpenidDO> all = oAuth2OpenidService.findAll(oAuth2OpenidDO);
        List<OAuth2OpenidDTO> list = all.stream().map(objMapStruct::transfer).collect(Collectors.toList());

        return Result.success(list);
    }

    @Override
    public Result<PageInfo<OAuth2OpenidDTO>> list(OAuth2OpenidQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        OAuth2OpenidDO oAuth2OpenidDO = objMapStruct.transfer(request);
        Page<OAuth2OpenidDO> all = (Page<OAuth2OpenidDO>) oAuth2OpenidService.findAll(oAuth2OpenidDO);

        PageInfo<OAuth2OpenidDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }
}
