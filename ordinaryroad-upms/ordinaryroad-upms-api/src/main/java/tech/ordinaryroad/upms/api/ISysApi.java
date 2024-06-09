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

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.dto.SysUserInfoDTO;
import tech.ordinaryroad.upms.request.SysUserInfoRequest;

/**
 * @author mjz
 * @date 2021/12/10
 */
@Api(value = "API")
@HttpExchange("http://ordinaryroad-upms")
public interface ISysApi {

    /**
     * 获取当前请求用户的相关信息，User，Role，Permission，RequestPath
     *
     * @param request Request
     * @return Result
     */
    @PostExchange(value = "/userinfo")
    Result<SysUserInfoDTO> userInfo(@RequestBody(required = false) SysUserInfoRequest request);

}
