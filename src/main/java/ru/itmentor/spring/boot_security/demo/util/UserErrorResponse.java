package ru.itmentor.spring.boot_security.demo.util;

import lombok.Data;

@Data
public class UserErrorResponse {
    private String errorMessage;
    private long timestamp;

    public UserErrorResponse(String errorMessage, long timestamp) {
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }
}
