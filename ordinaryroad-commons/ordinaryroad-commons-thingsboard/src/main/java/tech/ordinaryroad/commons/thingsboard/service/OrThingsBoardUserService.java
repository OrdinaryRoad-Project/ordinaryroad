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
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.User;
import org.thingsboard.server.common.data.id.CustomerId;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.id.UserId;
import org.thingsboard.server.common.data.security.Authority;
import tech.ordinaryroad.commons.thingsboard.properties.OrThingsBoardProperties;

import java.util.UUID;

/**
 * @author mjz
 * @date 2022/3/25
 */
@RequiredArgsConstructor
@Service
public class OrThingsBoardUserService {

    private final OrThingsBoardProperties thingsBoardProperties;
    private final OrThingsBoardClientService clientService;

    /**
     * Create or update the User. When creating user, platform generates User Id as time-based UUID. The newly created User Id will be present in the response. Specify existing User Id to update the device. Referencing non-existing User Id will cause 'Not Found' error.
     * <p>
     * Device email is unique for entire platform setup.
     *
     * @param email      邮箱
     * @param firstName  名
     * @param lastName   姓
     * @param tenantId   租户ID
     * @param customerId 租户下的客户ID
     * @return User
     */
    public User create(String email, String firstName, String lastName, String tenantId, String customerId) {
        final User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setTenantId(TenantId.fromUUID(UUID.fromString(tenantId)));
        user.setCustomerId(new CustomerId(UUID.fromString(customerId)));
        user.setAuthority(Authority.CUSTOMER_USER);
        return clientService.getClient().saveUser(user, false);
    }

    public void delete(String id) {
        clientService.getClient().deleteUser(new UserId(UUID.fromString(id)));
    }

    /**
     * 激活用户，默认密码为 Abc123
     *
     * @param id 用户Id
     * @return {
     * "refreshToken": "AAB254FF67D..",
     * "token": "AAB254FF67D.."
     * }
     * @see OrThingsBoardProperties.OrThingsBoardCustomerProperties#getDefaultUserPassword()
     */
    public JsonNode active(String id) {
        String defaultUserPassword = thingsBoardProperties.getCustomer().getDefaultUserPassword();
        return this.active(id, defaultUserPassword);
    }

    /**
     * 激活用户，默认密码为 Abc123
     *
     * @param id       用户Id
     * @param password 默认密码
     * @return {
     * "refreshToken": "AAB254FF67D..",
     * "token": "AAB254FF67D.."
     * }
     * @see OrThingsBoardProperties.OrThingsBoardCustomerProperties#getDefaultUserPassword()
     */
    public JsonNode active(String id, String password) {
        password = StrUtil.blankToDefault(password, OrThingsBoardProperties.OrThingsBoardCustomerProperties.DEFAULT_USER_PASSWORD);
        return clientService.getClient().activateUser(new UserId(UUID.fromString(id)), password).orElseThrow();
    }

    public User findById(String id) {
        return clientService.getClient().getUserById(new UserId(UUID.fromString(id))).orElseThrow();
    }

    /**
     * Returns the token of the User based on the provided User Id.
     * If the user who performs the request has the authority of 'SYS_ADMIN', it is possible to get the token of any tenant administrator.
     * If the user who performs the request has the authority of 'TENANT_ADMIN', it is possible to get the token of any customer user that belongs to the same tenant.
     *
     * @param id 用户Id
     * @return {
     * "refreshToken": "AAB254FF67D..",
     * "token": "AAB254FF67D.."
     * }
     */
    public JsonNode getToken(String id) {
        return clientService.getClient().getUserToken(UserId.fromString(id)).orElseThrow();
    }

}
