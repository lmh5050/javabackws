package com.example.testtoy.service;

import com.example.testtoy.entity.User;
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

    // 회원가입 메소드
    public User registerUser(User user) {
        // 사용자가 이미 존재하는지 확인
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("이미 존재하는 사용자명입니다.");
        }

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 사용자 정보 삽입
        userRepository.insertUser(user);

        return user;
    }
}