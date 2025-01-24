package com.example.testtoy.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeeklyCharacterUpdateDataDto {
    private String username;
    private List<RaidMatchCharacterDto> raids = new ArrayList<>();
}
