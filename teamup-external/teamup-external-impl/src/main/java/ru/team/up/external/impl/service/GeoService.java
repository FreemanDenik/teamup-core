package ru.team.up.external.impl.service;

import org.springframework.stereotype.Service;
import ru.team.up.external.impl.model.MapEntity;
@Service
public interface GeoService {
    MapEntity getGeocode (String address);
    MapEntity getAddress (String code);

}
