package com.example.testtoy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CharacterRegisterDto {
    private String username;
    private String characterName;
}
