package com.metacoding.bankv1.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        // 1. 동일 유저네임 확인
        User user = userRepository.findByUsername(joinDTO.getUsername());

        // 2. 있으면, exception !오류는 exception으로 처리한다
        if (user != null) {
            throw new RuntimeException("동일한 username이 있습니다");
        }

        // 3. 없으면 회원가입
        userRepository.save(joinDTO.getUsername(), joinDTO.getPassword(), joinDTO.getFullname());
    }

    public User 로그인(UserRequest.LoginDTO loginDTO) {
        // 1. 해당 username이 있는가?
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 2. 필터링(username 유무 확인, password 동일 확인)
        if (user == null) {
            throw new RuntimeException("해당 username이 없습니다");
        }
        if (!(user.getPassword().equals(loginDTO.getPassword()))) {
            throw new RuntimeException("해당 password가 틀렸습니다");
        }

        // 3. 인증
        return user;
    }
}
