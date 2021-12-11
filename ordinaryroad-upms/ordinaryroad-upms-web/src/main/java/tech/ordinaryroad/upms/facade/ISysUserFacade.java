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
package tech.ordinaryroad.upms.facade;

import com.github.pagehelper.PageInfo;
import tech.ordinaryroad.commons.core.base.request.delete.BaseDeleteRequest;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.*;

import java.util.List;

/**
 * @author mjz
 * @date 2021/10/27
 */
public interface ISysUserFacade {

    /**
     * 创建
     *
     * @param request Request
     * @return DTO
     */
    Result<SysUserDTO> create(SysUserSaveRequest request);

    /**
     * 查询
     *
     * @param request Request
     * @return DTO
     */
    Result<SysUserDTO> findById(SysUserQueryRequest request);

    /**
     * 查询所有
     *
     * @param request Request
     * @return List
     */
    Result<List<SysUserDTO>> findAll(SysUserQueryRequest request);

    /**
     * 分页查询所有
     *
     * @param request Request
     * @return Page
     */
    Result<PageInfo<SysUserDTO>> list(SysUserQueryRequest request);

    /**
     * 更新
     *
     * @param request Request
     * @return DTO
     */
    Result<SysUserDTO> update(SysUserSaveRequest request);

    /**
     * 删除
     *
     * @param request Request
     * @return DTO
     */
    Result<Boolean> delete(BaseDeleteRequest request);

    /**
     * 更新用户名
     *
     * @param request Request
     * @return Boolean
     */
    Result<Boolean> updateUsername(SysUserUpdateUsernameRequest request);

    /**
     * 更新邮箱
     *
     * @param request Request
     * @return Boolean
     */
    Result<Boolean> updateEmail(SysUserUpdateEmailRequest request);

    /**
     * 更新密码
     *
     * @param request Request
     * @return Boolean
     */
    Result<Boolean> updatePassword(SysUserUpdatePasswordRequest request);

    /**
     * 根据唯一列查询用户
     *
     * @param request Request
     * @return DTO
     */
    Result<SysUserDTO> findByUniqueColumn(SysUserQueryRequest request);

    /**
     * 用户注册
     *
     * @param request Request
     * @return DTO
     */
    Result<SysUserDTO> register(SysUserRegisterRequest request);

    /**
     * 重置密码
     *
     * @param request Request
     * @return Boolean
     */
    Result<?> resetPassword(SysUserResetPasswordRequest request);

    Result<?> updateEnabled(SysUserUpdateEnabledRequest request);

    /**
     * 根据关联外键查询所有用户
     *
     * @param request Request
     * @return Result
     */
    Result<List<SysUserDTO>> findAllByForeignColumn(SysUserQueryRequest request);
}
