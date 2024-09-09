package com.js.bookforum.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;

    public S3Service(@Value("${aws.accessKeyId}") String accessKeyId,
                     @Value("${aws.secretKey}") String secretAccessKey,
                     @Value("${aws.s3.bucketName}") String bucketName,
                     @Value("${aws.region}") String region) {

        this.bucketName = bucketName;

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String key = "images/" + file.getOriginalFilename(); // S3에서의 파일 경로
        Path tempFile = Files.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(tempFile);

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromFile(tempFile)); // 파일을 RequestBody로 감싸서 업로드

            return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toExternalForm();
        } finally {
            Files.deleteIfExists(tempFile); // 임시 파일 삭제
        }
    }
}