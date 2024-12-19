package com.example.testtoy.repository;

import com.example.testtoy.dto.RaidDataDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LostarkRepository {
    List<RaidDataDto> getRaidData();
}

