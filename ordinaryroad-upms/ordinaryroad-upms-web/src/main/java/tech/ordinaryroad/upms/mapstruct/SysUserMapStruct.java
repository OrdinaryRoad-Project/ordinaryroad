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
package tech.ordinaryroad.upms.mapstruct;

import org.mapstruct.Mapper;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.entity.SysUserDO;
import tech.ordinaryroad.upms.request.*;

/**
 * @author mjz
 * @date 2021/10/27
 */
@Mapper(componentModel = "spring")
public interface SysUserMapStruct {

    SysUserDO transfer(SysUserSaveRequest request);

    SysUserDO transfer(SysUserRegisterRequest request);

    SysUserDTO transfer(SysUserDO sysUserDO);

    SysUserDO transfer(SysUserQueryRequest request);

    SysUserDO transfer(SysUserUpdateUsernameRequest request);

    SysUserDO transfer(SysUserUpdatePasswordRequest request);
}
