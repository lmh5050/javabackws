package com.example.testtoy.repository;

import com.example.testtoy.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository{
        // 사용자 정보 조회 (username 기준)
    Optional<User> findByUsername(String username);
    // 사용자 정보 삽입
    void insertUser(User user);
}
