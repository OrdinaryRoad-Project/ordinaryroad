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
package tech.ordinaryroad.auth.server.api;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import tech.ordinaryroad.auth.server.dto.OAuth2RegisteredClientDTO;
import tech.ordinaryroad.auth.server.request.OAuth2RegisteredClientQueryRequest;
import tech.ordinaryroad.auth.server.request.OAuth2RegisteredClientSaveRequest;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;

import java.util.List;

/**
 * @author mjz
 * @date 2022/1/14
 */
@Tag(name = "已注册的第三方客户端API")
@HttpExchange("http://ordinaryroad-auth-server")
public interface IOAuth2RegisteredClientApi {

    @PostExchange(value = "/registered_client/create")
    Result<OAuth2RegisteredClientDTO> create(@Validated @RequestBody OAuth2RegisteredClientSaveRequest request);

    @PostExchange(value = "/registered_client/delete")
    Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request);

    @PostExchange(value = "/registered_client/update")
    Result<OAuth2RegisteredClientDTO> update(@Validated @RequestBody OAuth2RegisteredClientSaveRequest request);

    @PostExchange(value = "/registered_client/find/id")
    Result<OAuth2RegisteredClientDTO> findById(@RequestBody OAuth2RegisteredClientQueryRequest request);

    @PostExchange(value = "/registered_client/find/unique")
    Result<OAuth2RegisteredClientDTO> findByUniqueColumn(@RequestBody OAuth2RegisteredClientQueryRequest request);

    @PostExchange(value = "/registered_client/find_all")
    Result<List<OAuth2RegisteredClientDTO>> findAll(@RequestBody OAuth2RegisteredClientQueryRequest request);

    @PostExchange(value = "/registered_client/list")
    Result<PageInfo<OAuth2RegisteredClientDTO>> list(@RequestBody OAuth2RegisteredClientQueryRequest request);

}
