package com.vinu.linkdrop.service.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploaderService {

    String doUpload(MultipartFile file, Integer timeToLive);
}
