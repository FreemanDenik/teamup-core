package ru.team.up.input.controller.publicController;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.team.up.core.entity.City;
import ru.team.up.core.entity.Role;
import ru.team.up.core.entity.User;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.CityService;
import ru.team.up.input.service.UserServiceRest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Yura Katkov
 */
@Slf4j
@RunWith(MockitoJUnitRunner.class)
class CheckRestControllerPublicTest {

    @Mock
    private CityService cityService;

    @Mock
    private UserServiceRest userService;

    @Mock
    private MonitorProducerService monitorProducerService;

    @InjectMocks
    private CheckRestControllerPublic checkRestControllerPublic =
            new CheckRestControllerPublic(cityService, userService, monitorProducerService);

    private City cityTest1;
    private City cityTest2;
    private List<City> cityListTest;
    private User userTest;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        cityListTest = new ArrayList<>();

        cityTest1 = City.builder()
                .name("Краснодар")
                .subject("Краснодарский край")
                .build();
        cityTest2 = City.builder()
                .name("Сочи")
                .subject("Краснодарский край")
                .build();
        cityListTest.add(cityTest1);
        cityListTest.add(cityTest1);
        cityListTest.add(cityTest2);

        userTest = User.builder()
                .id(1L)
                .email("userka@mail.ru")
                .username("NeoTheUser")
                .firstName("Neo")
                .lastName("Neon")
                .password("Neo12345")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .role(Role.ROLE_USER)
                .userMessages(new HashSet<>())
                .build();
    }

    @Test
    void getCityByName() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(cityService.findCityByName("Краснодар")).thenReturn(cityTest1);
        // Проверка, что город по названию найден
        assertEquals(200, checkRestControllerPublic.getCityByName("Краснодар").getStatusCodeValue());
        // Проверка, что город с другим названием не найден
        assertEquals(204, checkRestControllerPublic.getCityByName("Москва").getStatusCodeValue());
    }

    @Test
    void getCityByNameAndSubject() {
        when(cityService.findCityByNameAndSubject("Краснодар", "Краснодарский край")).thenReturn(cityTest1);
        // Проверка, что город по комбинации (город + регион) найден
        assertEquals(200, checkRestControllerPublic
                .getCityByNameAndSubject("Краснодар", "Краснодарский край").getStatusCodeValue());
        // Проверка, что даже при правильном запросе города, но неправильном регионе, город не найден
        assertEquals(204, checkRestControllerPublic
                .getCityByNameAndSubject("Краснодар", "Кубань").getStatusCodeValue());
    }

    @Test
    void getAllCities() {
        // Проверка, что при пустом списке ничего не найдется
        assertEquals(204, checkRestControllerPublic.getAllCities().getStatusCodeValue());
        when(cityService.getAllCities()).thenReturn(cityListTest);
        // Проверка, что города найдены
        assertEquals(200, checkRestControllerPublic.getAllCities().getStatusCodeValue());
        // Проверка, что размер списка такой, какой ожидается
        assertEquals(3, Objects.requireNonNull(checkRestControllerPublic.getAllCities().getBody()).size());
    }

    @Test
    void getSomeCitiesByName() {
        when(cityService.getSomeCitiesByName("Краснодар")).thenReturn(cityListTest.stream()
                .filter(city -> city.getName().equals("Краснодар")).collect(Collectors.toList()));
        // Проверка, что города с похожим названием найдены
        assertEquals(200, checkRestControllerPublic.getSomeCitiesByName("Краснодар").getStatusCodeValue());
        // Проверка, что найдены все города с одинаковым названием
        assertEquals(2,
                Objects.requireNonNull(checkRestControllerPublic.getSomeCitiesByName("Краснодар").getBody()).size());
        // Проверка, что города с непохожим назнванием не найдены
        assertEquals(204, checkRestControllerPublic.getSomeCitiesByName("Москва").getStatusCodeValue());
    }

    @Test
    void isAvailableUsername() {
        when(userService.getUserByUsername("NeoTheUser")).thenReturn(userTest);
        // Проверка, что имя занято
        assertEquals(406, checkRestControllerPublic.isAvailableUsername("NeoTheUser").getStatusCodeValue());
        // Проверка, что имя свободно
        assertEquals(200, checkRestControllerPublic.isAvailableUsername("NewNeo").getStatusCodeValue());
    }

    @Test
    void isAvailableEmail() {
        when(userService.getUserByEmail("userka@mail.ru")).thenReturn(userTest);
        // Проверка, что емаил занят
        assertEquals(406, checkRestControllerPublic.isAvailableEmail("userka@mail.ru").getStatusCodeValue());
        // Проверка, что емаил свободен
        assertEquals(200, checkRestControllerPublic.isAvailableEmail("neo@mail.ru").getStatusCodeValue());
    }
}