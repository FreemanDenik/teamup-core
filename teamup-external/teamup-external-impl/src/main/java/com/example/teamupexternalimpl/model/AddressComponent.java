package com.example.teamupexternalimpl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddressComponent {

    @JsonProperty("long_name")
    public String longName;

    @JsonProperty("short_name")
    public String shortName;

    public List<String> types;
}