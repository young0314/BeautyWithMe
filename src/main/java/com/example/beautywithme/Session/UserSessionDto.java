package com.example.beautywithme.Session;

import com.example.beautywithme.User.Role;
import com.example.beautywithme.User.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserSessionDto implements Serializable {
    private String userKey;
    private String email;
    private String password;
    private String nickname;
    private Role role;

    public UserSessionDto(User user){
        this.userKey=user.getUserKey();
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.nickname=user.getNickname();
        this.role=user.getRole();
    }


}
