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
public class AdministrativeArea {

    @JsonProperty("AdministrativeAreaName")
    private String administrativeAreaName;

    @JsonProperty("Locality")
    private Locality locality;

}
