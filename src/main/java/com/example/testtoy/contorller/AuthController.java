package com.example.testtoy.contorller;

import com.example.testtoy.jwtToken.JwtTokenProvider;
import com.example.testtoy.dto.UserInfoDto;
import com.example.testtoy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthController(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserInfoDto request) {
        System.out.println("Login request received: " + request);
        try {
            // 서비스 계층에서 로그인 처리 후 JWT 토큰 반환
            String token = userService.login(request);
            return ResponseEntity.ok(token); // 성공적으로 토큰 반환
        } catch (RuntimeException e) {
            // 예외가 발생하면 400 Bad Request로 응답
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
