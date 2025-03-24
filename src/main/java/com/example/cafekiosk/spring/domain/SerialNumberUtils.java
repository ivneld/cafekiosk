package com.example.cafekiosk.spring.domain;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SerialNumberUtils {

    public static String generate() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
