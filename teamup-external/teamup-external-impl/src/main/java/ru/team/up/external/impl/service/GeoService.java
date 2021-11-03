package ru.team.up.external.impl.service;

import ru.team.up.external.impl.model.MapEntity;

public interface GeoService {
    MapEntity getGeocode (String address);
    MapEntity getAddress (String code);

}
