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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

/**
 * 邮箱推送服务类
 *
 * @author mjz
 * @date 2022/3/13
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailPushService {

    private final JavaMailSender mailSender;

    public Boolean send(@NotNull String email, @NotNull String title, @NotNull String content, MimeType mimeType) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
        try {
            // from必须
            helper.setFrom("1962247851@qq.com", "OrdinaryRoad");
            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(mimeMessage);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("Send email to {} failed", email);
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

}
