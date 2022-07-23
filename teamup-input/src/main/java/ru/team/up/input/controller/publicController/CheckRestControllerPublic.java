package ru.team.up.input.controller.publicController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.City;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.repositories.AccountRepository;
import ru.team.up.core.service.AccountService;
import ru.team.up.core.service.CityService;
import ru.team.up.dto.ControlDto;
import ru.team.up.dto.ParametersDto;
import ru.team.up.input.service.UserServiceRest;
import ru.team.up.sup.service.ParameterService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Tag(name = "Check Public Controller", description = "Check API")
@RestController
@RequestMapping(value = "api/public/check")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CheckRestControllerPublic {

    private CityService cityService;
    private UserServiceRest userService;
    private MonitorProducerService monitorProducerService;
    private AccountService accountService;

    public CheckRestControllerPublic(CityService cityService, UserServiceRest userService, MonitorProducerService monitorProducerService) {
    }

    @Operation(summary = "Поиск города по названию")
    @GetMapping("/city/one/{name}")
    public ResponseEntity<City> getCityByName(@PathVariable("name") String name) {
        log.debug("Получен запрос на город {}", name);

        if (!ParameterService.getCityByNameEnabled.getValue()) {
            log.debug("Метод getCityByNameEnabled выключен параметром getCityByNameEnabled = false");
            throw new RuntimeException("Method findEventById is disabled by parameter getCityByNameEnabled");
        }

        Optional<City> optionalCity = Optional.ofNullable(cityService.findCityByName(name));

        return optionalCity
                .map(city -> {
                    log.debug("Город с названием: {} найден", name);

                    Map<String, ParametersDto> monitoringParameters = new HashMap<>();

                    ParametersDto cityName = ParametersDto.builder()
                            .description("Название города ")
                            .value(name)
                            .build();
                    monitoringParameters.put("Название города", cityName);

                    monitorProducerService.send(
                            monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                                    ControlDto.MANUAL,
                                    this.getClass(),
                                    monitoringParameters)
                    );
                    return new ResponseEntity<>(city, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.error("Город с названием: {} не найден", name);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                });
    }

    @Operation(summary = "Поиск города по названию")
    @GetMapping("/city/{name}/{subject}")
    public ResponseEntity<City> getCityByNameAndSubject(@PathVariable("name") String name,
                                                        @PathVariable("subject") String subject) {
        log.debug("Получен запрос на город {} в субъекте {}", name, subject);
        if (!ParameterService.getCityByNameInSubjectEnabled.getValue()) {
            log.debug("Метод getCityByNameAndSubject выключен параметром getCityByNameInSubjectEnabled = false");
            throw new RuntimeException("Method findEventById is disabled by parameter getCityByNameInSubjectEnabled");
        }

        Optional<City> optionalCity = Optional.ofNullable(cityService.findCityByNameAndSubject(name, subject));

        return optionalCity
                .map(city -> {
                    log.debug("Город с названием: {} и субъектом {} найден", name, subject);

                    Map<String, ParametersDto> monitoringParameters = new HashMap<>();

                    ParametersDto cityName = ParametersDto.builder()
                            .description("Название города ")
                            .value(name)
                            .build();
                    monitoringParameters.put("Название города", cityName);

                    monitorProducerService.send(
                            monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                                    ControlDto.MANUAL,
                                    this.getClass(),
                                    monitoringParameters)
                    );
                    return new ResponseEntity<>(city, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.debug("Город с названием: {} и субъектом {} не найден", name, subject);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                });
    }

    @Operation(summary = "Получение списка всех городов")
    @GetMapping("/city")
    public ResponseEntity<List<City>> getAllCities() {
        log.debug("Получен запрос на список городов");
        if (!ParameterService.getAllCitiesEnabled.getValue()) {
            log.debug("Метод getAllCities выключен параметром getAllCitiesEnabled = false");
            throw new RuntimeException("Method getAllCities is disabled by parameter getAllCitiesEnabled");
        }
        List<City> cities = cityService.getAllCities();

        if (cities.isEmpty()) {
            log.error("Список городов пуст");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.debug("Список городов получен");

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto countCity = ParametersDto.builder()
                .description("Количество городов ")
                .value(cities.size())
                .build();
        monitoringParameters.put("Количество городов ", countCity);

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @Operation(summary = "Получение списка 10 городов, подходящих по имени")
    @GetMapping("/city/{name}")
    public ResponseEntity<List<City>> getSomeCitiesByName(@PathVariable("name") String name) {
        log.debug("Получен запрос на список городов по имени {}", name);
        if (!ParameterService.getSomeCitiesByNameEnabled.getValue()) {
            log.debug("Метод getSomeCitiesByName выключен параметром getSomeCitiesByNameEnabled = false");
            throw new RuntimeException("Method getSomeCitiesByName is disabled by parameter getSomeCitiesByNameEnabled");
        }
        List<City> cities = cityService.getSomeCitiesByName(name);

        if (cities.isEmpty()) {
            log.error("Список городов пуст");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.debug("Список городов получен");

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto listCity = ParametersDto.builder()
                .description("10 городов по имени")
                .value(name)
                .build();
        monitoringParameters.put("10 городов по имени", listCity);

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @Operation(summary = "Проверка доступности username")
    @GetMapping("/username/{username}")
    public ResponseEntity<String> isAvailableUsername(@PathVariable("username") String username) {
        log.debug("Получен запрос на проверку доступности username {}", username);
        if (!ParameterService.getIsAvailableUsernameEnabled.getValue()) {
            log.debug("Метод isAvailableUsername выключен параметром getEventByIdEnabled = false");
            throw new RuntimeException("Method isAvailableUsername is disabled by parameter isAvailableUsernameEnabled");
        }
        Optional<Account> optionalUser = accountService.findByUserName(username);

        return optionalUser
                .map(user -> {
                    log.debug("Username {} занят другим пользователем, значение недоступно", username);
                    return new ResponseEntity<>("Username (" + username + ") IS NOT available"
                            , HttpStatus.FOUND);
                })
                .orElseGet(() -> {
                    log.debug("Значение username {} доступно", username);

                    Map<String, ParametersDto> monitoringParameters = new HashMap<>();

                    ParametersDto isAvailableUsernmaeParam = ParametersDto.builder()
                            .description("Значение username доступно")
                            .value(username)
                            .build();
                    monitoringParameters.put("Значение username доступно", isAvailableUsernmaeParam);

                    monitorProducerService.send(
                            monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                                    ControlDto.MANUAL,
                                    this.getClass(),
                                    monitoringParameters)
                );

                    return new ResponseEntity<>("Username (" + username + ") is available", HttpStatus.OK);
                });
    }

    @Operation(summary = "Проверка доступности email")
    @GetMapping("/email/{email}")
    public ResponseEntity<String> isAvailableEmail(@PathVariable("email") String email) {
        log.debug("Получен запрос на проверку доступности email {}", email);
        if (!ParameterService.getIsAvailableEmailEnabled.getValue()) {
            log.debug("Метод isAvailableEmail выключен параметром getIsAvailableEmailEnabled = false");
            throw new RuntimeException("Method isAvailableEmail is disabled by parameter getIsAvailableEmailEnabled");
        }

        Optional<Account> optionalUser = accountService.getUserByEmail(email);

        return optionalUser
                .map(user -> {
                    log.debug("Email {} занят другим пользователем, значение недоступно", email);
                    return new ResponseEntity<>("Email (" + email + ") IS NOT available", HttpStatus.FOUND);
                })
                .orElseGet(() -> {
                    log.debug("Значение email {} доступно", email);

                    Map<String, ParametersDto> monitoringParameters = new HashMap<>();

                    ParametersDto isAvailableEmailParam = ParametersDto.builder()
                            .description("Значение email доступно ")
                            .value(email)
                            .build();

                    monitoringParameters.put("Значение email доступно", isAvailableEmailParam);

                    monitorProducerService.send(
                            monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                                    ControlDto.MANUAL,
                                    this.getClass(),
                                    monitoringParameters)
                    );
                    return new ResponseEntity<>("Email (" + email + ") is available", HttpStatus.OK);
                });
    }
}
