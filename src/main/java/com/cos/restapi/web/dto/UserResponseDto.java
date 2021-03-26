package com.cos.restapi.web.dto;

import com.cos.restapi.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private String phone;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.phone = user.getPhone();
    }
}
