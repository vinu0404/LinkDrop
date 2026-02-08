package com.vinu.linkdrop.service.impl;

import com.vinu.linkdrop.exceptionHandler.CodeExpiredException;
import com.vinu.linkdrop.exceptionHandler.FileStorageException;
import com.vinu.linkdrop.service.interfaces.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class DownloadServiceImpl implements DownloadService {

    private final RedisTemplate<String, String> redisTemplate;



    @Override
    public Path resolveFile(String code) {
        String filePath = redisTemplate.opsForValue().get(code);
        if (ObjectUtils.isEmpty(filePath)) {
            throw new CodeExpiredException("Link expired or invalid");
        }
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new FileStorageException("File not found on server");
        }
        return path;
    }



}
