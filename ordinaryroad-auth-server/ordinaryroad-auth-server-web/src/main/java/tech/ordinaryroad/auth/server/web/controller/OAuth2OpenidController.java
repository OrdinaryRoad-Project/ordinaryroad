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
import tech.ordinaryroad.auth.server.api.IOAuth2OpenidApi;
import tech.ordinaryroad.auth.server.dto.OAuth2OpenidDTO;
import tech.ordinaryroad.auth.server.facade.IOAuth2OpenidFacade;
import tech.ordinaryroad.auth.server.request.OAuth2OpenidQueryRequest;
import tech.ordinaryroad.auth.server.request.OAuth2OpenidSaveRequest;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;

import java.util.List;

/**
 * @author mjz
 * @date 2022/1/14
 */
@RequiredArgsConstructor
@RestController
public class OAuth2OpenidController implements IOAuth2OpenidApi {

    private final IOAuth2OpenidFacade oAuth2OpenidFacade;

    @Override
    public Result<OAuth2OpenidDTO> create(@Validated @RequestBody OAuth2OpenidSaveRequest request) {
        return oAuth2OpenidFacade.create(request);
    }

    @Override
    public Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request) {
        return oAuth2OpenidFacade.delete(request);
    }

    @Override
    public Result<OAuth2OpenidDTO> update(@Validated @RequestBody OAuth2OpenidSaveRequest request) {
        return oAuth2OpenidFacade.update(request);
    }

    @Override
    public Result<OAuth2OpenidDTO> findById(@RequestBody OAuth2OpenidQueryRequest request) {
        return oAuth2OpenidFacade.findById(request);
    }

    @Override
    public Result<OAuth2OpenidDTO> findByClientIdAndOrNumber(@RequestBody OAuth2OpenidQueryRequest request) {
        return oAuth2OpenidFacade.findByClientIdAndOrNumber(request);
    }

    @Override
    public Result<OAuth2OpenidDTO> findByClientIdAndOpenid(@RequestBody OAuth2OpenidQueryRequest request) {
        return oAuth2OpenidFacade.findByClientIdAndOpenid(request);
    }

    @Override
    public Result<List<OAuth2OpenidDTO>> findAll(@RequestBody OAuth2OpenidQueryRequest request) {
        return oAuth2OpenidFacade.findAll(request);
    }

    @Override
    public Result<PageInfo<OAuth2OpenidDTO>> list(@RequestBody OAuth2OpenidQueryRequest request) {
        return oAuth2OpenidFacade.list(request);
    }

}
