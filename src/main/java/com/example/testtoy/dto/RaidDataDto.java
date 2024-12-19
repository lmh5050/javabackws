package com.example.testtoy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RaidDataDto {
    @JsonProperty("raidName")
    private String raidName;

    @JsonProperty("gold1")
    private int gold1;

    @JsonProperty("gold2")
    private int gold2;

    @JsonProperty("gold3")
    private int gold3;

    @JsonProperty("reward1")
    private String reward1;

    @JsonProperty("reward2")
    private String reward2;

    @JsonProperty("reward3")
    private String reward3;

    @JsonProperty("plus1")
    private String plus1;

    @JsonProperty("plus2")
    private String plus2;

    @JsonProperty("plus3")
    private String plus3;
}
