package com.vinu.linkdrop.exceptionHandler;

public class CodeExpiredException extends RuntimeException {
    public CodeExpiredException(String message) {
        super(message);
    }
}
