package com.metacoding.bankv1.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "user_tb")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 12) // 제약조건 거는 법
    private String username; // 유저 아이디

    @Column(nullable = false, length = 12)
    private String password;

    @Column(nullable = false)
    private String fullname;

    private Timestamp createdAt; // 생성 날짜(insert 시점)
}