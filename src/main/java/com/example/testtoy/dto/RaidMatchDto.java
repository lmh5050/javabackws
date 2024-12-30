package com.example.testtoy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RaidMatchDto {

    @JsonProperty("raidName")
    private String raidName;

    @JsonProperty("characterName")
    private String characterName;

    @JsonProperty("time")
    private String time;

    @JsonProperty("text")
    private String text;

    @JsonProperty("no")
    private int no;

}
