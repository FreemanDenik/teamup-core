package ru.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoObjectCollection {

    @JsonProperty("metaDataProperty")
    private MetaDataProperty metaDataProperty;

    @JsonProperty("featureMember")
    private ArrayList<FeatureMember> featureMember;

}
