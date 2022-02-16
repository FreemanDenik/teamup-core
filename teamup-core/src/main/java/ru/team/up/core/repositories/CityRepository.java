package ru.team.up.core.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.team.up.core.entity.City;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, String> {

    City findCityByName(String name);

    City getCityByLatAndLon(String lat, String lon);

    City getCityByNameAndSubject(String name, String subject);

    @Query("FROM City WHERE name LIKE %?1%")
    List<City> getSomeCitiesByName(String name);
}
