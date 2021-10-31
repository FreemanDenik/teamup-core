package com.example.teamupexternalimpl.service;

import com.example.teamupexternalimpl.model.MapEntity;

public interface GeoService {
    MapEntity getGeocode (String address);
    MapEntity getAddress (String code);

}
