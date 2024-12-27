package com.example.testtoy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RaidMatchDto {

    @JsonProperty("raidName")
    private String raidName;

    @JsonProperty("characterNameRepresent")
    private String characterNameRepresent;  // String으로 변경하거나, 숫자 변환 로직 추가

    @JsonProperty("time")
    private String time;  // String으로 변경

    @JsonProperty("text")
    private String text;  // String으로 변경

}
