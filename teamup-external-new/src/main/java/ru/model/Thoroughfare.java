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
public class Thoroughfare {

    @JsonProperty("ThoroughfareName")
    private String thoroughfareName;

    @JsonProperty("Premise")
    private Premise premise;

}
