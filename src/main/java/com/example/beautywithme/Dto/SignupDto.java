package com.example.beautywithme.Dto;

import com.example.beautywithme.User.Role;
import com.example.beautywithme.User.User;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignupDto {
    private String userKey;
    private String email;
    private String name;
    private  String password;
    private String passwordCheck;
    private  String phone;
    private  String nickname;
    private Date birth;
    private String gender;
    private String platformType;
    private Role role; //USER권한 부여


    public User toEntity(){
        User user = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .birth(birth)
                .gender(gender)
                .phone(phone)
                .role(Role.USER)
                .build();

                return user;
    }

}
