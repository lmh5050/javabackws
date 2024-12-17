package com.example.testtoy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081/ , http://192.168.129.191:8081/")  // 요청을 허용할 출처 설정
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // OPTIONS 추가
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
