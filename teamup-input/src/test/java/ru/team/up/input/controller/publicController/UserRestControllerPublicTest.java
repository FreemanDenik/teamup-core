package ru.team.up.input.controller.publicController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.team.up.core.entity.*;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.service.UserServiceRest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserRestControllerPublicTest {

    @Mock
    private UserServiceRest userServiceRest;

    @Mock
    private MonitorProducerService monitoringProducerService;

    @InjectMocks
    private UserRestControllerPublic userRestControllerPublic =
            new UserRestControllerPublic(userServiceRest, monitoringProducerService);

    private User testUser;
    private List<User> userList;
    private Event event;
    private List<Event> eventList;
    private UserRequest userRequest;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        userList = new ArrayList<>();
        eventList = new ArrayList<>();

        User securityUser = mock(User.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(securityUser);

        testUser = User.builder()
                .id(1L)
                .firstName("Marina")
                .lastName("Sysenko")
                .middleName("Alexsandrovna")
                .username("test")
                .email("testemail@gmail.com")
                .password("1234")
                .accountCreatedTime(LocalDate.of(2021, 11, 20))
                .lastAccountActivity(LocalDateTime.of(2021, 11, 20, 19, 0))
                .city("Volgograd")
                .birthday(LocalDate.of(1967, 1, 20))
                .aboutUser("I like to cook")
                .build();

        event = Event.builder()
                .id(1L)
                .eventName("eventName")
                .descriptionEvent("descriptionEvent")
                .placeEvent("placeEvent")
                .timeEvent(LocalDateTime.now())
                .eventUpdateDate(LocalDate.now())
                .eventType(new EventType(1L, "eventType"))
                .authorId(testUser)
                .status(new Status(1L, "eventStatus"))
                .build();

        userRequest = UserRequest.builder()
                .user(testUser)
                .build();
    }

    @Test
    void getUserById() {
        when(userServiceRest.getUserById(1L)).thenReturn(testUser);
        // OK
        assertEquals("Marina", userRestControllerPublic.getUserById(1L).getUserDto().getFirstName());
        // Not OK
        assertNotEquals("Masha", userRestControllerPublic.getUserById(1L).getUserDto().getFirstName());
        // Неверный запрос
        assertThrows(NullPointerException.class,
                ()-> userRestControllerPublic.getUserById(4L).getUserDto().getFirstName());
    }

    @Test
    void getUserByEmail() {
        when(userServiceRest.getUserByEmail("testemail@gmail.com")).thenReturn(testUser);
        // OK
        assertEquals("Marina",
                userRestControllerPublic.getUserByEmail("testemail@gmail.com").getUserDto().getFirstName());
        // Not OK
        assertNotEquals("Masha",
                userRestControllerPublic.getUserByEmail("testemail@gmail.com").getUserDto().getFirstName());
        // Неверный запрос
        assertThrows(NullPointerException.class,
                ()-> userRestControllerPublic.getUserByEmail("wrong@gmail.com").getUserDto().getFirstName());
    }

    @Test
    void getUserByUsername() {
        when(userServiceRest.getUserByUsername("Marina")).thenReturn(testUser);
        // OK
        assertEquals("Marina",
                userRestControllerPublic.getUserByUsername("Marina").getUserDto().getFirstName());
        // Not OK
        assertNotEquals("Masha",
                userRestControllerPublic.getUserByUsername("Marina").getUserDto().getFirstName());
        // Неверный запрос
        assertThrows(NullPointerException.class,
                ()-> userRestControllerPublic.getUserByUsername("Masha").getUserDto().getFirstName());
    }

    @Test
    void getUsersList() {
        when(userServiceRest.getAllUsers()).thenReturn(userList);
        // С пустым списком
        assertThrows(RuntimeException.class, ()-> userRestControllerPublic.getUsersList());
        userList.add(testUser);
        // ОК
        assertEquals(1, userRestControllerPublic.getUsersList().size());
        // Not OK
        assertNotEquals(2, userRestControllerPublic.getUsersList().size());
    }

    @Test
    void getEventsByOwnerId() {
        when(userServiceRest.getEventsByOwnerId(1L)).thenReturn(eventList);
        // Нет мероприятий
        assertEquals(0, userRestControllerPublic.getEventsByOwnerId(1L).getEventDtoList().size());
        eventList.add(event);
        // ОК
        assertEquals(1, userRestControllerPublic.getEventsByOwnerId(1L).getEventDtoList().size());
        // Not OK
        assertNotEquals(2, userRestControllerPublic.getEventsBySubscriberId(2L).getEventDtoList().size());
    }

    @Test
    void getEventsBySubscriberId() {
        when(userServiceRest.getEventsBySubscriberId(1L)).thenReturn(eventList);
        // Нет мероприятий
        assertEquals(0, userRestControllerPublic.getEventsBySubscriberId(1L).getEventDtoList().size());
        eventList.add(event);
        // ОК
        assertEquals(1, userRestControllerPublic.getEventsBySubscriberId(1L).getEventDtoList().size());
        // Not OK
        assertNotEquals(2, userRestControllerPublic.getEventsBySubscriberId(2L).getEventDtoList().size());
    }

    @Test
    void updateUser() {
        when(userServiceRest.updateUser(userRequest, testUser.getId())).thenReturn(testUser);
        // Неверный запрос (пользователь не найден)
        assertThrows(RuntimeException.class, ()-> userRestControllerPublic.updateUser(userRequest, testUser.getId()));
        when(userServiceRest.getUserById(testUser.getId())).thenReturn(testUser);
        // ОК
        assertEquals(1L, userRestControllerPublic.updateUser(userRequest, testUser.getId()).getId());
        // Not OK
        assertNotEquals(2L, userRestControllerPublic.updateUser(userRequest, testUser.getId()).getId());
    }

    @Test
    void deleteUserById() {
        when(userServiceRest.getUserById(1L)).thenReturn(testUser);
        // OK
        assertEquals(200, userRestControllerPublic.deleteUserById(1L).getStatusCodeValue());
        // Not OK
        assertEquals(204, userRestControllerPublic.deleteUserById(2L).getStatusCodeValue());
    }

    @Test
    void getTopUsersListInCity() {
        when(userServiceRest.getTopUsersInCity("Сочи")).thenReturn(userList);
        // Пустой список
        assertEquals(0, userRestControllerPublic.getTopUsersListInCity("Сочи").getUserDtoList().size());
        userList.add(testUser);
        // ОК
        assertEquals(1, userRestControllerPublic.getTopUsersListInCity("Сочи").getUserDtoList().size());
        // Not OK
        assertEquals(0, userRestControllerPublic.getTopUsersListInCity("Москва").getUserDtoList().size());
    }

    @Test
    void getAllUsersByCityByLimit() {
        when(userServiceRest.getAllUsersByCityByLimit("Санкт-Петербург", 1)).thenReturn(userList);
//      не тестировал
        assertEquals(0, userRestControllerPublic.getAllUsersByCityByLimit("Санкт-Петербург", 1).getUserDtoList().size());
        userList.add(testUser);
//      не тестировал
        assertEquals(1, userRestControllerPublic.getAllUsersByCityByLimit("Санкт-Петербург", 1).getUserDtoList().size());
//      не тестировал
        assertEquals(0, userRestControllerPublic.getAllUsersByCityByLimit("Санкт-Петербург", 1).getUserDtoList().size());
    }
}