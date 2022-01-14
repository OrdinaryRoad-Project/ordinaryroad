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

import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ordinaryroad.auth.server.api.IOAuth2RegisteredClientApi;
import tech.ordinaryroad.auth.server.dto.OAuth2RegisteredClientDTO;
import tech.ordinaryroad.auth.server.facade.IOAuth2RegisteredClientFacade;
import tech.ordinaryroad.auth.server.request.OAuth2RegisteredClientQueryRequest;
import tech.ordinaryroad.auth.server.request.OAuth2RegisteredClientSaveRequest;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;

import java.util.List;

/**
 * @author mjz
 * @date 2022/1/14
 */
@RequiredArgsConstructor
@RestController
public class OAuth2RegisteredClientController implements IOAuth2RegisteredClientApi {

    private final IOAuth2RegisteredClientFacade oAuth2RegisteredClientFacade;

    @Override
    public Result<OAuth2RegisteredClientDTO> create(@Validated @RequestBody OAuth2RegisteredClientSaveRequest request) {
        return oAuth2RegisteredClientFacade.create(request);
    }

    @Override
    public Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request) {
        return oAuth2RegisteredClientFacade.delete(request);
    }

    @Override
    public Result<OAuth2RegisteredClientDTO> update(@Validated @RequestBody OAuth2RegisteredClientSaveRequest request) {
        return oAuth2RegisteredClientFacade.update(request);
    }

    @Override
    public Result<OAuth2RegisteredClientDTO> findById(@RequestBody OAuth2RegisteredClientQueryRequest request) {
        return oAuth2RegisteredClientFacade.findById(request);
    }

    @Override
    public Result<OAuth2RegisteredClientDTO> findByUniqueColumn(@RequestBody OAuth2RegisteredClientQueryRequest request) {
        return oAuth2RegisteredClientFacade.findByUniqueColumn(request);
    }

    @Override
    public Result<List<OAuth2RegisteredClientDTO>> findAll(@RequestBody OAuth2RegisteredClientQueryRequest request) {
        return oAuth2RegisteredClientFacade.findAll(request);
    }

    @Override
    public Result<PageInfo<OAuth2RegisteredClientDTO>> list(@RequestBody OAuth2RegisteredClientQueryRequest request) {
        return oAuth2RegisteredClientFacade.list(request);
    }

}
