package com.example.testtoy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (API 서버라면 대체로 비활성화)
                .csrf(csrf -> csrf.disable())
                // 요청에 대한 보안 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // 모든 경로 접근 허용 (테스트용)
                        .anyRequest().authenticated()
                )
                // 폼 로그인 비활성화
                .formLogin(form -> form.disable());

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
