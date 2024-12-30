package com.example.testtoy.service;
import com.example.testtoy.dto.RaidDataDto;
import com.example.testtoy.dto.RaidMatchCharacterDto;
import com.example.testtoy.dto.RaidMatchDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public LostArkApiService(@Value("${lostark.api.key}") String apiKey, com.example.testtoy.repository.LostarkRepository lostarkRepository, com.example.testtoy.repository.LostarkRepository lostarkRepository1) {
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
                .peek(character -> {
                    // classType을 설정
                    if (character.getCharacterClassName() != null) {
                        switch (character.getCharacterClassName()) {
                            case "도화가":
                            case "홀리나이트":
                            case "바드":
                                character.setClassType("서폿");
                                break;
                            default:
                                character.setClassType("딜러");
                                break;
                        }
                    }
                })
                .sorted((c1, c2) -> {
                    double maxLevel1 = Double.parseDouble(c1.getItemMaxLevel().replace(",", ""));
                    double maxLevel2 = Double.parseDouble(c2.getItemMaxLevel().replace(",", ""));
                    return Double.compare(maxLevel2, maxLevel1);  // 내림차순 정렬
                })
                .collect(Collectors.toList());  // 다시 List로 변환

        System.out.println(sortedCharacterInfo);
        return sortedCharacterInfo;
    }

    //캐릭터 정보 업데이트 하는 메소드 => (리턴값이 있는 이유 > 그냥 보여주기 위함)
    public List<CharacterInfoDto> updateCharacterInfoList(String characterName) {
        List<CharacterInfoDto> characterInfo = webClient.get()
                .uri("/characters/" + characterName + "/siblings")
                .retrieve()
                .bodyToFlux(CharacterInfoDto.class)
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
                .collectList()
                .block();

        List<CharacterInfoDto> sortedCharacterInfo = characterInfo.stream()
                .peek(character -> {
                    // classType을 설정
                    if (character.getCharacterClassName() != null) {
                        switch (character.getCharacterClassName()) {
                            case "도화가":
                            case "홀리나이트":
                            case "바드":
                                character.setClassType("서폿");
                                break;
                            default:
                                character.setClassType("딜러");
                                break;
                        }
                    }
                })
                .sorted((c1, c2) -> {
                    double maxLevel1 = Double.parseDouble(c1.getItemMaxLevel().replace(",", ""));
                    double maxLevel2 = Double.parseDouble(c2.getItemMaxLevel().replace(",", ""));
                    return Double.compare(maxLevel2, maxLevel1);  // 내림차순 정렬
                })
                .collect(Collectors.toList());

        // 제일 높은 레벨의 캐릭터 이름을 추출
        if (!sortedCharacterInfo.isEmpty()) {
            String representativeCharacterName = sortedCharacterInfo.get(0).getCharacterName();

            // 모든 캐릭터의 대표 캐릭터명에 첫 번째 캐릭터 이름을 설정
            sortedCharacterInfo.forEach(character -> character.setRepresentCharacterName(representativeCharacterName));
        }

        System.out.println(sortedCharacterInfo);
        LostarkRepository.insertCharacterData(sortedCharacterInfo);
        return sortedCharacterInfo;
    }

    //레이드 데이터 단순히 조회하는 메소드
    public List<RaidDataDto> getRaidData() {
        return LostarkRepository.getRaidData();
    }

    //레이드 데이터 DB에 insert 하는 메소드
    public int insertRaidData(RaidDataDto requestData) {
        return LostarkRepository.insertRaidData(requestData);
    }

    //레이드 데이터 delete 하는 메소드
    public void deleteRaidData(String raidName) {
        LostarkRepository.deleteRaidData(raidName);
    }

    //레이드 매칭정보 불러오는 메소드
    public List<RaidMatchDto> getRaidMatchInfo() {
        return LostarkRepository.getRaidMatchInfo();
    }

    @Transactional//레이드 데이터 DB에 insert 하는 메소드
    public void insertRaidMatchInfo(RaidMatchDto requestData) {
        LostarkRepository.insertRaidMatchInfo(requestData); // 데이터를 인서트 하는 코드
        RaidMatchDto selectedData = LostarkRepository.selectRaidMatchInfo(requestData);
        LostarkRepository.insertRaidMatchCharacterInfo(selectedData); // 가져온 데이터를 다시 다른 테이블에 넣어주는 구조
    }

    public List<RaidMatchCharacterDto> getRaidMatchCharacterInfo(String raidNo) {
        int raidNumber = Integer.parseInt(raidNo);
        System.out.println(raidNumber + "1");
        List<RaidMatchCharacterDto> debugTmp = LostarkRepository.getRaidMatchCharacterInfo(raidNumber);
        System.out.println(debugTmp + "2");
        return debugTmp;
    }
}
