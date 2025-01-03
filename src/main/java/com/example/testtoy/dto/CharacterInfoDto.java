package com.example.testtoy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CharacterInfoDto {
    @JsonProperty("ServerName")
    private String serverName;

    @JsonProperty("CharacterName")
    private String characterName;

    @JsonProperty("CharacterClassName")
    private String characterClassName;

    @JsonProperty("ItemMaxLevel")
    private String itemMaxLevel;

    private String classType;
    private String className;
    private String characterNameRepresent;
    private String representCharacterName;

    public void setRepresentCharacterName(String characterNameRepresent) {
        this.characterNameRepresent = characterNameRepresent;
    }

}
