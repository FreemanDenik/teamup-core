package com.example.teamupexternalimpl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class GeoViewPort implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("northeast")
    private ViewPortNorthEast northEast;

    @JsonProperty("southwest")
    private ViewPortSouthWest southWest;
}
