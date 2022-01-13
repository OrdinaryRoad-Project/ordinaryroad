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
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.upms.constants.ServiceNameCons;
import tech.ordinaryroad.upms.dto.SysFileDTO;
import tech.ordinaryroad.upms.request.SysFileQueryRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mjz
 * @date 2022/1/11
 */
@Api(value = "文件API")
@FeignClient(name = ServiceNameCons.SERVICE_NAME, contextId = "iSysFileApi")
public interface ISysFileApi {

    /**
     * 上传文件
     *
     * @param bucketName 桶名称
     * @param file       资源
     * @return Result
     */
    @PostMapping(value = "/file/upload")
    Result<String> upload(@RequestParam(value = "bucketName", required = false) String bucketName, @RequestPart("file") MultipartFile file);

    /**
     * 下载文件
     *
     * @param request    HttpServletRequest
     * @param response   HttpServletResponse
     * @param showInline 是否直接在网页中显示而不是直接下载
     */
    @GetMapping(value = "/file/download/**", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    void download(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false, defaultValue = "false") Boolean showInline);

    @PostMapping(value = "/file/list")
    Result<PageInfo<SysFileDTO>> list(@RequestBody SysFileQueryRequest request);

}
