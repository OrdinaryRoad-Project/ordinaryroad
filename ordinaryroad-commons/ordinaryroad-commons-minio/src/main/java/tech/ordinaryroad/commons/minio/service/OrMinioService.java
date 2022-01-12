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
package tech.ordinaryroad.commons.minio.service;

import cn.hutool.core.util.StrUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.commons.minio.properties.OrMinioProperties;
import tech.ordinaryroad.commons.minio.response.DownloadResponses;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.HashMap;

/**
 * MinIO服务类
 *
 * @author mjz
 * @date 2022/1/11
 */
@RequiredArgsConstructor
@Component
public class OrMinioService {

    private final OrMinioProperties minioProperties;
    private final MinioClient minioClient;
    public static final String METADATA_KEY_ORIGINAL_FILENAME = "original-filename";

    public void upload(@NotNull String bucketName, @NotNull String filename, @NotNull String originalFilename, @NotNull InputStream inputStream) throws Exception {
        HashMap<String, String> userMetadata = new HashMap<>(1);
        userMetadata.put(METADATA_KEY_ORIGINAL_FILENAME, originalFilename);
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .bucket(bucketName)
                .object(filename)
                .userMetadata(userMetadata)
                .stream(inputStream, inputStream.available(), -1)
                .build();
        minioClient.putObject(putObjectArgs);
    }

    public DownloadResponses download(@NotNull String bucketName, @NotNull String filename) throws Exception {
        DownloadResponses downloadResponses = new DownloadResponses();
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .build();
        downloadResponses.setGetObjectResponse(minioClient.getObject(getObjectArgs));
        StatObjectArgs statObjectArgs = StatObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .build();
        downloadResponses.setStatObjectResponse(minioClient.statObject(statObjectArgs));
        return downloadResponses;
    }

    /**
     * 获取桶名称，传入的优先，没传则使用配置文件中的桶名称{@link OrMinioProperties#getBucketName()}
     *
     * @param bucketName 桶名称
     * @return String
     */
    public String getBucketName(String bucketName) {
        return StrUtil.blankToDefault(bucketName, minioProperties.getBucketName());
    }

}
