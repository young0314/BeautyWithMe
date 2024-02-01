package com.example.beautywithme.User;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

public class UserId implements Serializable {
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(unique = true, columnDefinition = "VARCHAR(36)")
    private String userKey;
    @Column(unique = true, columnDefinition = "VARCHAR(40)")
    private String email;

}
