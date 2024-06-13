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
import reactor.core.publisher.Mono;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.*;

import java.util.List;

/**
 * @author mjz
 * @date 2021/10/27
 */
@Tag(name = "用户API")
@HttpExchange("http://ordinaryroad-upms")
public interface ISysUserApi {

    @PostExchange(value = "/user/create")
    Result<SysUserDTO> create(@Validated @RequestBody SysUserSaveRequest request);

    @PostExchange(value = "/user/find/id")
    Result<SysUserDTO> findById(@RequestBody SysUserQueryRequest request);

    @PostExchange(value = "/user/find_all")
    Result<List<SysUserDTO>> findAll(@RequestBody SysUserQueryRequest request);

    @PostExchange(value = "/user/list")
    Result<PageInfo<SysUserDTO>> list(@RequestBody SysUserQueryRequest request);

    @PostExchange(value = "/user/update")
    Result<Boolean> update(@Validated @RequestBody SysUserSaveRequest request);

    @PostExchange(value = "/user/delete")
    Result<Boolean> delete(@Validated @RequestBody BaseDeleteRequest request);

    @PostExchange(value = "/user/find/unique")
    Result<SysUserDTO> findByUniqueColumn(@RequestBody SysUserQueryRequest request);

    @PostExchange(value = "/user/find_all/foreign")
    Result<List<SysUserDTO>> findAllByForeignColumn(@RequestBody SysUserQueryRequest request);

    /**
     * 根据唯一列查找用户
     *
     * @param request Request
     * @return Mono
     * @deprecated 暂时没搞懂怎么用
     */
    @Deprecated
    @PostExchange(value = "/user/find/unique/async")
    Mono<Result<SysUserDTO>> findByUniqueColumnAsync(@RequestBody SysUserQueryRequest request);

    @PostExchange(value = "/user/update/avatar")
    Result<Boolean> updateAvatar(@Validated @RequestBody SysUserUpdateAvatarRequest request);

    @PostExchange(value = "/user/update/username")
    Result<Boolean> updateUsername(@Validated @RequestBody SysUserUpdateUsernameRequest request);

    @PostExchange(value = "/user/update/email")
    Result<Boolean> updateEmail(@Validated @RequestBody SysUserUpdateEmailRequest request);

    @PostExchange(value = "/user/update/password")
    Result<Boolean> updatePassword(@Validated @RequestBody SysUserUpdatePasswordRequest request);

    @PostExchange(value = "/user/register")
    Result<SysUserDTO> register(@Validated @RequestBody SysUserRegisterRequest request);

    @PostExchange(value = "/user/reset/password")
    Result<?> resetPassword(@Validated @RequestBody SysUserResetPasswordRequest request);

    @PostExchange(value = "/user/reset/password_by_code")
    Result<?> resetPasswordByCode(@Validated @RequestBody SysUserResetPasswordByCodeRequest request);

    @PostExchange(value = "/user/update/enabled")
    Result<?> updateEnabled(@Validated @RequestBody SysUserUpdateEnabledRequest request);

    @PostExchange(value = "/user/update/user_roles")
    Result<Boolean> updateUserRoles(@Validated @RequestBody SysUserRolesSaveRequest request);
}
