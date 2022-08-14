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
package tech.ordinaryroad.commons.mybatis.service;

import cn.hutool.core.util.IdUtil;
import tech.ordinaryroad.commons.mybatis.model.BaseDO;

/**
 * @author mjz
 * @date 2022/3/26
 */
public interface IFillMetaFieldService<T extends BaseDO> {

    /**
     * 创建时生成Uuid字段
     *
     * @param t BaseDO
     * @return String
     */
    default String generateUuid(T t) {
        return IdUtil.simpleUUID();
    }

    /**
     * 创建时生成CreateBy字段
     *
     * @param t BaseDO
     * @return String
     */
    String generateCreateBy(T t);

    /**
     * 更新时生成UpdateBy字段
     *
     * @param t BaseDO
     * @return String
     */
    String generateUpdateBy(T t);

    /**
     * 填充失败时错误信息收件箱
     *
     * @param t BaseDO
     * @param e Exception
     * @return String
     */
    default String emailToReceiveErrorMsgWhenGenerating(T t, Exception e) {
        return "1962247851@qq.com";
    }

}
