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
package tech.ordinaryroad.commons.mybatis.service.impl;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import tech.ordinaryroad.commons.mybatis.model.BaseDO;
import tech.ordinaryroad.commons.mybatis.service.IFillMetaFieldService;
import tech.ordinaryroad.push.api.IPushApi;
import tech.ordinaryroad.push.request.EmailPushRequest;

/**
 * @author mjz
 * @date 2022/3/26
 */
@Slf4j
@ConditionalOnMissingBean(IFillMetaFieldService.class)
@Configuration
public class FillMetaFieldServiceImpl<T extends BaseDO> implements IFillMetaFieldService<T> {

    @Autowired
    protected IPushApi pushApi;

    @Override
    public String generateUuid(T t) {
        return IdUtil.simpleUUID();
    }

    @Override
    public void generateUuidFailed(T t, Exception e) {
        String requestPath = SaHolder.getRequest().getRequestPath();
        final EmailPushRequest emailPushRequest = new EmailPushRequest();
        // 设置邮箱
        emailPushRequest.setEmail(emailToReceiveErrorMsgWhenGenerating(t, e));
        emailPushRequest.setTitle("创建时填充字段异常");
        emailPushRequest.setContent("fillMetaFieldsWhenCreate uuid failed, " + requestPath + "\n" + ExceptionUtil.getMessage(e));
        pushApi.email(emailPushRequest);
    }

    @Override
    public String generateCreateBy() {
        return StpUtil.getLoginIdAsString();
    }

    @Override
    public void generateCreateByFailed(T t, Exception e) {
        String requestPath = SaHolder.getRequest().getRequestPath();
        final EmailPushRequest emailPushRequest = new EmailPushRequest();
        emailPushRequest.setEmail(emailToReceiveErrorMsgWhenGenerating(t, e));
        emailPushRequest.setTitle("创建时填充字段异常");
        emailPushRequest.setContent("fillMetaFieldsWhenCreate createBy failed, " + requestPath + "\n" + ExceptionUtil.getMessage(e));
        pushApi.email(emailPushRequest);
    }

    @Override
    public String generateUpdateBy() {
        return StpUtil.getLoginIdAsString();
    }

    @Override
    public void generateUpdateByFailed(T t, Exception e) {
        String requestPath = SaHolder.getRequest().getRequestPath();
        final EmailPushRequest emailPushRequest = new EmailPushRequest();
        emailPushRequest.setEmail(emailToReceiveErrorMsgWhenGenerating(t, e));
        emailPushRequest.setTitle("更新时填充字段异常");
        emailPushRequest.setContent("fillMetaFieldsWhenCreate updateBy failed, " + requestPath + "\n" + ExceptionUtil.getMessage(e));
        pushApi.email(emailPushRequest);
    }
}
