package com.example.testtoy.repository;

import com.example.testtoy.dto.CharacterInfoDto;
import com.example.testtoy.dto.RaidDataDto;
import com.example.testtoy.dto.RaidMatchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LostarkRepository {
    List<RaidDataDto> getRaidData(); //레이드 데이터 조회

    int  insertRaidData(RaidDataDto raidInfo); //레이드 데이터 등록
    void deleteRaidData(String raidName); //레이드 데이터 삭제
    void insertCharacterData(List<CharacterInfoDto> sortedData); //캐릭터 정보 등록
    List<RaidMatchDto> getRaidMatchInfo();

}

