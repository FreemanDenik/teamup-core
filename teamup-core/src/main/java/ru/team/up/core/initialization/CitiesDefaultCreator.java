package ru.team.up.core.initialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.City;
import ru.team.up.core.entity.CityForParse;
import ru.team.up.core.repositories.CityRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Transactional
@Profile("cdb")
public class CitiesDefaultCreator {

    private final CityRepository cityRepository;

    @Autowired
    public CitiesDefaultCreator(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Bean("cityDefaultCreator")
    @Async //не уверен, работает ли эта аннотация (@EnableAsync навешана в SchedulingConfig)
    public void citiesDefaultCreator() throws IOException {
        List <CityForParse> cities = new ObjectMapper().readValue(
                new File("teamup-core/src/main/resources/database/cities.json"),
                new TypeReference<>(){}
        );

        AtomicLong i = new AtomicLong(1);
        cities.forEach(city -> {
            cityRepository.save(City.builder()
                            .id(i.getAndIncrement())
                            .name(city.name)
                            .subject(city.subject)
                            .lat(city.coords.lat)
                            .lon(city.coords.lon)
                            .build());
        });
    }
}
