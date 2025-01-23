package com.example.testtoy.repository;
import com.example.testtoy.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LostarkScheduleRepository {
    List<CharacterInfoDto> getExistData();
    void updateCharacterData(List<CharacterInfoDto> data);  // 파라미터 타입 변경
}

