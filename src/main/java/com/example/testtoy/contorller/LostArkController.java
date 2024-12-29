package com.example.testtoy.contorller;

import com.example.testtoy.dto.CharacterInfoDto;
import com.example.testtoy.dto.RaidDataDto;
import com.example.testtoy.dto.RaidMatchDto;
import com.example.testtoy.service.LostArkApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
    @RequestMapping("/api/lostark")
    public class LostArkController {

    private final LostArkApiService lostArkApiService;

    @Autowired
    public LostArkController(LostArkApiService lostArkApiService) {
        this.lostArkApiService = lostArkApiService;
    }

    @GetMapping("/characters/{id}") // 로그인시 아이디로 파라미터 보내주고 그 파라미터 맞는 값 조회하는 api + 지금은 api통신인데 추후에 db에서 불러오는 형식으로 변경
    public List<CharacterInfoDto> getCharacterInfo(@PathVariable String id) {
        return lostArkApiService.getCharacterInfoList(id);
    }

    @PostMapping("/characters/{characterName}") // 인풋창에서 등록 눌리면 데이터 db에 등록되는 api
    public List<CharacterInfoDto> updateCharacterInfo(@PathVariable String characterName, @RequestBody Map<String, String> requestData) {
        String inputData = requestData.get("data");
        System.out.println("Character Name: " + characterName);
        System.out.println("Received Data: " + inputData);
        // 데이터 처리 후 응답
        return lostArkApiService.updateCharacterInfoList(inputData);
    }

    @GetMapping("/characters/raid") //레이드 종류 나타내는 api
    public List<RaidDataDto> getRaidData() {
        List<RaidDataDto> raidDataResult = lostArkApiService.getRaidData();
        return raidDataResult;
    }

    @PostMapping("/characters/raid") //레이드 등록 버튼 클릭시 레이드 등록되는 api
    public int insertRaidData(@RequestBody RaidDataDto requestData) {
        // 받은 데이터를 서비스로 전달하거나 DB에 저장
        int raidDataResult = lostArkApiService.insertRaidData(requestData);
        // 결과 반환
        return raidDataResult;
    }

    @DeleteMapping("/characters/raid/{raidName}") //레이드 테이블에서 값 삭제하는 api
    public int deleteRaidData(@PathVariable String raidName, @RequestBody Map<String, String> requestData) {
        String inputData = requestData.get("data");
        System.out.println("Raid Name: " + raidName);
        System.out.println("Received Data: " + inputData);
        lostArkApiService.deleteRaidData(inputData);  // 실제 레이드 데이터 삭제 로직
        return 0;  // 성공적인 삭제 후 반환
    }

    @GetMapping("/characters/raidMatch") //레이드 테이블에서 값 불러오는 api
    public List<RaidMatchDto> getRaidMatchInfo() {
        return lostArkApiService.getRaidMatchInfo();
    }

    @PostMapping("/characters/raidMatch")
    public int insertRaidMatch(@RequestBody RaidMatchDto requestData) {
        lostArkApiService.insertRaidMatchInfo(requestData);
        return 0;
    }



}




