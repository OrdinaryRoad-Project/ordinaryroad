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
package tech.ordinaryroad.upms.web.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.minio.GetObjectResponse;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.minio.response.DownloadResponses;
import tech.ordinaryroad.commons.minio.service.OrMinioService;
import tech.ordinaryroad.upms.api.ISysFileApi;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;

/**
 * @author mjz
 * @date 2022/1/11
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class SysFileController implements ISysFileApi {

    private final OrMinioService orMinioService;

    @Override
    public Result<String> upload(@RequestParam(value = "bucketName", required = false) String bucketName, @RequestPart("file") MultipartFile file) {
        String realBucketName = orMinioService.getBucketName(bucketName);
        String originalFilename = file.getOriginalFilename();
        String prefix = IdUtil.fastSimpleUUID();
        String extName = FileUtil.extName(originalFilename);
        String suffix = StrUtil.DOT + extName;
        String filename = prefix + suffix;
        String dateString = DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATE_PATTERN);

        // 是否需要压缩 大于1MB并且支持压缩
        boolean needCompress = file.getSize() > 1024 * 1024;
        if (needCompress) {
            needCompress = ThumbnailatorUtils.isSupportedOutputFormat(extName);
        }

        // 保存缩略图
        if (needCompress) {
            try {
                // 缩略图临时文件
                File tempFile = FileUtil.createTempFile(prefix, suffix, null, true);
                @Cleanup InputStream inputStream = file.getInputStream();
                Thumbnails.of(inputStream)
                        .scale(1.0)
                        .outputQuality(0.4)
                        .toFile(tempFile);
                // 保存缩略图
                String realTempFilename = String.format("/%s/thumbnail/%s", dateString, filename);
                @Cleanup FileInputStream tempFileInputStream = new FileInputStream(tempFile);
                orMinioService.upload(realBucketName, realTempFilename, originalFilename, tempFileInputStream);
            } catch (Exception e) {
                e.printStackTrace();
                // 图片创建缩略图报错
                log.error("error when creating thumbnail of {}.", filename);
                return Result.fail(e.getMessage());
            }
        }
        // 保存源文件
        try {
            // 文件名改为UUID.扩展名
            String realFilename = String.format("/%s/%s", dateString, filename);
            @Cleanup InputStream inputStream = file.getInputStream();
            orMinioService.upload(realBucketName, realFilename, originalFilename, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("file {} upload failed.", filename);
            return Result.fail(e.getMessage());
        }

        // 下载路径/{bucketName}/{objectName}
        String path;
        if (needCompress) {
            path = String.format("/%s/%s/thumbnail/%s", realBucketName, dateString, filename);
        } else {
            path = String.format("/%s/%s/%s", realBucketName, dateString, filename);
        }
        return Result.success(path);
    }

    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false, defaultValue = "false") Boolean showInline) {
        String fullPath = request.getServletPath().replace("/file/download/", "");
        int indexBetweenBucketAndFilename = fullPath.indexOf("/");
        String bucketName = fullPath.substring(0, indexBetweenBucketAndFilename);
        String filename = fullPath.substring(indexBetweenBucketAndFilename);
        // 图片默认inline，其它默认attachment
        String showType = "attachment";

        try {
            DownloadResponses downloadResponses = orMinioService.download(bucketName, filename);
            String originalFilename = downloadResponses.getStatObjectResponse().userMetadata().get(OrMinioService.METADATA_KEY_ORIGINAL_FILENAME);
            @Cleanup GetObjectResponse getObjectResponse = downloadResponses.getGetObjectResponse();

            String extName = FileUtil.extName(originalFilename);
            if (ThumbnailatorUtils.isSupportedOutputFormat(extName)) {
                response.setContentType(String.format("image/%s", extName));
                if (!showInline) {
                    showType = "inline";
                }
            }

            response.setHeader(
                    "Content-Disposition",
                    String.format("%s; filename=%s", showType, URLEncoder.encode(originalFilename, "UTF-8"))
            );

            @Cleanup ServletOutputStream outputStream = response.getOutputStream();
            IoUtil.copy(getObjectResponse, outputStream);
        } catch (Exception e) {
            log.error("file download failed. {} {}", bucketName, filename);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
