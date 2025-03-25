package com.metacoding.bankv1.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public void 계좌생성(AccountRequest.SaveDTO saveDTO, Integer userId) {
        accountRepository.save(saveDTO.getNumber(), saveDTO.getPassword(), saveDTO.getBalance(), userId);
    }
}
