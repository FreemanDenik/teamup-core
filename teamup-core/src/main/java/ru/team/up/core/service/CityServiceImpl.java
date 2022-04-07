package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.City;
import ru.team.up.core.repositories.CityRepository;
import ru.team.up.sup.service.ParameterService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    @Transactional
    public City save(City city) {
        log.debug("Сохранение города {} в БД", city);
        return cityRepository.save(city);
    }

    @Override
    @Transactional
    public City findCityByName(String name) {
        log.debug("Поиск города по имени {}", name);
        return cityRepository.findCityByName(name);
    }

    @Override
    @Transactional
    public City findCityByCoords(String lat, String lon) {
        log.debug("Поиск города по широте {} и долготе {}", lat, lon);
        return cityRepository.getCityByLatAndLon(lat, lon);
    }

    @Override
    @Transactional
    public City findCityByNameAndSubject(String name, String subject) {
        log.debug("Поиск города по имени {} и субъекту {}", name, subject);
        return cityRepository.getCityByNameAndSubject(name, subject);
    }

    @Override
    @Transactional
    public List<City> getAllCities() {
        log.debug("Поиск списка всех городов");
        return cityRepository.findAll();
    }

    @Override
    public List<City> getSomeCitiesByName(String name) {
        log.debug("Поиск списка городов по имени {}", name);
        return cityRepository.getSomeCitiesByName(name)
                .stream()
                .limit(ParameterService.countReturnCity.getValue())
                .collect(Collectors.toList());
    }
}
