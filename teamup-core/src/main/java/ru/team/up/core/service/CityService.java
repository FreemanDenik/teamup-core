package ru.team.up.core.service;

import ru.team.up.core.entity.City;

import java.util.List;

public interface CityService {

    City save(City city);

    City findCityByName(String name);

    City findCityByCoords(String lat, String lon);

    City findCityByNameAndSubject(String name, String subject);

    List <City> getAllCities();

    List<City> getSomeCitiesByName(String name);
}
