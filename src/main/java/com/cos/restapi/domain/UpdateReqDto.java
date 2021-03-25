package com.cos.restapi.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UpdateReqDto {

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 4, message = "비밀번호가 너무 짧습니다. (4자 이상)")
    private String password;
    private String phone;
}
