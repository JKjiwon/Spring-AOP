package com.cos.restapi.config;


import com.cos.restapi.web.dto.ResponseDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class BindingAdvice {

    public static final Logger log = LoggerFactory.getLogger(BindingAdvice.class);


    @Around("execution(* com.cos.restapi.web.*Controller.*(..))")
    public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName(); // 클래스명
        String method = proceedingJoinPoint.getSignature().getName(); // 함수명
        Object[] args = proceedingJoinPoint.getArgs(); // 함수의 파라메터의 값(인스턴스)

        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errMap.put(error.getField(), error.getDefaultMessage());


                        // 로그 레벨 error, warn, info, debug
                        log.warn(type + "." + method + "() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
                        log.debug(type + "." + method + "() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
                        // Sentry.io 에 로그 남기기
//                        Sentry.captureMessage(type + "." + method + "() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
                    }
                    return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), errMap);
                }
            }
        }
        return proceedingJoinPoint.proceed(); // 함수의 스텍을 실행하라.
    }
}
