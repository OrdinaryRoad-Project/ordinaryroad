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
package tech.ordinaryroad.commons.thingsboard.service;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.Tenant;
import org.thingsboard.server.common.data.id.TenantId;
import tech.ordinaryroad.commons.base.exception.BaseException;

import java.util.Optional;
import java.util.UUID;

/**
 * @author mjz
 * @date 2022/3/25
 */
@RequiredArgsConstructor
@Service
public class OrThingsBoardTenantService {

    private final OrThingsBoardClientService clientService;

    public Tenant create(String email, String title) {
        if (StrUtil.isBlank(email) || StrUtil.isBlank(title)) {
            throw new BaseException("email and title cannot be empty when creating tenant");
        }
        final Tenant tenant = new Tenant();
        tenant.setEmail(email);
        tenant.setTitle(title);
        return clientService.getClient().saveTenant(tenant);
    }

    public void delete(String id) {
        if (StrUtil.isBlank(id)) {
            throw new BaseException("id cannot be empty when deleting tenant");
        }
        clientService.getClient().deleteTenant(new TenantId(UUID.fromString(id)));
    }

    public Optional<Tenant> findById(String id) {
        if (StrUtil.isBlank(id)) {
            return Optional.empty();
        }
        return clientService.getClient().getTenantById(new TenantId(UUID.fromString(id)));
    }

}
