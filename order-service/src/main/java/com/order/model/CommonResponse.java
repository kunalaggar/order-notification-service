package com.order.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommonResponse<T> {

    private boolean success;
    private int statusCode;
    private String message;
    private T data;
    private String error;
    private LocalDateTime timestamp;

    public CommonResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public CommonResponse(boolean success, int statusCode, String message, T data, String error) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }
}

