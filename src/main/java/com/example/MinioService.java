package com.example;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MinioService {

    private final MinioClient minioClient;
    private final String bucket;

    public MinioService(
            @Value("${minio.url}") String url,
            @Value("${minio.access-key}") String accessKey,
            @Value("${minio.secret-key}") String secretKey,
            @Value("${minio.bucket}") String bucket
    ) {
        this.minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
        this.bucket = bucket;
    }

    public String uploadBinary(MultipartFile file) throws Exception {
        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) {
            throw new IllegalArgumentException("Invalid file name");
        }

        String safeFileName = UUID.randomUUID() + "_" + originalName.replaceAll("\\s+", "_");

        System.out.println("📥 Uploading file: " + originalName + " as: " + safeFileName);
        System.out.println("📦 Target bucket: " + bucket);

        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            System.out.println("🆕 Bucket created: " + bucket);
        }

        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(safeFileName)
                            .stream(is, file.getSize(), -1)
                            .contentType(file.getContentType() != null ? file.getContentType() : "application/octet-stream")
                            .build()
            );
        }

        System.out.println("✅ Upload complete: " + safeFileName);

        // 🔁 Small delay to give MinIO time to index
        Thread.sleep(1000);

        return safeFileName;
    }

    public String getFileUrl(String fileName) throws Exception {
        int attempts = 0;
        while (attempts < 5) {
            try {
                System.out.println("🔍 Checking if file exists: " + fileName);

                // Check if file is present
                minioClient.statObject(
                        StatObjectArgs.builder()
                                .bucket(bucket)
                                .object(fileName)
                                .build()
                );

                // If found, generate URL
                String url = minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(bucket)
                                .object(fileName)
                                .expiry(1, TimeUnit.HOURS)
                                .build()
                );

                System.out.println("🌐 Presigned URL generated: " + url);
                return url;

            } catch (ErrorResponseException e) {
                if ("NoSuchKey".equals(e.errorResponse().code())) {
                    System.out.println("⌛ File not yet visible. Retrying... attempt " + (attempts + 1));
                    Thread.sleep(1000);
                    attempts++;
                } else {
                    throw e;
                }
            }
        }

        throw new RuntimeException("❌ File not found after multiple retries: " + fileName);
    }
}