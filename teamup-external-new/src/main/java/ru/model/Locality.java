package ru.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Locality {

    @JsonProperty("LocalityName")
    private String localityName;

    @JsonProperty("Thoroughfare")
    private Thoroughfare thoroughfare;

}
