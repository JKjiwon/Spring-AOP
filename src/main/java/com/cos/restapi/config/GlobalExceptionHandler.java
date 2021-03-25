package com.cos.restapi.config;

import com.cos.restapi.domain.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

//Exception을 전역으로 낚아채기
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = DatabaseNotFoundException.class)
    public ResponseDto<String> handleDatabaseNotFoundException(DatabaseNotFoundException e) {
        return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
