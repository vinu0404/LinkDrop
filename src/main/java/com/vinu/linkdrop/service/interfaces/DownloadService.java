package com.vinu.linkdrop.service.interfaces;


import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public interface DownloadService {

    Path resolveFile(String code);
}
