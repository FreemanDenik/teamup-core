package ru.team.up.input.controller.publicController;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.team.up.core.entity.*;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.UserService;
import ru.team.up.dto.ControlDto;
import ru.team.up.dto.ReportDto;
import ru.team.up.input.exception.EventCheckException;
import ru.team.up.input.exception.EventCreateRequestException;
import ru.team.up.input.payload.request.EventRequest;
import ru.team.up.input.payload.request.JoinRequest;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.service.EventServiceRest;
import ru.team.up.input.wordmatcher.WordMatcher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class EventRestControllerPublicTest {

    @Mock
    private EventServiceRest eventServiceRest;

    @Mock
    private WordMatcher wordMatcher;

    @Mock
    private MonitorProducerService monitoringProducerService;

    @Mock
    private UserService userService;

    @Spy
    @InjectMocks
    private EventRestControllerPublic eventRestControllerPublic =
            new EventRestControllerPublic(eventServiceRest, wordMatcher, userService, monitoringProducerService);

    private List<Event> events;
    private JoinRequest joinRequest;
    private JoinRequest emptyJoinRequest;
    private UserRequest userRequest;
    private EventRequest eventRequest2;
    private Event event2;
    private Event event;
    private User testUser2;
    private EventType eventType;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        emptyJoinRequest = null;
        events = new LinkedList<>();
        User securityUser = mock(User.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(securityUser);

        eventType = EventType.builder()
                .id(1L)
                .type("type")
                .build();

        Interests programming = Interests.builder()
                .title("Programming")
                .shortDescription("Like to write programs in java")
                .build();

        User testUser = User.builder()
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
                .birthday(LocalDate.of(1979, 1, 20))
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

        Status status = Status.builder()
                .status("status")
                .build();

        event = Event.builder()
                .id(1L)
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
                .id(2L)
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
        // Все ок
        assertEquals(2, eventRestControllerPublic.getAllEvents().getEventDtoList().size());
        // Не ок
        assertNotEquals(5, eventRestControllerPublic.getAllEvents().getEventDtoList().size());
    }

    @Test
    void findEventById() {
        when(eventServiceRest.getEventById(1L)).thenReturn(event);
        // OK
        assertEquals(1L, eventRestControllerPublic.findEventById(1L).getEventDto().getId());
        // Not OK
        assertNotEquals(2L, eventRestControllerPublic.findEventById(1L).getEventDto().getId());
    }

    @Test
    void getAllEventByCity() {
        when(eventServiceRest.getAllEventsByCity("Краснодар")).thenReturn(events);
        // OK
        assertEquals(2, eventRestControllerPublic.getAllEventByCity("Краснодар").getEventDtoList().size());
        // Not OK
        assertNotEquals(5, eventRestControllerPublic.getAllEventByCity("Краснодар").getEventDtoList().size());
        // Неверный запрос
        assertNotEquals(2, eventRestControllerPublic.getAllEventByCity("Футбол").getEventDtoList().size());
    }

    @Test
    void getAllEventsByCityByLimit() {
        when(eventServiceRest.getAllEventsByCityByLimit("Санкт-Петербург", 1)).thenReturn(events);
        // не тестировал
        assertEquals(2, eventRestControllerPublic.getAllEventByCityByLimit("Санкт-Петербург", 1).getEventDtoList().size());
        // не тестировал
        assertNotEquals(5, eventRestControllerPublic.getAllEventByCityByLimit("Санкт-Петербург", 1).getEventDtoList().size());
        // не тестировал
        assertNotEquals(2, eventRestControllerPublic.getAllEventByCityByLimit("Санкт-Петербург", 1).getEventDtoList().size());
    }

    @Test
    void findEventsByName() {
        when(eventServiceRest.getEventByName("Event")).thenReturn(events);
        // OK
        assertEquals(2, eventRestControllerPublic.findEventsByName("Event").getEventDtoList().size());
        // Not OK
        assertNotEquals(4,
                eventRestControllerPublic.findEventsByName("Event").getEventDtoList().size());
        // Неверный запрос
        assertNotEquals(2,
                eventRestControllerPublic.findEventsByName("WrongEventName").getEventDtoList().size());
    }

    @Test
    void findEventsByAuthor() {
        when(eventServiceRest.getAllEventsByAuthor(1L)).thenReturn(events);
        // OK
        assertEquals(200, eventRestControllerPublic.findEventsByAuthor(userRequest).getStatusCodeValue());
        // Not OK
        assertEquals(204,
                eventRestControllerPublic.findEventsByAuthor(new UserRequest(testUser2)).getStatusCodeValue());
        // Неверный запрос
        assertThrows(NullPointerException.class,
                ()-> eventRestControllerPublic.findEventsByAuthor(new UserRequest()).getStatusCodeValue());
    }

    @Test
    void findEventsByType() {
        when(eventServiceRest.getAllEventsByEventType(eventType)).thenReturn(events);
        // OK
        assertEquals(200, eventRestControllerPublic.findEventsByType(eventType).getStatusCodeValue());
        // Неверный запрос
        assertEquals(204, eventRestControllerPublic.findEventsByType(new EventType()).getStatusCodeValue());
    }

    @Test
    void createEvent() {
        when(eventServiceRest.saveEvent(event2)).thenReturn(event2);
        // OK
        assertEquals(201, eventRestControllerPublic.createEvent(eventRequest2).getStatusCodeValue());
        // Неверный запрос
        assertThrows(NullPointerException.class, ()-> eventRestControllerPublic.createEvent(new EventRequest()));
        // Нет плохих слов
        when(wordMatcher.detectBadWords(eventRequest2.getEvent().getEventName())).thenReturn(false);
        when(wordMatcher.detectUnnecessaryWords(eventRequest2.getEvent().getEventName())).thenReturn(false);
        assertEquals(201, eventRestControllerPublic.createEvent(eventRequest2).getStatusCodeValue());
        // Есть плохие слова
        when(wordMatcher.detectBadWords(eventRequest2.getEvent().getEventName())).thenReturn(true);
        when(wordMatcher.detectUnnecessaryWords(eventRequest2.getEvent().getEventName())).thenReturn(false);
        assertThrows(EventCreateRequestException.class, ()-> eventRestControllerPublic.createEvent(eventRequest2));
        // Есть ненужные слова
        when(wordMatcher.detectBadWords(eventRequest2.getEvent().getEventName())).thenReturn(false);
        when(wordMatcher.detectUnnecessaryWords(eventRequest2.getEvent().getEventName())).thenReturn(true);
        assertThrows(EventCheckException.class, ()-> eventRestControllerPublic.createEvent(eventRequest2));
        // Исключение плохих слов > ненужных слов
        when(wordMatcher.detectBadWords(eventRequest2.getEvent().getEventName())).thenReturn(true);
        when(wordMatcher.detectUnnecessaryWords(eventRequest2.getEvent().getEventName())).thenReturn(true);
        assertThrows(EventCreateRequestException.class, ()-> eventRestControllerPublic.createEvent(eventRequest2));

        // ТОЖЕ САМОЕ, НО ДЛЯ ОПИСАНИЯ МЕРОПРИЯТИЯ
        // Сброс проверок по названию мероприятия
        when(wordMatcher.detectBadWords(eventRequest2.getEvent().getEventName())).thenReturn(false);
        when(wordMatcher.detectUnnecessaryWords(eventRequest2.getEvent().getEventName())).thenReturn(false);
        // Нет плохих слов
        when(wordMatcher.detectBadWords(eventRequest2.getEvent().getDescriptionEvent())).thenReturn(false);
        when(wordMatcher.detectUnnecessaryWords(eventRequest2.getEvent().getDescriptionEvent())).thenReturn(false);
        assertEquals(201, eventRestControllerPublic.createEvent(eventRequest2).getStatusCodeValue());
        // Есть плохие слова
        when(wordMatcher.detectBadWords(eventRequest2.getEvent().getDescriptionEvent())).thenReturn(true);
        when(wordMatcher.detectUnnecessaryWords(eventRequest2.getEvent().getDescriptionEvent())).thenReturn(false);
        assertThrows(EventCreateRequestException.class, ()-> eventRestControllerPublic.createEvent(eventRequest2));
        // Есть ненужные слова
        when(wordMatcher.detectBadWords(eventRequest2.getEvent().getDescriptionEvent())).thenReturn(false);
        when(wordMatcher.detectUnnecessaryWords(eventRequest2.getEvent().getDescriptionEvent())).thenReturn(true);
        assertThrows(EventCheckException.class, ()-> eventRestControllerPublic.createEvent(eventRequest2));
        // Исключение плохих слов > ненужных слов
        when(wordMatcher.detectBadWords(eventRequest2.getEvent().getDescriptionEvent())).thenReturn(true);
        when(wordMatcher.detectUnnecessaryWords(eventRequest2.getEvent().getDescriptionEvent())).thenReturn(true);
        assertThrows(EventCreateRequestException.class, ()-> eventRestControllerPublic.createEvent(eventRequest2));
    }

    @Test
    void updateEvent() {
        when(eventServiceRest.updateEvent(event.getId(), event)).thenReturn(event);
        assertEquals(200, eventRestControllerPublic.updateEvent(eventRequest2, 1L).getStatusCodeValue());
        assertThrows(NullPointerException.class, ()-> eventRestControllerPublic.updateEvent(new EventRequest(), 1L));
    }

    @Test
    void deleteEvent() {
        when(eventServiceRest.getEventById(1L)).thenReturn(event);
        // OK
        assertEquals(200, eventRestControllerPublic.deleteEvent(1L).getStatusCodeValue());
        // Not OK
        assertEquals(204, eventRestControllerPublic.deleteEvent(3L).getStatusCodeValue());
    }

    @Test
    void addEventParticipant() {
        when(eventServiceRest.addParticipant(1L, 1L)).thenReturn(event);
        // OK
        assertEquals(200, eventRestControllerPublic.addEventParticipant(joinRequest).getStatusCodeValue());
        // Неверный запрос
        assertThrows(NullPointerException.class, ()-> eventRestControllerPublic.addEventParticipant(emptyJoinRequest));
    }

    @Test
    void deleteEventParticipant() {
        when(eventServiceRest.deleteParticipant(1L, 1L)).thenReturn(event);
        assertEquals(200, eventRestControllerPublic.deleteEventParticipant(joinRequest).getStatusCodeValue());
        assertThrows(NullPointerException.class,
                ()-> eventRestControllerPublic.deleteEventParticipant(emptyJoinRequest));
    }
}