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

import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.ordinaryroad.commons.minio.response.DownloadResponses;

import jakarta.validation.constraints.NotNull;
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

    private final MinioClient minioClient;
    public static final String METADATA_KEY_ORIGINAL_FILENAME = "original-filename";

    public void upload(@NotNull String bucketName, @NotNull String objectName, @NotNull String originalFilename, @NotNull InputStream inputStream) throws Exception {
        makeBucketIfNotExists(bucketName);
        HashMap<String, String> userMetadata = new HashMap<>(1);
        userMetadata.put(METADATA_KEY_ORIGINAL_FILENAME, originalFilename);
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .userMetadata(userMetadata)
                .stream(inputStream, inputStream.available(), -1)
                .build();
        minioClient.putObject(putObjectArgs);
    }

    public DownloadResponses download(@NotNull String bucketName, @NotNull String objectName) throws Exception {
        DownloadResponses downloadResponses = new DownloadResponses();
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        downloadResponses.setGetObjectResponse(minioClient.getObject(getObjectArgs));
        StatObjectArgs statObjectArgs = StatObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        downloadResponses.setStatObjectResponse(minioClient.statObject(statObjectArgs));
        return downloadResponses;
    }

    /**
     * 不存在则创建存储桶
     *
     * @param bucketName 桶名称
     * @throws Exception Exception
     */
    public void makeBucketIfNotExists(String bucketName) throws Exception {
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

}
