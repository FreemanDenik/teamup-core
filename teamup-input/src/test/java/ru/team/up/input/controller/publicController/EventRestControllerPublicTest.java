package ru.team.up.input.controller.publicController;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.team.up.core.entity.*;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.dto.ControlDto;
import ru.team.up.dto.ReportDto;
import ru.team.up.input.payload.request.EventRequest;
import ru.team.up.input.payload.request.JoinRequest;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.service.EventServiceRest;
import ru.team.up.input.wordmatcher.WordMatcher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class EventRestControllerPublicTest {

    @Mock
    private EventServiceRest eventServiceRest;

    @Mock
    private WordMatcher wordMatcher;

    @Mock
    private MonitorProducerService monitoringProducerService;

    @InjectMocks
    private EventRestControllerPublic eventRestControllerPublic =
            new EventRestControllerPublic(eventServiceRest, wordMatcher, monitoringProducerService);

    List<Event> events;
    JoinRequest joinRequest;
    UserRequest userRequest;
    EventRequest eventRequest2;
    Event event2;
    Event event;
    Status status;
    User testUser2;
    User testUser;
    Interests programming;
    EventType eventType;
    ReportDto reportDto;

    User securityUser;
    Authentication authentication;
    SecurityContext securityContext;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        securityUser = mock(User.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);

        events = new LinkedList<>();

        eventType = EventType.builder()
                .type("type")
                .build();

        programming = Interests.builder()
                .title("Programming")
                .shortDescription("Like to write programs in java")
                .build();

        testUser = User.builder()
                .id(1L)
                .firstName("Aleksey")
                .lastName("Tkachenko")
                .middleName("Petrovich")
                .username("alextk")
                .email("alextk@bk.ru")
                .password("1234")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Moscow")
                .birthday(LocalDate.of (1979, 1, 20))
                .aboutUser("Studying to be a programmer.")
                .userInterests(Collections.singleton(programming))
                .build();

        testUser2 = User.builder()
                .id(2L)
                .firstName("Aleksey2")
                .lastName("Tkachenko2")
                .middleName("Petrovich2")
                .username("alextk2")
                .email("alextk2@bk.ru")
                .password("1234")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Moscow")
                .birthday(LocalDate.of (1979, 1, 20))
                .aboutUser("Studying to be a programmer.")
                .userInterests(Collections.singleton(programming))
                .build();

        status = Status.builder()
                .status("status")
                .build();

        event = Event.builder()
                .eventName("eventName")
                .descriptionEvent("descriptionEvent")
                .placeEvent("placeEvent")
                .timeEvent(LocalDateTime.now())
                .eventUpdateDate(LocalDate.now())
                .participantsEvent(Set.of(testUser2))
                .eventType(eventType)
                .authorId(testUser)
                .eventInterests(Collections.singleton(programming))
                .status(status)
                .build();

        event2 = Event.builder()
                .eventName("eventName2")
                .descriptionEvent("descriptionEvent2")
                .placeEvent("placeEvent2")
                .timeEvent(LocalDateTime.now().plusDays(10))
                .eventUpdateDate(LocalDate.now())
                .participantsEvent(Set.of(testUser2))
                .eventType(eventType)
                .authorId(testUser)
                .eventInterests(Collections.singleton(programming))
                .status(status)
                .build();

        eventRequest2 = EventRequest.builder()
                .event(event2)
                .build();

        joinRequest = JoinRequest.builder()
                .eventId(1L)
                .userId(1L)
                .build();

        userRequest = UserRequest.builder()
                .user(testUser)
                .build();


        events.add(event);
        events.add(event2);

    }

    @Test
    void getAllEvents() {
        when(eventServiceRest.getAllEvents()).thenReturn(events);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(securityUser);
        // Все ок
        assertEquals(2, eventRestControllerPublic.getAllEvents().getEventDtoList().size());
        // Не ок
        assertNotEquals(5, eventRestControllerPublic.getAllEvents().getEventDtoList().size());
    }

    @Test
    void findEventById() {
    }

    @Test
    void getAllEventByCity() {
    }

    @Test
    void findEventsByName() {
    }

    @Test
    void findEventsByAuthor() {
    }

    @Test
    void findEventsByType() {
    }

    @Test
    void createEvent() {
    }

    @Test
    void updateEvent() {
    }

    @Test
    void deleteEvent() {
    }

    @Test
    void addEventParticipant() {
    }

    @Test
    void deleteEventParticipant() {
    }
}