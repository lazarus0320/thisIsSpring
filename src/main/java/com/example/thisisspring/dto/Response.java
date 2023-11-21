package com.example.thisisspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response<T> {
    private T data;
    private String message;
    // data만 오는 경우와 data, message 모두 오는 경우에 대해 커버
    public Response(String message) {
        this.message = message;
    }
}
