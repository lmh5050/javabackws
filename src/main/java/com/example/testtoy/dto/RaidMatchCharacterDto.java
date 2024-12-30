package com.example.testtoy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RaidMatchCharacterDto {

    @JsonProperty("characterName")
    private String characterName;  // String으로 변경하거나, 숫자 변환 로직 추가
    @JsonProperty("itemMaxLevel")
    private String itemMaxLevel;  // String으로 변경
    @JsonProperty("className")
    private String className;  // String으로 변경
    @JsonProperty("classType")
    private String classType;
}
