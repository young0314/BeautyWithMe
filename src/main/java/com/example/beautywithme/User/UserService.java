package com.example.beautywithme.User;

import com.example.beautywithme.Dto.LoginDto;
import com.example.beautywithme.Dto.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    /**
     * email 중복 체크
     * 회원가입 기능 구현 시 사용
     * 중복되면 true return
     */
    public boolean checkLoginIdDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }
    /**
     * nickname 중복 체크
     * 회원가입 기능 구현 시 사용
     * 중복되면 true return
     */
    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public String signup(SignupDto signupDto){
        signupDto.setPassword(encoder.encode(signupDto.getPassword()));

        return userRepository.save(signupDto.toEntity()).getEmail();
    }

    public boolean login(String email, String password) throws IllegalAccessException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        // 사용자 ID로 사용자 조회
        if (userOptional.isPresent()) {
            if (encoder.matches(password, userOptional.get().getPassword())) {
                return true; // 로그인 성공
            }
        }
        return false;
    }
    }

