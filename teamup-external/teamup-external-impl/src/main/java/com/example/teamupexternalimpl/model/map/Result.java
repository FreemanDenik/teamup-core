package com.example.teamupexternalimpl.model.map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Result {

    private List<AddressComponent> address_components;
    public String formatted_address;
    public Geometry geometry;
    public String place_id;
    public PlusCode plus_code;
    public List<String> types;
    public List<String> postcode_localities;

}
