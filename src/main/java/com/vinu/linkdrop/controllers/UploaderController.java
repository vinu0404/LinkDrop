package com.vinu.linkdrop.controllers;

import com.vinu.linkdrop.service.interfaces.UploaderService;
import com.vinu.linkdrop.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class UploaderController {

    private final UploaderService uploaderService;

    @PostMapping("/upload")
    public ResponseEntity<GenericResponse<String>> upload(
            @RequestParam("file") MultipartFile uploadedFile,
            @RequestParam("ttl") Integer timeToLive) {

        String code = uploaderService.doUpload(uploadedFile, timeToLive);

        GenericResponse<String> response = GenericResponse.<String>builder()
                .status("SUCCESS")
                .message("Code generated successfully")
                .data(code)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
