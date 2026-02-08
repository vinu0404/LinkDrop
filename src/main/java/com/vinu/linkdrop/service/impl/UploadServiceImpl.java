package com.vinu.linkdrop.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vinu.linkdrop.exceptionHandler.FileStorageException;
import com.vinu.linkdrop.service.interfaces.UploaderService;
import com.vinu.linkdrop.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploaderService {

    @Value("${backblaze.bucketName}")
    private String bucketName;

    private final RedisTemplate<String, String> redisTemplate;
    private final AmazonS3 amazonS3;

    @Override
    public String doUpload(MultipartFile file, Integer timeToLive) {
        String fileKey = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            PutObjectRequest putRequest = new PutObjectRequest(
                    bucketName,
                    fileKey,
                    file.getInputStream(),
                    metadata
            );

            amazonS3.putObject(putRequest);

        } catch (IOException e) {
            throw new FileStorageException("Failed to store file in cloud storage: " + e.getMessage());
        } catch (Exception e) {
            throw new FileStorageException("Failed to upload to Backblaze B2: " + e.getMessage());
        }
        String code = Util.generateCode();
        redisTemplate.opsForValue().set(
                code,
                fileKey,
                Duration.ofMinutes(Math.min(timeToLive, 15))
        );

        return code;
    }
}