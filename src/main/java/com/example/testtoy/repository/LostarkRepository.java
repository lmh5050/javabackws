package com.example.testtoy.repository;

import com.example.testtoy.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LostarkRepository {
    List<RaidDataDto> getRaidData(); //레이드 데이터 조회
    List<CharacterInfoDto> getCharacterInfoList(String username);
    int  insertRaidData(RaidDataDto raidInfo); //레이드 데이터 등록
    void deleteRaidData(String raidName); //레이드 데이터 삭제
    void insertCharacterData(Map<String, Object> sortedData); //캐릭터 정보 등록
    List<RaidMatchDto> getRaidMatchInfo();
    void insertRaidMatchInfo(RaidMatchDto sortedData);
    RaidMatchDto selectRaidMatchInfo(RaidMatchDto sortedData);
    void insertRaidMatchCharacterInfo(RaidMatchDto sortedData);
    List<RaidMatchCharacterDto> getRaidMatchCharacterInfo(int raidNumber);
    List<RaidApplyCharacterInfoDto> getRaidMatchApplyRaid(String id);

}

