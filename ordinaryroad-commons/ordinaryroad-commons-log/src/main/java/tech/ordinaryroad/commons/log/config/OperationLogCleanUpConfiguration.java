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
package tech.ordinaryroad.commons.log.config;

import com.baomidou.dynamic.datasource.annotation.DS;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import tech.ordinaryroad.commons.base.exception.BaseException;
import tech.ordinaryroad.commons.log.entity.OperationLogDO;
import tech.ordinaryroad.commons.log.service.OperationLogService;

import java.time.LocalDateTime;

/**
 * @author mjz
 * @date 2024/6/17
 */
@Slf4j
@DS("or_commons_log")
@Configuration
@ConditionalOnExpression("${ordinaryroad.commons.log.clean-up.enabled:false}")
@RequiredArgsConstructor
public class OperationLogCleanUpConfiguration {

    private final OperationLogService operationLogService;

    @PostConstruct
    public void init() {
        if (minSecondsInterval < 60 || minSecondsInterval > 10 * 365 * 24 * 3600) {
            throw new BaseException("时间范围1min~10year");
        }
    }

    /**
     * 清理小于该时间的日志，默认清理两星期前的14*24*3600
     */
    @Value("${ordinaryroad.commons.log.clean-up.min-seconds-interval:1209600}")
    private long minSecondsInterval;

    /**
     * 每天3点清理日志
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanUp() {
        int delete = operationLogService.getDao()
                .wrapper()
                .lt(OperationLogDO::getCreatedTime, LocalDateTime.now().minusSeconds(minSecondsInterval))
                .delete();
        if (log.isDebugEnabled()) {
            log.debug("{} 条日志已清理", delete);
        }
    }

}
