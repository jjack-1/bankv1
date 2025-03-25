package com.metacoding.bankv1.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpSession session;


    @GetMapping("/join-form")
    public String joinForm() {
        return "/user/join-form";
    }


    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        userService.회원가입(joinDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "/user/login-form";
    }

    // request와 session의 스코프 = 얼마동안 살아있는가? session이 더 오래 살아 있다
    // PostMapping인 이유(예외) 비번을 body로 받아야 하니까
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser); // stateful 서버에 상태를 저장
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // 세션에 있는 정보 전부 제거
        return "redirect:/";
    }
}
