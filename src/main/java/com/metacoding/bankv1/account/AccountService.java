package com.metacoding.bankv1.account;

import com.metacoding.bankv1.account.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;

    @Transactional
    public void 계좌생성(AccountRequest.SaveDTO saveDTO, Integer userId) {
        accountRepository.save(saveDTO.getNumber(), saveDTO.getPassword(), saveDTO.getBalance(), userId);
    }

    public List<Account> 나의계좌목록(Integer userId) {
        return accountRepository.findAllByUserId(userId);
    }

    @Transactional
    public void 계좌이체(AccountRequest.TransferDTO transferDTO, Integer userId) {
        // 1. 출금 계좌 조회
        Account withdrawAccount = accountRepository.findByNumber(transferDTO.getWithdrawNumber());
        // 1-1. 출금 계좌 없으면 예외
        if (withdrawAccount == null)
            throw new RuntimeException("해당 출금 계좌가 없습니다");

        // 2. 입금 계좌 조회
        Account depositAccount = accountRepository.findByNumber(transferDTO.getDepositNumber());
        // 2-1. 입급 계좌 없으면 예외
        if (depositAccount == null)
            throw new RuntimeException("해당 입금 계좌가 없습니다");

        // 3. 출금 계좌의 잔액 조회
        if (withdrawAccount.getBalance() < transferDTO.getAmount())
            throw new RuntimeException("출금 계좌 잔액: " + transferDTO.getAmount() + ", 이체 하려는 금액: " + transferDTO.getAmount());

        // 4. 출금 비밀번호 확인
        if (!(withdrawAccount.getPassword().equals(transferDTO.getWithdrawPassword())))
            throw new RuntimeException("출금 계좌 비밀번호가 틀렸습니다");

        // 5. 출금 계좌와 로그인 유저 동일성 확인(권한 체크)
        if (!(withdrawAccount.getUserId().equals(userId)))
            throw new RuntimeException("출금계좌의 권한이 없습니다");

        // 이체금액 음수이면 예외
        if (transferDTO.getAmount() < 0)
            throw new RuntimeException("음수 금액은 불가능 합니다");

        // 6. 출금 계좌 & 입금 계좌 업데이트
        // 6-1. 출금 계좌 업데이트
        int withdrawBalance = withdrawAccount.getBalance();
        withdrawBalance -= transferDTO.getAmount();
        accountRepository.updateByNumber(
                withdrawBalance,
                withdrawAccount.getPassword(),
                withdrawAccount.getNumber());
        // 6-2. 입금 계좌 업데이트
        int depositBalance = depositAccount.getBalance();
        depositBalance += transferDTO.getAmount();
        accountRepository.updateByNumber(
                depositBalance,
                depositAccount.getPassword(),
                depositAccount.getNumber());

        // 7. history table 저장
        historyRepository.save(
                transferDTO.getWithdrawNumber(),
                transferDTO.getDepositNumber(),
                transferDTO.getAmount(),
                withdrawBalance,
                depositBalance);
    }

    public List<AccountResponse.DetailDTO> 계좌상세보기(Integer number, String type, Integer sessionUserId) {
        // 1. 계좌 존재 확인
        Account account = accountRepository.findByNumber(number);
        if (account == null)
            throw new RuntimeException("해당 계좌가 없습니다");
        // 2. 계좌 주인 확인
        if (!(account.getUserId().equals(sessionUserId)))
            throw new RuntimeException("해당 계좌의 권한이 없습니다");
        // 3. 조회
        List<AccountResponse.DetailDTO> detailList = accountRepository.findAllByNumber(number, type);
        return detailList;
    }
}
