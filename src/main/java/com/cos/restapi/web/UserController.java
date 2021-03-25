package com.cos.restapi.web;


import com.cos.restapi.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserRepository userRepository;

    @GetMapping("/user")
    public ResponseDto<List<User>> findAll() {
        return new ResponseDto<>(HttpStatus.OK.value(), userRepository.findAll()); // MessageConverter (javaObject -> json)
    }

    @GetMapping("/user/{id}")
    public ResponseDto<User> findById(@PathVariable int id) {
        return new ResponseDto<>(HttpStatus.OK.value(), userRepository.findById(id));
    }

    // x--www-form-urlencoded -> request.getParameter()
    // text/plain => @RequestBody 어노테이션
    // application/json => @RequestBody 어노테이션 + 오브젝트로 받기
    @CrossOrigin
    @PostMapping("/user")
    public ResponseDto<User> save(@Valid @RequestBody JoinReqDto joinReqDto, BindingResult bindingResult) {
        // Validation 로직 => AOP로 처리
//        if (bindingResult.hasErrors()) {
//            Map<String, String> errMap = new HashMap<>();
//            for (FieldError error : bindingResult.getFieldErrors()) {
//                errMap.put(error.getField(), error.getDefaultMessage());
//            }
//            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), errMap);
//        }
        System.out.println("UserController.save");
        // 저장 로직
        User user = new User();
        user.setUsername(joinReqDto.getUsername());
        user.setPassword(joinReqDto.getPassword());
        user.setPhone(joinReqDto.getPhone());
        User savedUser = userRepository.save(user);
        return new ResponseDto<>(HttpStatus.OK.value(), savedUser);
    }

    @DeleteMapping("/user/{id}")
    public ResponseDto<String> delete(@PathVariable int id) {
        userRepository.deleteById(id);
        return new ResponseDto<>(HttpStatus.OK.value(), id + "번 유저가 삭제되었습니다.");
    }

    @PutMapping("/user/{id}")
    public ResponseDto<User> update(@PathVariable int id, @Valid @RequestBody UpdateReqDto updateReqDto, BindingResult bindingResult) {

        // Validation 로직 => AOP로 처리

        // 업데이트 로직
        User user = userRepository.findById(id);

        if (updateReqDto.getPassword() != null) {
            user.setPassword(updateReqDto.getPassword());
        }
        if (updateReqDto.getPhone() != null) {
            user.setPhone(updateReqDto.getPhone());
        }
        User updatedUser = userRepository.save(user);

        return new ResponseDto<>(HttpStatus.OK.value(), updatedUser);
    }
}
