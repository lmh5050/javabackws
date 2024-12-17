package com.example.testtoy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS 활성화
                .cors(cors -> cors.disable())
                // CSRF 비활성화
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

    // CORS 필터 설정 추가
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 허용할 출처 설정
        config.setAllowedOrigins(Arrays.asList("http://localhost:8081", "http://192.168.129.191:8081"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true); // 쿠키/인증정보 허용

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
