package com.vinu.linkdrop.controllers;


import com.vinu.linkdrop.service.interfaces.DownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class DownloadController {

    private final DownloadService downloadService;


    @GetMapping("download/{code}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String code) throws IOException {
        Path path = downloadService.resolveFile(code);
        InputStreamResource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + path.getFileName().toString() + "\"")
                .body(resource);
    }

}

