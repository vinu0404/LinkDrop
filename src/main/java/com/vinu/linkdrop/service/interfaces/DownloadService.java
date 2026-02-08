package com.vinu.linkdrop.service.interfaces;

import java.io.InputStream;

public interface DownloadService {
    InputStream resolveFile(String code);
    String getFileName(String code);
}