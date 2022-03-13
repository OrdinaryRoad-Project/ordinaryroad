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
package tech.ordinaryroad.push.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.push.facade.IPushFacade;
import tech.ordinaryroad.push.request.EmailPushRequest;
import tech.ordinaryroad.push.service.impl.EmailPushService;

/**
 * @author mjz
 * @date 2021/11/27
 */
@RequiredArgsConstructor
@Component
public class EmailPushFacadeImpl implements IPushFacade<EmailPushRequest> {

    private final EmailPushService emailPushService;

    @Override
    public Result<?> send(EmailPushRequest request) {
        if (emailPushService.send(request.getEmail(), request.getTitle(), request.getContent(), request.getMimeType())) {
            return Result.success();
        } else {
            return Result.fail();
        }
    }
}
