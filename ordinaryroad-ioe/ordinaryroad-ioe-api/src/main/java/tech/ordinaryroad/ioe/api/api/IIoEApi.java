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
package tech.ordinaryroad.ioe.api.api;


import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.ioe.api.constant.ServiceNameCons;
import tech.ordinaryroad.ioe.api.dto.IoEUserinfoDTO;

import javax.validation.constraints.NotBlank;

/**
 * @author mjz
 * @date 2022/3/25
 */
@Api(value = "IoE API")
@FeignClient(name = ServiceNameCons.SERVICE_NAME, contextId = "iIoEApi")
public interface IIoEApi {

    @ApiOperation(value = "OAuth2认证的重定向接口", notes = "使用openid登录本系统")
    @GetMapping("/authorized")
    ModelAndView authorized(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String state,
            // refuse
            @RequestParam(required = false) String handle,
            // 拒绝消息
            @RequestParam(required = false) String msg);

    @ApiOperation(value = "获取业务用户信息接口", notes = "openid未在ThingsBoard平台注册Customer则data为空")
    @GetMapping("/userinfo")
    Result<IoEUserinfoDTO> userinfo();

    @GetMapping("/thingsboard/token")
    Result<JsonNode> thingsboardToken();

    @GetMapping("/device/pbl/{id}/temp/unlock/pin")
    Result<String> pblTempUnlockPin(@PathVariable @Validated @NotBlank String id);

    @GetMapping("/no_auth/device/pbl/temp/unlock/pin/{pin}")
    Result<Boolean> pblUnlock(@PathVariable @Validated @NotBlank String pin);

}
