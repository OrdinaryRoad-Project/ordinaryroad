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
package tech.ordinaryroad.upms.api;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.dto.SysRequestPathDTO;
import tech.ordinaryroad.upms.request.SysRequestPathQueryRequest;
import tech.ordinaryroad.upms.request.SysRequestPathSaveRequest;

import java.util.List;

/**
 * @author mjz
 * @date 2021/11/9
 */
@Tag(name = "请求路径API")
@HttpExchange("http://ordinaryroad-upms")
public interface ISysRequestPathApi {

    @PostExchange(value = "/request_path/create")
    Result<SysRequestPathDTO> create(@Validated @RequestBody SysRequestPathSaveRequest request);

    @PostExchange(value = "/request_path/delete")
    Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request);

    @PostExchange(value = "/request_path/update")
    Result<SysRequestPathDTO> update(@Validated @RequestBody SysRequestPathSaveRequest request);

    @PostExchange(value = "/request_path/find/id")
    Result<SysRequestPathDTO> findById(@RequestBody SysRequestPathQueryRequest request);

    @PostExchange(value = "/request_path/find/unique")
    Result<SysRequestPathDTO> findByUniqueColumn(@RequestBody SysRequestPathQueryRequest request);

    @PostExchange(value = "/request_path/find_all/permission_uuids")
    Result<List<SysRequestPathDTO>> findAllByPermissionUuids(@RequestBody SysRequestPathQueryRequest request);

    @PostExchange(value = "/request_path/find_all/user_uuids")
    Result<List<SysRequestPathDTO>> findAllByUserUuid(@RequestBody SysRequestPathQueryRequest request);

    @PostExchange(value = "/request_path/find_all/ids")
    Result<List<SysRequestPathDTO>> findAllByIds(@RequestBody BaseQueryRequest request);

    @PostExchange(value = "/request_path/find_all")
    Result<List<SysRequestPathDTO>> findAll(@RequestBody SysRequestPathQueryRequest request);

    @PostExchange(value = "/request_path/list")
    Result<PageInfo<SysRequestPathDTO>> list(@RequestBody SysRequestPathQueryRequest request);

}
