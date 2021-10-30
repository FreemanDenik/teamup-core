package com.example.teamupexternalimpl.model.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MapResourceResult {

    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;

    @JsonProperty("formatted_address")
    public String formattedAddress;

    @JsonProperty("place_id")
    public String placeId;

    @JsonProperty("plus_code")
    public PlusCode plusCode;

    @JsonProperty("postcode_localities")
    public List<String> postcodeLocalities;
    public List<String> types;
    public Geometry geometry;

}