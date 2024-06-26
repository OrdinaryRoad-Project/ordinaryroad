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
package tech.ordinaryroad.upms.config;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import tech.ordinaryroad.commons.mybatis.model.BaseDO;
import tech.ordinaryroad.commons.mybatis.service.impl.DefaultFillMetaFieldServiceImpl;
import tech.ordinaryroad.upms.entity.SysUserDO;

/**
 * @author mjz
 * @date 2022/11/25
 */
@Slf4j
@Configuration
public class SysFillMetaFieldServiceImpl<T extends BaseDO> extends DefaultFillMetaFieldServiceImpl<T> {

    @Override
    public void beforeInsert(T t) {
        if (t instanceof SysUserDO) {
            SysUserDO sysUserDO = (SysUserDO) t;
            // TODO 测试一下 or帐号生成服务
            SysOrNumberService orNumberService = SpringUtil.getBean(SysOrNumberService.class);
            sysUserDO.setOrNumber(orNumberService.genId());
        }
    }

}
