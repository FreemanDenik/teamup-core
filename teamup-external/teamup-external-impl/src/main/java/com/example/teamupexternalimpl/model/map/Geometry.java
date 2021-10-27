package com.example.teamupexternalimpl.model.map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Geometry {

    private Bounds bounds;
    private Location location;
    public String location_type;
    public Viewport viewport;

}
