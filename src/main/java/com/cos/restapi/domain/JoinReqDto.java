package com.cos.restapi.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class JoinReqDto {

    @NotBlank(message = "유저 네임을 입력하세요.")
    @Size(max = 20, message = "유저네임 길이를 초과했습니다. (20자 이내).")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 4, message = "비밀번호가 너무 짧습니다. (4자 이상)")
    private String password;

    private String phone;
}
