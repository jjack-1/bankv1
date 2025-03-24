package com.metacoding.bankv1.user;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        // 1. 동일 유저네임 확인
        User user = userRepository.findByUsername(joinDTO.getUsername());
        if (user != null) {
            // 2. 있으면, exception !오류는 exception으로 처리한다
            throw new RuntimeException("동일한 username이 있습니다");
        }
        // 3. 없으면 회원가입
        userRepository.save(joinDTO.getUsername(), joinDTO.getPassword(), joinDTO.getFullname());
    }
}
