package com.example.teamupexternalimpl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@JsonRootName("results")
public class GeoObject implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("place_id")
    private String placeId;

    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;

    @JsonProperty("formatted_address")
    private String formattedAddress;

    @JsonProperty("geometry")
    private GeoGeometry geoGeometry;

    @JsonProperty("plus_code")
    private GeoPlusCode plusCode;

    @JsonProperty("types")
    private List<String> types;
}
