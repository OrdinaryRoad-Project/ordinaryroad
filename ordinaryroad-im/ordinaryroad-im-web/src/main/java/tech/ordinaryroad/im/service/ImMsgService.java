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

package tech.ordinaryroad.im.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.common.utils.Objects;
import org.springframework.stereotype.Service;
import tech.ordinaryroad.commons.core.base.request.query.BaseQueryRequest;
import tech.ordinaryroad.commons.mybatis.service.BaseService;
import tech.ordinaryroad.im.dao.ImMsgDAO;
import tech.ordinaryroad.im.entity.ImMsgDO;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;
import java.util.Optional;

/**
 * 单聊即时消息服务类
 *
 * @author mjz
 * @date 2022/1/21
 */
@Service
public class ImMsgService extends BaseService<ImMsgDAO, ImMsgDO> {

    public Optional<ImMsgDO> findByMsgId(String msgId) {
        if (StrUtil.isBlank(msgId)) {
            return Optional.empty();
        }

        WeekendSqls<ImMsgDO> sqls = WeekendSqls.custom();

        Example example = Example.builder(ImMsgDO.class)
                .where(sqls.andEqualTo(ImMsgDO::getMsgId, msgId))
                .build();
        return Optional.ofNullable(super.dao.selectOneByExample(example));
    }

    public List<ImMsgDO> findAll(ImMsgDO imMsgDO, String orNumber, String chatPartnerOrNumber, BaseQueryRequest baseQueryRequest) {
        WeekendSqls<ImMsgDO> sqls = WeekendSqls.custom();
        String payload = imMsgDO.getPayload();
        if (StrUtil.isNotBlank(payload)) {
            sqls.andLike(ImMsgDO::getPayload, "%" + payload + "%");
        }
        String bizType = imMsgDO.getBizType();
        if (StrUtil.isNotBlank(bizType)) {
            sqls.andEqualTo(ImMsgDO::getBizType, bizType);
        }
        Boolean read = imMsgDO.getRead();
        if (Objects.nonNull(read)) {
            sqls.andEqualTo(ImMsgDO::getRead, read);
        }

        WeekendSqls<ImMsgDO> orNumberSqls1 = WeekendSqls.custom();
        orNumberSqls1.andEqualTo(ImMsgDO::getCreateBy, orNumber).andEqualTo(ImMsgDO::getToOrNumber, chatPartnerOrNumber);
        WeekendSqls<ImMsgDO> orNumberSqls2 = WeekendSqls.custom();
        orNumberSqls2.andEqualTo(ImMsgDO::getCreateBy, chatPartnerOrNumber).andEqualTo(ImMsgDO::getToOrNumber, orNumber);

        Example.Builder exampleBuilder = Example.builder(ImMsgDO.class).where(sqls).orWhere(orNumberSqls1).orWhere(orNumberSqls2);

        return super.findAll(baseQueryRequest, sqls, exampleBuilder);
    }
}
