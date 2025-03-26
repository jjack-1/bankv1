package com.metacoding.bankv1.account.history;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HistoryRepository {
    private final EntityManager em;

    public void save(Integer withdrawNumber, Integer depositNumber, Integer amount, Integer withdrawBalance, Integer depositBalance) {
        Query query = em.createNativeQuery("insert into history_tb (withdraw_number, deposit_number, amount, withdraw_balance, deposit_balance, created_at) values (?, ?, ?, ?, ?, now())");
        query.setParameter(1, withdrawNumber);
        query.setParameter(2, depositNumber);
        query.setParameter(3, amount);
        query.setParameter(4, withdrawBalance);
        query.setParameter(5, depositBalance);
        query.executeUpdate();
    }
}
