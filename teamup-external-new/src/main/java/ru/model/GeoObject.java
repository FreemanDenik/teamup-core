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
public class GeoObject {

    @JsonProperty("metaDataProperty")
    private MetaDataProperty2 metaDataProperty2;

    private String name;

    private String description;

    @JsonProperty("boundedBy")
    private BoundedBy boundedBy;

    @JsonProperty("Point")
    private Point point;

}
