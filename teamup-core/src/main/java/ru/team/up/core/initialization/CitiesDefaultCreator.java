package ru.team.up.core.initialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.City;
import ru.team.up.core.entity.CityForParse;
import ru.team.up.core.repositories.CityRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Transactional
public class CitiesDefaultCreator {

    private final CityRepository cityRepository;

    @Autowired
    public CitiesDefaultCreator(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Bean("CitiesDefaultCreator")
    public void citiesDefaultCreator() throws IOException {
        List <CityForParse> cities = new ObjectMapper().readValue(
                new File("teamup-core/src/main/resources/database/cities.json"),
                new TypeReference<List<CityForParse>>(){}
        );
        for (int id = 0; id < cities.size(); id++) {
            cityRepository.save(City.builder()
                    .id((long) id + 1)
                    .name(cities.get(id).name)
                    .subject(cities.get(id).subject)
                    .lat(cities.get(id).coords.lat)
                    .lon(cities.get(id).coords.lon)
                    .build());
        }
    }
}
