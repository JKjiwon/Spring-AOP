package com.cos.restapi.web.dto;

import lombok.Getter;

@Getter
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
