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
package tech.ordinaryroad.commons.core.constant;

/**
 * 缓存常量
 * <p>
 * key命名规则（https://www.cnblogs.com/joshua317/p/11995197.html）：
 * 业务模块名:业务逻辑含义:其他:value类型
 *
 * @author mjz
 * @date 2021/11/27
 */
public class CacheConstants {

    /**
     * 默认验证码的key
     */
    public static String CAPTCHA_DEFAULT_KEY = "gateway:captcha.default:%s:string";

    /**
     * 登录验证码的key
     */
    public static String CAPTCHA_LOGIN_KEY = "gateway:captcha.login:%s:string";

    /**
     * 注册验证码的key
     */
    public static String CAPTCHA_REGISTER_KEY = "gateway:captcha.register:%s:string";

}
