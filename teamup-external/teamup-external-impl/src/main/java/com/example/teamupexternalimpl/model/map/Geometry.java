package com.example.teamupexternalimpl.model.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Geometry {

    private Bounds bounds;
    private Location location;
    public Viewport viewport;

    @JsonProperty("location_type")
    public String locationType;

}