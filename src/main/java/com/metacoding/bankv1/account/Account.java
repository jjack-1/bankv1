package com.metacoding.bankv1.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "account_tb")
@Getter
@NoArgsConstructor
public class Account {
    @Id
    private Integer number; // 계좌번호 pk
    private String password;
    private Integer balance; // 잔액
    private Integer userId; // fk
    private Timestamp createdAt;
}
