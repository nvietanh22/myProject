package vn.lottefinance.pdms_core.service.external;

import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@Slf4j
public class MinioService {
    @Value("${minio.bucket.name}")
    private String bucketName;


    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

        @PostConstruct
    private void initializeBucket() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error initializing bucket: " + e.getMessage(), e);
        }
    }

    public String uploadFile(MultipartFile file, String fileName) {
        log.info("Start upload file : {} to minio", bucketName);
        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );
            log.info("Done upload file {} to minio", fileName);
            return fileName;
        } catch (Exception e) {
            log.error("Error upload file {} to minio with error: {}", fileName, e.getMessage());
            throw new RuntimeException("Error uploading file: " + e.getMessage(), e);
        }
    }

    public InputStream downloadFile(String objectName) {
        log.info("Start download file: {}", objectName);
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );

        } catch (Exception e) {
            log.error("Error downloadFile {} to minio with error: {}", objectName, e.getMessage());
            throw new RuntimeException("Error downloading file: " + e.getMessage(), e);
        }
    }

    public boolean deleteFile(String objectName) {
        log.info("Start deleteFile file: {}", objectName);

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );

            log.info("Done deleteFile {} to minio", objectName);
            return true;
        } catch (Exception e) {
            log.error("Error deleteFile {} to minio with error: {}", objectName, e.getMessage());
            throw new RuntimeException("Error deleting file: " + e.getMessage(), e);
        }
    }
}
