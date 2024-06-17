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

import java.util.Arrays;
import java.util.List;

/**
 * @author mjz
 * @date 2022/1/15
 */
public interface PathConstants {

    String UPMS_FILE_UPLOAD = "/file/upload";

    String UPMS_FILE_DOWNLOAD = "/file/download";
    String IM_MIMC_CALLBACK = "/mimc/callback";
    String AUTH_OPENID_CREATE = "/openid/create";

    /**
     * 创建免登接口地址
     * TODO 内部接口、免登、登录、免登或登录
     */
    List<String> NO_LOGIN_CREATE_PATHS = Arrays.asList(UPMS_FILE_UPLOAD, IM_MIMC_CALLBACK, AUTH_OPENID_CREATE);

    /**
     * 更新免登接口地址
     * TODO 内部接口、免登、登录、免登或登录
     */
    List<String> NO_LOGIN_UPDATE_PATHS = Arrays.asList(IM_MIMC_CALLBACK);

    /**
     * 不需要保存请求体的地址
     */
    List<String> DO_NOT_SET_REQUEST_BODY_PATHS = Arrays.asList(UPMS_FILE_UPLOAD);
    /**
     * 不需要保存响应体的地址
     */
    List<String> DO_NOT_SET_RESPONSE_BODY_PATHS = Arrays.asList(UPMS_FILE_DOWNLOAD);
}
