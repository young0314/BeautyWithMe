package com.example.beautywithme.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(UserId.class)
public class User implements Serializable {
    @Id
    @Column(unique = true, columnDefinition = "VARCHAR(36)")
    private String userKey; // 가입자 고유 key ,UUID
    private LocalDateTime createDay;
    @Id
    @Column(unique = true, columnDefinition = "VARCHAR(40)")
    private String email; //가입자 이메일(아이디)

    private String password; //가입자 비밀번호
    @Column(columnDefinition = "VARCHAR(20)")
    private String name; //가입자 이름
    @Column(columnDefinition = "VARCHAR(20)")
    private String phone;
    @Column(columnDefinition = "VARCHAR(2)")
    private String gender;
    @Column(columnDefinition = "VARCHAR(10)")
    private String nickname;
    @Column(columnDefinition = "VARCHAR(10)")
    private String platformType; //sns플랫폼 타입
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private Date birth; //가입자 생년월일
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private Role role;

    @PrePersist //UUID생성
    public void generateUserKey() {
        if (this.userKey == null) {
            this.userKey = UUID.randomUUID().toString();
        }
        if (this.createDay == null) {
            this.createDay = LocalDateTime.now();
        }
    }

}
