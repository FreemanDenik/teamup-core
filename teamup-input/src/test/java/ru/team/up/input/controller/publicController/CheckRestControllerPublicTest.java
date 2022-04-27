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
import ru.team.up.core.service.CityService;
import ru.team.up.input.service.UserServiceRest;

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

    @InjectMocks
    private CheckRestControllerPublic checkRestControllerPublic =
            new CheckRestControllerPublic(cityService, userService);

    private City cityTest;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        cityTest = City.builder()
                .name("Краснодар")
                .subject("Краснодарский край")
                .lat("45°02′")
                .lon("38°59′")
                .build();
    }

    //TODO убрать final в полях контроллера, тогда заработает

    @Test
    void getCityByName() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(cityService.findCityByName("Краснодар")).thenReturn(cityTest);
        assertEquals(200, checkRestControllerPublic.getCityByName("Краснодар").getStatusCodeValue());
        assertEquals(204, checkRestControllerPublic.getCityByName("Москва").getStatusCodeValue());
    }

    @Test
    void getCityByNameAndSubject() {
        when(cityService.findCityByNameAndSubject("Краснодар", "Краснодарский край")).thenReturn(cityTest);
        assertEquals(200, checkRestControllerPublic
                .getCityByNameAndSubject("Краснодар", "Краснодарский край").getStatusCodeValue());
        assertEquals(204, checkRestControllerPublic
                .getCityByNameAndSubject("Краснодар", "Кубань").getStatusCodeValue());
    }

    @Test
    void getAllCities() {
    }

    @Test
    void getSomeCitiesByName() {
    }

    @Test
    void isAvailableUsername() {
    }

    @Test
    void isAvailableEmail() {
    }
}