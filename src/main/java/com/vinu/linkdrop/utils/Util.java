package com.vinu.linkdrop.utils;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class Util {
    public static String generateCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }
}