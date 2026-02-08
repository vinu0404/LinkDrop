package com.vinu.linkdrop.service.impl;
import com.vinu.linkdrop.exceptionHandler.FileStorageException;
import com.vinu.linkdrop.repository.FilesRepository;
import com.vinu.linkdrop.service.interfaces.UploaderService;
import com.vinu.linkdrop.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploaderService {

    @Value("${files.data}")
    private String DIR;

    private final FilesRepository filesRepository;
    private final RedisTemplate<String, String> redisTemplate;



    @Override
    public String doUpload(MultipartFile file, Integer timeToLive){
        String name = UUID.randomUUID().toString();
        Path path = Paths.get(DIR, name + "_" + file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file");
        }
        String code = Util.generateCode();
        redisTemplate.opsForValue().set(code,path.toString(),
                Duration.ofMinutes(Math.min(timeToLive,15)));
        return code;
    }
}

