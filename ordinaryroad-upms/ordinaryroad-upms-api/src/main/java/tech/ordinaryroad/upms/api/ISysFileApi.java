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
package tech.ordinaryroad.upms.api;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.core.constant.PathConstants;
import tech.ordinaryroad.upms.dto.SysFileDTO;
import tech.ordinaryroad.upms.request.SysFileQueryRequest;

/**
 * @author mjz
 * @date 2022/1/11
 */
@Tag(name = "文件API")
@HttpExchange("http://ordinaryroad-upms")
public interface ISysFileApi {

    /**
     * 上传文件
     *
     * @param clientId     clientId
     * @param clientSecret clientSecret
     * @param file         文件
     * @return Result
     */
    @PostExchange(value = PathConstants.UPMS_FILE_UPLOAD)
    Result<String> upload(@RequestParam String clientId, @RequestParam String clientSecret, @RequestPart MultipartFile file);

    /**
     * 下载文件
     *
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @param showInline 是否直接在网页中显示而不是直接下载
     */
    @GetExchange(value = PathConstants.UPMS_FILE_DOWNLOAD + "/**")
    void download(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) Boolean showInline);

    @PostExchange(value = "/file/list")
    Result<PageInfo<SysFileDTO>> list(@RequestBody SysFileQueryRequest request);

}
