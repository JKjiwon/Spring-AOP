package com.cos.restapi.config;

import com.cos.restapi.domain.ResponseDto;
import io.sentry.Sentry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


// @Component -> Controller 가 뜨고 나서
// @Configuration -> Controller 가 뜨기
@Component
@Aspect
public class BindingAdvice {

    public static final Logger log = LoggerFactory.getLogger(BindingAdvice.class);

    // Around의 proceedingJoinPoint.proceed(); 이 실행 되어야 @Before이 실행된다.
    // 즉, Around 코드 중 에러가 발생하면 @Before이 발생하지 않는다
    @Before("execution(* com.cos.restapi.web..*Controller.*(..))")
    public void testCheck() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        System.out.println("주소: " + request.getRequestURL());

        // request 값을 처리 못하나요?
        // log 처리는? 파일로 남겨야 하는데....
        System.out.println("로그를 남겼습니다.");
    }

    @After("execution(* com.cos.restapi.web..*Controller.*(..))")
    public void testCheck2() {
        System.out.println("후처리 로그를 남겼습니다.");
    }

    // 함수 : 앞 뒤
    // 함수 : 앞(username이 안들어 왔으면 내가 강제로 넣어주고 실행)
    // 함수 : 뒤
    @Around("execution(* com.cos.restapi.web..*Controller.*(..))")
    public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();

        System.out.println("type = " + type);
        System.out.println("method = " + method);

        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                // 서비스 : 정상적인 화면 -> 사용자 요청
                if (bindingResult.hasErrors()) {
                    Map<String, String> errMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errMap.put(error.getField(), error.getDefaultMessage());

                        // 로그 레벨 error, warn, info, debug
                        log.warn(type + "." + method + "() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
                        log.debug(type + "." + method + "() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());
                        Sentry.captureMessage(type + "." + method + "() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage());

                        // DB연결 -> DB남기기
                        // File file = new File();
                    }
                    return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), errMap);
                }
            }
        }
        return proceedingJoinPoint.proceed(); // 함수의 스택을 실행해라.
    }
}
