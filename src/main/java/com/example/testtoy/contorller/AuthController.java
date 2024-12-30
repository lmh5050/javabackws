package com.example.testtoy.contorller;

import com.example.testtoy.jwtToken.JwtTokenProvider;
import com.example.testtoy.dto.UserInfoDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserInfoDto request) {
        // 인증 로직 추가 (DB에서 사용자 확인 등)
        if ("testuser".equals(request.getUsername()) && "password".equals(request.getPassword())) {
            return jwtTokenProvider.createToken(request.getUsername(), "ROLE_USER");
        }
        throw new RuntimeException("Invalid credentials");
    }
}
