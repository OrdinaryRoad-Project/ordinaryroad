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
package tech.ordinaryroad.push.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotNull;
import java.util.Collections;

/**
 * 安卓客户推送服务类（极光推送）
 *
 * @author mjz
 * @date 2022/3/13
 */
@RequiredArgsConstructor
@Service
public class AndroidPushService {

    private final JPushService jPushService;

    public Boolean send(@NotNull String packageName, @NotNull String orNumber, @NotNull String title, @NotNull String content, @NotNull String channel, JSONObject intent, JSONObject extras) {
        return jPushService.sendToAlias(packageName, Collections.singletonList(orNumber), title, title, content, channel, intent.toJavaObject(JsonObject.class), extras.toJavaObject(JsonObject.class)) == 1;
    }

}
