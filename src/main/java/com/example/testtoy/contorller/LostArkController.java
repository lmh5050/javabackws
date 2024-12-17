package com.example.testtoy.contorller;

import com.example.testtoy.dto.CharacterInfoDto;
import com.example.testtoy.service.LostArkApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

        @GetMapping("/characters/{characterName}")
        public List<CharacterInfoDto> getCharacterInfo(@PathVariable String characterName) {
            return lostArkApiService.getCharacterInfoList(characterName);
        }

        @PostMapping("/characters/{characterName}")
        public ResponseEntity<String> updateCharacterInfo(@PathVariable String characterName, @RequestBody Map<String, String> requestData) {
            String inputData = requestData.get("data");
            System.out.println("Character Name: " + characterName);
            System.out.println("Received Data: " + inputData);

            // 데이터 처리 후 응답
            return ResponseEntity.ok("Character: " + characterName + " Data: " + inputData);
        }

    @GetMapping("/characters/test")
    public String test() {
        String result = lostArkApiService.test();
        System.out.println("Character Name: " +result);
        return result;
    }
    }



