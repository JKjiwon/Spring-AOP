package com.cos.restapi.web;


import com.cos.restapi.service.UserService;
import com.cos.restapi.web.dto.ResponseDto;
import com.cos.restapi.web.dto.UserJoinRequestDto;
import com.cos.restapi.web.dto.UserResponseDto;
import com.cos.restapi.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @GetMapping("/user")
    public ResponseDto<List<UserResponseDto>> findAllUsers() {
        return new ResponseDto<>(HttpStatus.OK.value(), userService.findAllUsers2()); // MessageConverter (javaObject -> json)
    }

    @GetMapping("/user/{id}")
    public ResponseDto<UserResponseDto> findUser(@PathVariable Long id) {
        return new ResponseDto<>(HttpStatus.OK.value(), userService.findUser2(id));
    }

    // x--www-form-urlencoded -> request.getParameter()
    // text/plain => @RequestBody 어노테이션
    // application/json => @RequestBody 어노테이션 + 오브젝트로 받기
    @CrossOrigin
    @PostMapping("/user")
    public ResponseDto<String> joinUser(@Valid @RequestBody UserJoinRequestDto userJoinRequestDto, BindingResult bindingResult) {
        // Validation 로직 => AOP로 처리
        // 저장 로직
        return new ResponseDto<>(HttpStatus.OK.value(), userService.joinUser(userJoinRequestDto)+"번 유저가 생성되었습니다.");
    }

    @DeleteMapping("/user/{id}")
    public ResponseDto<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseDto<>(HttpStatus.OK.value(), id + "번 유저가 삭제되었습니다.");
    }

    @PutMapping("/user/{id}")
    public ResponseDto<String> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequestDto updateReqDto, BindingResult bindingResult) {
        // Validation 로직 => AOP로 처리

        // 업데이트 로직
        return new ResponseDto<>(HttpStatus.OK.value(),
                userService.updateUser(id, updateReqDto) + "번 유저가 업데이트 되었습니다.");
    }
}
