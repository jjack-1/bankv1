package com.metacoding.bankv1.account.history;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "history_tb")
@Getter
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer withdrawNumber; // 1111 fk
    private Integer depositNumber; // 2222 fk
    private Integer amount;
    private Integer withdrawBalance; // 그 시점의 잔액
    private Timestamp createdAt;
}
