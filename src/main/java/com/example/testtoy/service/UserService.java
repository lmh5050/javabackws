package com.example.testtoy.service;

import com.example.testtoy.dto.UserInfoDto;
import com.example.testtoy.entity.User;
import com.example.testtoy.jwtToken.JwtTokenProvider;
import com.example.testtoy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    // 회원가입 메소드
    public User registerUser(User user) {
        // 사용자가 이미 존재하는지 확인
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("이미 존재하는 사용자명입니다.");
        }

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 사용자 정보 삽입 /테스트용 애드
        userRepository.insertUser(user);

        return user;
    }

    public String login(UserInfoDto request) {
        // DB에서 사용자 조회
        UserInfoDto user = userRepository.findByUser(request.getUsername());
        // 사용자 존재 여부와 비밀번호 확인
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // 비밀번호가 맞으면 토큰 생성
            String consJwtToken = jwtTokenProvider.createToken(user.getUsername(), "ROLE_USER");
            return consJwtToken;
        } else {
            // 사용자명 또는 비밀번호가 틀린 경우 예외 처리
            throw new RuntimeException("Invalid credentials");
        }
    }
}