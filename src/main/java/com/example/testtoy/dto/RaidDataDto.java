package com.example.testtoy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RaidDataDto {
    @JsonProperty("raidName")
    private String raidName;

    @JsonProperty("gold1")
    private String gold1;  // String으로 변경하거나, 숫자 변환 로직 추가

    @JsonProperty("gold2")
    private String gold2;  // String으로 변경

    @JsonProperty("gold3")
    private String gold3;  // String으로 변경

    @JsonProperty("reward1")
    private String reward1;

    @JsonProperty("plus1")
    private String plus1;

    @JsonProperty("plus2")
    private String plus2;

    @JsonProperty("plus3")
    private String plus3;

    private String raidLevel;
}
