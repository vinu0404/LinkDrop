package com.vinu.linkdrop.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

@Component
public class Util {

    @Value("${files.data}")
    private String DIR;

    @PostConstruct
    public void init() {
        File file = new File(DIR);
        if (!file.exists()) {
            file.mkdir();
            System.out.println("Directory created: " + DIR);
        } else {
            System.out.println("Directory already exists");
        }
    }

    public static String generateCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }
}
