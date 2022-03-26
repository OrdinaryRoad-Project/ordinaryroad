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
package tech.ordinaryroad.ioe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.util.StopWatch;
import tech.ordinaryroad.commons.satoken.properties.OAuth2ClientProperties;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author mjz
 * @date 2022/3/22
 */
@Slf4j
@EnableConfigurationProperties({OAuth2ClientProperties.class})
@EnableDiscoveryClient
@EnableFeignClients({"tech.ordinaryroad.**.**.api"})
@MapperScan({"tech.ordinaryroad.ioe.dao"})
@SpringBootApplication
public class OrdinaryRoadIoEApp {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("run");
        SpringApplication.run(OrdinaryRoadIoEApp.class, args);
        stopWatch.stop();
        log.info("run end！ {}", stopWatch.prettyPrint());
    }

}
