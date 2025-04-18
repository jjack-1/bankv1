package com.metacoding.bankv1.account;

import com.metacoding.bankv1.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final HttpSession session;

    @GetMapping("/")
    public String home() {
        return "/home";
    }

    @GetMapping("/account/save-form")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 인증 체크
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해 주세요");

        return "/account/save-form";
    }

    @PostMapping("/account/save")
    public String save(AccountRequest.SaveDTO saveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 인증 체크
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해 주세요");

        accountService.계좌생성(saveDTO, sessionUser.getId());

        return "redirect:/account";
    }

    @GetMapping("/account")
    public String list(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 인증 체크
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해 주세요");

        List<Account> accountList = accountService.나의계좌목록(sessionUser.getId());

        request.setAttribute("models", accountList);

        return "/account/list";
    }

    @GetMapping("/account/transfer-form")
    public String transferForm() {
        // 로그인 인증 -> 공통 부가 로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 인증 체크
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해 주세요");
        return "/account/transfer-form";
    }

    @PostMapping("/account/transfer")
    public String transfer(AccountRequest.TransferDTO transferDTO) {
        // 로그인 인증 -> 공통 부가 로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 인증 체크
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해 주세요");
        accountService.계좌이체(transferDTO, sessionUser.getId());
        return "redirect:/"; // TODO : 리다이렉트 주소 변경
    }

    // /account/1111?type=입금,출금,전체
    // pk나 유니크 값은 uri 주소로 받는게 약속이다
    @GetMapping("/account/{number}")
    public String detail(@PathVariable Integer number,
                         @RequestParam(value = "type", required = false, defaultValue = "전체") String type,
                         HttpServletRequest request) {
        // 로그인 인증 -> 공통 부가 로직
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 인증 체크
        if (sessionUser == null) throw new RuntimeException("로그인 후 사용해 주세요");

        // request.getParam("값") -> 쿼리스트링, xxx-formdata
        List<AccountResponse.DetailDTO> detailList = accountService.계좌상세보기(number, type, sessionUser.getId());
        request.setAttribute("models", detailList);
        return "/account/detail";
    }


}
