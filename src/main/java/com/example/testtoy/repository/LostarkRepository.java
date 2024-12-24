package com.example.testtoy.repository;

import com.example.testtoy.dto.CharacterInfoDto;
import com.example.testtoy.dto.RaidDataDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LostarkRepository {
    List<RaidDataDto> getRaidData();

    int  insertRaidData(RaidDataDto raidInfo);
    void deleteRaidData(String raidName);
    void insertCharacterData(List<CharacterInfoDto> sortedData);
}

