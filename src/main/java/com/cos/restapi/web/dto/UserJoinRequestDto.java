package com.cos.restapi.web.dto;

import com.cos.restapi.domain.User;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class UserJoinRequestDto {

    @NotBlank(message = "아이디를 입력하세요.")
    @Size(max = 20, message = "아이디의 길이를 초과했습니다. (20자 이내)")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 4, message = "비밀번호가 너무 짧습니다. (4자 이상)")
    private String password;

    private String phone;

    public User toEntity(){
        return User.builder()
                .username(username)
                .password(password)
                .phone(phone)
                .build();
    }

    @Override
    public String toString() {
        return "UserJoinRequestDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
