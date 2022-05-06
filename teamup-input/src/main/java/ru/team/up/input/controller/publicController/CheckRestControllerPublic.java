package ru.team.up.input.controller.publicController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.City;
import ru.team.up.core.service.CityService;
import ru.team.up.input.service.UserServiceRest;

import java.util.List;
import java.util.Optional;

@Slf4j
@Tag(name = "Check Public Controller",description = "Check API")
@RestController
@RequestMapping(value = "api/public/check")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CheckRestControllerPublic {

    private CityService cityService;
    private UserServiceRest userService;

    @Operation(summary ="Поиск города по названию")
    @GetMapping("/city/one/{name}")
    public ResponseEntity<City> getCityByName(@PathVariable("name") String name) {
        log.debug("Получен запрос на город {}", name);

        Optional <City> optionalCity = Optional.ofNullable(cityService.findCityByName(name));

        return optionalCity
                .map(city -> {
                    log.debug("Город с названием: {} найден", name);
                    return new ResponseEntity<>(city, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.error("Город с названием: {} не найден", name);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                });
    }

    @Operation(summary ="Поиск города по названию")
    @GetMapping("/city/{name}/{subject}")
    public ResponseEntity<City> getCityByNameAndSubject(@PathVariable("name") String name,
                                                        @PathVariable("subject") String subject) {
        log.debug("Получен запрос на город {} в субъекте {}", name, subject);

        Optional<City> optionalCity = Optional.ofNullable(cityService.findCityByNameAndSubject(name, subject));

        return optionalCity
                .map(city -> {
                    log.debug("Город с названием: {} и субъектом {} найден", name, subject);
                    return new ResponseEntity<>(city, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.debug("Город с названием: {} и субъектом {} не найден", name, subject);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                });
    }

    @Operation(summary ="Получение списка всех городов")
    @GetMapping("/city")
    public ResponseEntity<List<City>> getAllCities() {
        log.debug("Получен запрос на список городов");
        List<City> cities = cityService.getAllCities();

        if (cities.isEmpty()) {
            log.error("Список городов пуст");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.debug("Список городов получен");
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @Operation(summary = "Получение списка 10 городов, подходящих по имени")
    @GetMapping("/city/{name}")
    public ResponseEntity<List<City>> getSomeCitiesByName(@PathVariable("name") String name) {
        log.debug("Получен запрос на список городов по имени {}", name);
        List<City> cities = cityService.getSomeCitiesByName(name);

        if (cities.isEmpty()) {
            log.error("Список городов пуст");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.debug("Список городов получен");
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @Operation(summary = "Проверка доступности username")
    @GetMapping("/username/{username}")
    public ResponseEntity<String> isAvailableUsername(@PathVariable("username") String username) {
        log.debug("Получен запрос на проверку доступности username {}", username);

        Optional<Account> optionalUser = Optional.ofNullable(userService.getUserByUsername(username));

        return optionalUser
                .map(user -> {
                    log.debug("Username {} занят другим пользователем, значение недоступно", username);
                    return new ResponseEntity<>("Username (" + username + ") IS NOT available", HttpStatus.NOT_ACCEPTABLE);
                })
                .orElseGet(() -> {
                    log.debug("Значение username {} доступно", username);
                    return new ResponseEntity<>("Username (" + username + ") is available", HttpStatus.OK);
                });
    }

    @Operation(summary = "Проверка доступности email")
    @GetMapping("/email/{email}")
    public ResponseEntity<String> isAvailableEmail(@PathVariable("email") String email) {
        log.debug("Получен запрос на проверку доступности email {}", email);

        Optional<Account> optionalUser = Optional.ofNullable(userService.getUserByEmail(email));

        return optionalUser
                .map(user -> {
                    log.debug("Email {} занят другим пользователем, значение недоступно", email);
                    return new ResponseEntity<>("Email (" + email + ") IS NOT available", HttpStatus.NOT_ACCEPTABLE);
                })
                .orElseGet(() -> {
                    log.debug("Значение email {} доступно", email);
                    return new ResponseEntity<>("Email (" + email + ") is available", HttpStatus.OK);
                });
    }
}
