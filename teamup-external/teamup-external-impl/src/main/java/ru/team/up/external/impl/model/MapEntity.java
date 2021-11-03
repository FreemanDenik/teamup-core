package ru.team.up.external.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class MapEntity {

    @JsonProperty("plus_code")
    private PlusCode plusCode;

    @JsonProperty("results")
    public ArrayList<MapResourceResult> mapResourceResults;
    private String status;
}