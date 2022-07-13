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
package tech.ordinaryroad.im.properties;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mjz
 * @date 2021/12/13
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ordinaryroad.im")
public class OrImProperties {

    private List<MimcProperties> mimcPropertiesList = new ArrayList<>();

    @Data
    public static class MimcProperties {
        private String appId;
        private String appKey;
        private String appSecret;
        private String regionKey = "REGION_CN";
        private String domain = "https://mimc.chat.xiaomi.net/";
        private String tokenUrl = "https://mimc.chat.xiaomi.net/api/account/token";
        private String packageName;
    }

    public MimcProperties getProperties(String appId) {
        if (StrUtil.isBlank(appId)) {
            return null;
        }

        List<MimcProperties> collect = mimcPropertiesList.stream()
                .filter(mimcProperties -> mimcProperties.getAppId().equals(appId))
                .collect(Collectors.toList());

        return CollUtil.getFirst(collect);
    }

}
