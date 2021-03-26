package com.cos.restapi.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String phone;

    @Builder
    public User(String username, String password, String phone) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public void update(String password, String phone) {

        if (password != null) {
            this.password = password;
        }
        if (phone != null){
            this.phone = phone;
        }
    }

    public void giveDatabaseId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
