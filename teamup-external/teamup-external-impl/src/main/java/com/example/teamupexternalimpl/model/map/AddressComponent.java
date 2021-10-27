package com.example.teamupexternalimpl.model.map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddressComponent {

    public String long_name;
    public String short_name;
    public List<String> types;

}
