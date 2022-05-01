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
package tech.ordinaryroad.ioe.service;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.exception.BaseException;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.ioe.entity.IoEUserDO;
import tech.ordinaryroad.upms.api.ISysUserApi;
import tech.ordinaryroad.upms.dto.SysUserDTO;
import tech.ordinaryroad.upms.request.SysUserQueryRequest;

import java.util.Optional;

/**
 * @author mjz
 * @date 2022/3/26
 */
@RequiredArgsConstructor
@Service
public class IoEService {

    private final IoEUserService userService;
    private final ISysUserApi sysUserApi;

    public IoEUserDO getUser() {
        final String orNumber = StpUtil.getLoginIdAsString();
        final Optional<IoEUserDO> optional = userService.findByOrNumber(orNumber);
        return optional.orElseThrow();
    }

    public String getOrNumber() {
        return this.getUser().getOrNumber();
    }

    public String getCustomerId() {
        return this.getUser().getCustomerId();
    }

    public String getUserId() {
        return this.getUser().getUserId();
    }

    public String getEmail(String orNumber) {
        final SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
        sysUserQueryRequest.setOrNumber(orNumber);
        Result<SysUserDTO> byUniqueColumn = sysUserApi.findByUniqueColumn(sysUserQueryRequest);
        if (!byUniqueColumn.getSuccess()) {
            throw new BaseException(byUniqueColumn.getMsg());
        }
        return byUniqueColumn.getData().getEmail();
    }

    public String getEmail() {
        final String orNumber = getOrNumber();
        return this.getEmail(orNumber);
    }

}
