package com.metacoding.bankv1.account;

import lombok.AllArgsConstructor;
import lombok.Data;

public class AccountResponse {

    @AllArgsConstructor
    @Data
    public static class DetailDTO {
        private Integer accountNumber;
        private Integer accountBalance;
        private String accountOwner;
        private String createdAt;
        private Integer wNumber;
        private Integer dNumber;
        private Integer amount;
        private Integer balance;
        private String type;
    }
}