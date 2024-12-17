package com.example.testtoy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.testtoy.repository")  // MyBatis 매퍼 인터페이스가 있는 패키지 지정
public class TesttoyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TesttoyApplication.class, args);
    }
}

