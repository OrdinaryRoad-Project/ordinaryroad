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

import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.minio.GetObjectResponse;
import io.minio.StatObjectResponse;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.ordinaryroad.commons.core.base.result.Result;
import tech.ordinaryroad.commons.minio.response.DownloadResponses;
import tech.ordinaryroad.commons.minio.service.OrMinioService;
import tech.ordinaryroad.commons.mybatis.utils.PageUtils;
import tech.ordinaryroad.upms.api.ISysFileApi;
import tech.ordinaryroad.upms.dto.SysFileDTO;
import tech.ordinaryroad.upms.entity.SysFileDO;
import tech.ordinaryroad.upms.mapstruct.SysFileMapStruct;
import tech.ordinaryroad.upms.request.SysFileQueryRequest;
import tech.ordinaryroad.upms.service.SysFileService;

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

    private final SysFileService sysFileService;
    private final SysFileMapStruct objMapStruct;
    private final OrMinioService orMinioService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<String> upload(@RequestParam String clientId, @RequestParam String clientSecret, @RequestPart MultipartFile file) {
        // Client校验
        SaOAuth2Util.checkClientSecret(clientId, clientSecret);

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
                        .scale(0.5)
                        .outputQuality(0.6)
                        .toFile(tempFile);
                // 保存缩略图
                String objectName = String.format("/%s/thumbnail/%s", dateString, filename);
                @Cleanup FileInputStream tempFileInputStream = new FileInputStream(tempFile);
                doUpload(tempFileInputStream, clientId, objectName, originalFilename, tempFile.length());
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
            String objectName = String.format("/%s/%s", dateString, filename);
            @Cleanup InputStream inputStream = file.getInputStream();
            doUpload(inputStream, clientId, objectName, originalFilename, file.getSize());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("file {} upload failed.", filename);
            return Result.fail(e.getMessage());
        }

        // 下载路径/{bucketName}/{objectName}
        String path;
        if (needCompress) {
            path = String.format("/%s/%s/thumbnail/%s", clientId, dateString, filename);
        } else {
            path = String.format("/%s/%s/%s", clientId, dateString, filename);
        }
        return Result.success(path);
    }

    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) Boolean showInline) {
        String fullPath = request.getServletPath().replace("/file/download/", "");
        int indexBetweenBucketAndObject = fullPath.indexOf("/");
        String bucketName = fullPath.substring(0, indexBetweenBucketAndObject);
        String objectName = fullPath.substring(indexBetweenBucketAndObject);
        // 图片默认inline，其它默认attachment
        String showType;

        try {
            DownloadResponses downloadResponses = orMinioService.download(bucketName, objectName);
            StatObjectResponse statObjectResponse = downloadResponses.getStatObjectResponse();
            String originalFilename = statObjectResponse.userMetadata().get(OrMinioService.METADATA_KEY_ORIGINAL_FILENAME);
            if (StrUtil.isBlank(originalFilename)) {
                int indexBetweenFilePathAndFilename = fullPath.lastIndexOf("/");
                originalFilename = fullPath.substring(indexBetweenFilePathAndFilename + 1);
            }
            @Cleanup GetObjectResponse getObjectResponse = downloadResponses.getGetObjectResponse();

            String extName = FileUtil.extName(originalFilename);
            if (ThumbnailatorUtils.isSupportedOutputFormat(extName)) {
                showType = "inline";
                if (BooleanUtil.isFalse(showInline)) {
                    showType = "attachment";
                }

                response.setContentType(String.format("image/%s", extName));
            } else {
                showType = "attachment";
                if (BooleanUtil.isTrue(showInline)) {
                    showType = "inline";
                }
            }

            response.setHeader(
                    HttpHeaders.CONTENT_DISPOSITION,
                    String.format("%s; filename=%s", showType, URLEncoder.encode(originalFilename, "UTF-8"))
            );
            response.setHeader(
                    HttpHeaders.CONTENT_LENGTH,
                    String.valueOf(statObjectResponse.size())
            );

            @Cleanup ServletOutputStream outputStream = response.getOutputStream();
            IoUtil.copy(getObjectResponse, outputStream);
        } catch (Exception e) {
            log.error("file download failed. {} {}", bucketName, objectName);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public Result<PageInfo<SysFileDTO>> list(@RequestBody SysFileQueryRequest request) {
        PageHelper.offsetPage(request.getOffset(), request.getLimit());

        SysFileDO sysFileDO = objMapStruct.transfer(request);
        Page<SysFileDO> all = (Page<SysFileDO>) sysFileService.findAll(sysFileDO, request);

        PageInfo<SysFileDTO> objectPageInfo = PageUtils.pageInfoDo2PageInfoDto(all, objMapStruct::transfer);

        return Result.success(objectPageInfo);
    }

    /**
     * 上传并记录日志
     *
     * @param inputStream      InputStream
     * @param realBucketName   桶名称
     * @param objectName       对象名称
     * @param originalFilename 原文件名
     * @param length           文件大小
     * @throws Exception Exception
     */
    private void doUpload(InputStream inputStream, String realBucketName, String objectName, String originalFilename, long length) throws Exception {
        orMinioService.upload(realBucketName, objectName, originalFilename, inputStream);
        // 记录日志
        SysFileDO sysFileDO = new SysFileDO();
        sysFileDO.setBucketName(realBucketName);
        sysFileDO.setObjectName(objectName);
        sysFileDO.setOriginalFilename(originalFilename);
        sysFileDO.setSize(length);
        sysFileService.createSelective(sysFileDO);
    }

}
