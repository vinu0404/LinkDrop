package com.vinu.linkdrop.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.vinu.linkdrop.exceptionHandler.CodeExpiredException;
import com.vinu.linkdrop.exceptionHandler.FileStorageException;
import com.vinu.linkdrop.service.interfaces.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class DownloadServiceImpl implements DownloadService {

    @Value("${backblaze.bucketName}")
    private String bucketName;

    private final RedisTemplate<String, String> redisTemplate;
    private final AmazonS3 amazonS3;

    @Override
    public InputStream resolveFile(String code) {
        String fileKey = redisTemplate.opsForValue().get(code);

        if (ObjectUtils.isEmpty(fileKey)) {
            throw new CodeExpiredException("Link expired or invalid");
        }

        try {
            if (!amazonS3.doesObjectExist(bucketName, fileKey)) {
                throw new FileStorageException("File not found in cloud storage");
            }

            S3Object s3Object = amazonS3.getObject(bucketName, fileKey);
            return s3Object.getObjectContent();

        } catch (Exception e) {
            throw new FileStorageException("Failed to retrieve file from cloud storage: " + e.getMessage());
        }
    }


    public String getFileName(String code) {
        String fileKey = redisTemplate.opsForValue().get(code);
        if (ObjectUtils.isEmpty(fileKey)) {
            throw new CodeExpiredException("Link expired or invalid");
        }
        int underscoreIndex = fileKey.indexOf('_');
        if (underscoreIndex > 0 && underscoreIndex < fileKey.length() - 1) {
            return fileKey.substring(underscoreIndex + 1);
        }
        return fileKey;
    }
}