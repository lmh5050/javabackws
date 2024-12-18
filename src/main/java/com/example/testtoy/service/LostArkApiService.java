package com.example.testtoy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.testtoy.dto.CharacterInfoDto;
import com.example.testtoy.repository.LostarkRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class LostArkApiService {

    private final WebClient webClient;
    private final LostarkRepository LostarkRepository;

    // API 키 설정 (application.properties에서 설정)
    public LostArkApiService(@Value("${lostark.api.key}") String apiKey, com.example.testtoy.repository.LostarkRepository lostarkRepository) {
        this.webClient = WebClient.builder()
                .baseUrl("https://developer-lostark.game.onstove.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "bearer " + apiKey) // API 키 추가
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        LostarkRepository = lostarkRepository;
    }

    // 캐릭터 정보 가져오기
    public List<CharacterInfoDto> getCharacterInfoList(String characterName) {
        List<CharacterInfoDto> characterInfo = webClient.get()
                                                        .uri("/characters/" + characterName + "/siblings")
                                                        .retrieve()
                                                        .bodyToFlux(CharacterInfoDto.class)  // 응답을 DTO 리스트로 변환
                                                        .filter(character -> {
                                                            // ITEMMAXLEVEL의 쉼표 제거 후 숫자로 변환하여 비교
                                                            String itemMaxLevel = character.getItemMaxLevel().replace(",", "");
                                                            try {
                                                                return Double.parseDouble(itemMaxLevel) >= 1600;
                                                            } catch (NumberFormatException e) {
                                                                // 숫자 변환 오류 발생 시 제외
                                                                return false;
                                                            }
                                                        })
                                                        .collectList()  // Flux를 List로 변환
                                                        .block(); // 동기식으로 결과 반환
        List<CharacterInfoDto> sortedCharacterInfo = characterInfo.stream()
                .sorted((c1, c2) -> {
                    double maxLevel1 = Double.parseDouble(c1.getItemMaxLevel().replace(",", ""));
                    double maxLevel2 = Double.parseDouble(c2.getItemMaxLevel().replace(",", ""));
                    return Double.compare(maxLevel2, maxLevel1);  // 내림차순 정렬
                })
                .collect(Collectors.toList());  // 다시 List로 변환
        System.out.println(sortedCharacterInfo);
        return sortedCharacterInfo ;
    }

    public String test() {
        String result = LostarkRepository.test();
        return result;
    }
}
