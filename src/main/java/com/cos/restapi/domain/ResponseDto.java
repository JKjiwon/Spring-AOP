package com.cos.restapi.domain;

import lombok.Data;

@Data
public class ResponseDto<T> {
    private int statusCode;
    private T data;

    public ResponseDto(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public ResponseDto(int statusCode) {
        this.statusCode = statusCode;
    }
}
