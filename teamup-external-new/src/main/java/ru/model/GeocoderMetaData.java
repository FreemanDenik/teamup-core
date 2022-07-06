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
public class GeocoderMetaData {

    private String precision;

    private String text;

    private String kind;

    @JsonProperty("Address")
    private Address address;

    @JsonProperty("AddressDetails")
    private AddressDetails addressDetails;

}
