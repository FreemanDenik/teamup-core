package com.example.teamupexternalimpl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class GeoLocation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lng")
    private String longitude;
}
