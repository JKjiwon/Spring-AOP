package com.cos.restapi.config;

import com.cos.restapi.web.dto.ResponseDto;
import io.sentry.Sentry;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

//Exception을 전역으로 낚아채기
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseDto<String> handleUserNotFoundException(UserNotFoundException e) {
        // Sentry.io 에 로그 남기기
        Sentry.captureException(e);
        return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
