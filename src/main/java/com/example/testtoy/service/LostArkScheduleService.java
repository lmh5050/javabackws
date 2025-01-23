package com.example.testtoy.service;

import com.example.testtoy.dto.*;
import com.example.testtoy.repository.LostarkScheduleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.testtoy.repository.LostarkRepository;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class LostArkScheduleService {

    private final WebClient webClient;
    private final LostarkScheduleRepository lostarkScheduleRepository;

    // API 키 설정 (application.properties에서 설정)
    public LostArkScheduleService(@Value("${lostark.api.key}") String apiKey, com.example.testtoy.repository.LostarkRepository lostarkRepository, com.example.testtoy.repository.LostarkRepository lostarkRepository1, LostarkScheduleRepository lostarkScheduleRepository, LostarkScheduleRepository lostarkScheduleRepository1, LostarkScheduleRepository lostarkScheduleRepository2) {
        this.webClient = WebClient.builder()
                .baseUrl("https://developer-lostark.game.onstove.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "bearer " + apiKey) // API 키 추가
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.lostarkScheduleRepository = lostarkScheduleRepository;
    }

    public void updateDatabase() {
        // 1. 기존 데이터에서 대표 캐릭터들 가져오기
        List<CharacterInfoDto> characterInfoExist = lostarkScheduleRepository.getExistData();

        // 2. 각 캐릭터에 대해 비동기 API 호출 처리
        Flux.fromIterable(characterInfoExist)
                .flatMap(characterInfoDto ->
                        webClient.get()
                                .uri("/characters/" + characterInfoDto.getCharacterNameRepresent() + "/siblings")
                                .retrieve()
                                .bodyToFlux(CharacterInfoDto.class)
                                .filter(character -> {
                                    String itemMaxLevel = character.getItemMaxLevel().replace(",", "");
                                    try {
                                        return Double.parseDouble(itemMaxLevel) >= 1600;
                                    } catch (NumberFormatException e) {
                                        return false;
                                    }
                                })
                )
                .doOnNext(character -> {
                    // 각 캐릭터의 itemMaxLevel 등을 디버깅 출력
                    System.out.println("Character Name: " + character.getCharacterName());
                    System.out.println("Item Max Level: " + character.getItemMaxLevel());
                })
                .collectList()  // List<CharacterInfoDto>로 변경됨
                .doOnTerminate(() -> System.out.println("Batch update starting..."))
                .subscribe(filteredCharacterInfoList -> {
                    // 필터링된 캐릭터 정보 리스트를 리포지토리로 넘겨서 배치 업데이트
                    System.out.println("Filtered Character List Size: " + filteredCharacterInfoList.size());
                    System.out.println("Filtered Character List: " + filteredCharacterInfoList.get(0));
                    System.out.println("Filtered Character List: " + filteredCharacterInfoList.get(1));
                    System.out.println("Filtered Character List: " + filteredCharacterInfoList.get(2));
                    lostarkScheduleRepository.updateCharacterData(filteredCharacterInfoList);  // List<CharacterInfoDto> 전달
                });
    }
}