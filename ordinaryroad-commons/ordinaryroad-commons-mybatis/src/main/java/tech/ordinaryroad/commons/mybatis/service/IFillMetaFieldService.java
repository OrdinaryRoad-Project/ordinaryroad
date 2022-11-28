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
     * 创建时生成uuid字段
     *
     * @return String
     */
    default String generateUuid(T t) {
        return IdUtil.getSnowflake().nextIdStr();
    }

    /**
     * 创建时生成uuid字段失败
     */
    default void generateUuidFailed(T t, Exception e) {
        // ignore
    }

    /**
     * 创建时生成CreateBy字段
     *
     * @return String
     */
    String generateCreateBy();

    /**
     * 创建时生成CreateBy字段失败
     */
    default void generateCreateByFailed(T t, Exception e) {
        // ignore
    }

    /**
     * 更新时生成UpdateBy字段
     *
     * @return String
     */
    String generateUpdateBy();

    /**
     * 更新时生成UpdateBy字段失败
     */
    default void generateUpdateByFailed(T t, Exception e) {
        // ignore
    }

    /**
     * 插入前的回掉
     *
     * @param t BaseDO
     */
    default void beforeInsert(T t) {
        // ignore
    }

    /**
     * 更新前的回掉
     *
     * @param t BaseDO
     */
    default void beforeUpdate(T t) {
        // ignore
    }

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
