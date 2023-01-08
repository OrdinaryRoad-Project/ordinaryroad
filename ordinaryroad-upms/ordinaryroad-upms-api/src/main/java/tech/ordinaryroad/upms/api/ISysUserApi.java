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
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.constants.ServiceNameCons;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.*;

import java.util.List;

/**
 * @author mjz
 * @date 2021/10/27
 */
@Api(value = "用户API")
@FeignClient(name = ServiceNameCons.SERVICE_NAME, contextId = "iSysUserApi")
public interface ISysUserApi {

    @PostMapping(value = "/user/create")
    Result<SysUserDTO> create(@Validated @RequestBody SysUserSaveRequest request);

    @PostMapping(value = "/user/find/id")
    Result<SysUserDTO> findById(@RequestBody SysUserQueryRequest request);

    @PostMapping(value = "/user/find_all")
    Result<List<SysUserDTO>> findAll(@RequestBody SysUserQueryRequest request);

    @PostMapping(value = "/user/list")
    Result<PageInfo<SysUserDTO>> list(@RequestBody SysUserQueryRequest request);

    @PostMapping(value = "/user/update")
    Result<Boolean> update(@Validated @RequestBody SysUserSaveRequest request);

    @PostMapping(value = "/user/delete")
    Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request);

    @PostMapping(value = "/user/find/unique")
    Result<SysUserDTO> findByUniqueColumn(@RequestBody SysUserQueryRequest request);

    @PostMapping(value = "/user/find_all/foreign")
    Result<List<SysUserDTO>> findAllByForeignColumn(@RequestBody SysUserQueryRequest request);

    /**
     * 根据唯一列查找用户
     *
     * @param request Request
     * @return Mono
     * @deprecated 暂时没搞懂怎么用
     */
    @Deprecated
    @PostMapping(value = "/user/find/unique/async")
    Mono<Result<SysUserDTO>> findByUniqueColumnAsync(@RequestBody SysUserQueryRequest request);

    @PostMapping(value = "/user/update/avatar")
    Result<Boolean> updateAvatar(@Validated @RequestBody SysUserUpdateAvatarRequest request);

    @PostMapping(value = "/user/update/username")
    Result<Boolean> updateUsername(@Validated @RequestBody SysUserUpdateUsernameRequest request);

    @PostMapping(value = "/user/update/email")
    Result<Boolean> updateEmail(@Validated @RequestBody SysUserUpdateEmailRequest request);

    @PostMapping(value = "/user/update/password")
    Result<Boolean> updatePassword(@Validated @RequestBody SysUserUpdatePasswordRequest request);

    @PostMapping(value = "/user/register")
    Result<SysUserDTO> register(@Validated @RequestBody SysUserRegisterRequest request);

    @PostMapping(value = "/user/reset/password")
    Result<?> resetPassword(@Validated @RequestBody SysUserResetPasswordRequest request);

    @PostMapping(value = "/user/reset/password_by_code")
    Result<?> resetPasswordByCode(@Validated @RequestBody SysUserResetPasswordByCodeRequest request);

    @PostMapping(value = "/user/update/enabled")
    Result<?> updateEnabled(@Validated @RequestBody SysUserUpdateEnabledRequest request);

    @PostMapping(value = "/user/update/user_roles")
    Result<Boolean> updateUserRoles(@Validated @RequestBody SysUserRolesSaveRequest request);
}
