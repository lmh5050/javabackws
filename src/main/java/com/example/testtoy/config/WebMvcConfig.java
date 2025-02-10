package com.example.testtoy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:8081",
                        "http://192.168.129.191:8081",
                        "http://34.64.68.37:80",
                        "http://localhost:3000",
                        "http://localhost:81",
                        "http://localhost:8080",
                        "http://34.47.90.90:3000",
                        "http://34.47.90.90:80",
                        "http://34.47.90.90:8081",
                        "http://10.178.0.2:8081",
                        "http://10.178.0.2:8080",
                        "http://10.178.0.2:80",
                        "http://10.178.0.2:81"


                )  // 요청을 허용할 출처 설정
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메소드 설정
                .allowedHeaders("*")  // 모든 헤더 허용
                .allowCredentials(true);  // 자격 증명 허용 (쿠키 등)
    }
}
