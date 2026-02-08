package com.vinu.linkdrop.exceptionHandler;

import com.vinu.linkdrop.utils.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<Object>handleFileUploadError(FileStorageException ex){
        GenericResponse<Object> response = GenericResponse.builder()
                .status("FAILED")
                .message(ex.getMessage())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(CodeExpiredException.class)
    public ResponseEntity<GenericResponse<Object>> handleExpired(CodeExpiredException ex) {

        GenericResponse<Object> response = GenericResponse.builder()
                .status("FAILED")
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Object>> handleGeneral(Exception ex) {

        GenericResponse<Object> response = GenericResponse.builder()
                .status("FAILED")
                .message("Something went wrong")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
