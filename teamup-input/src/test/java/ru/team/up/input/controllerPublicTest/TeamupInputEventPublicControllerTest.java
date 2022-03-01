package ru.team.up.input.controllerPublicTest;

import org.junit.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.entity.*;
import ru.team.up.input.controller.publicController.EventRestControllerPublic;
import ru.team.up.input.payload.request.EventRequest;
import ru.team.up.input.payload.request.JoinRequest;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.service.EventServiceRest;
import ru.team.up.input.wordmatcher.WordMatcher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TeamupInputEventPublicControllerTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private WordMatcher wordMatcher;

    @Mock
    private EventServiceRest eventService;

    @Autowired
    @InjectMocks
    private EventRestControllerPublic eventRestControllerPublic;

    EventType eventType = EventType.builder()
            .type("type")
            .build();

    Interests programming = Interests.builder()
            .title("Programming")
            .shortDescription("Like to write programs in java")
            .build();

    User testUser = User.builder()
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

    User testUser2 = User.builder()
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

    Event event = Event.builder()
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

    Event event2 = Event.builder()
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

    EventRequest eventRequest2 = EventRequest.builder()
            .event(event2)
            .build();

    JoinRequest joinRequest = JoinRequest.builder()
            .eventId(1L)
            .userId(1L)
            .build();

    UserRequest userRequest = UserRequest.builder()
            .user(testUser)
            .build();

    ArrayList<Event> events = new ArrayList<Event>();

    @Test
    public void testCreate() {
        when(wordMatcher.detectBadWords(eventRequest2.getEvent().getEventName())).thenReturn(false);
        when(wordMatcher.detectBadWords(eventRequest2.getEvent().getDescriptionEvent())).thenReturn(false);
        when(eventService.saveEvent(eventRequest2.getEvent())).thenReturn(event2);
        Assert.assertEquals(201, eventRestControllerPublic.createEvent(eventRequest2).getStatusCodeValue());
    }

    @Test
    public void testGetAll() {
        events.add(event);
        when(eventService.getAllEvents()).thenReturn(events);
        Assert.assertEquals(200, eventRestControllerPublic.getAllEvents().getStatusCodeValue());
    }

    @Test
    public void testGetById() {
        when(eventService.getEventById(1L)).thenReturn(event2);
        Assert.assertEquals(200, eventRestControllerPublic.findEventById(1L).getStatusCodeValue());
    }

    @Test
    public void testGetByName() {
        events.add(event2);
        when(eventService.getEventByName(event2.getEventName())).thenReturn(events);
        Assert.assertEquals(200, eventRestControllerPublic.findEventsByName(event2.getEventName()).getStatusCodeValue());
    }

    @Test
    public void testFindEventsByAuthor() {
        events.add(event2);
        when(eventService.getAllEventsByAuthor(testUser.getId())).thenReturn(events);
        Assert.assertEquals(200, eventRestControllerPublic.findEventsByAuthor(userRequest).getStatusCodeValue());
    }

    @Test
    public void testFindEventsByType() {
        events.add(event2);
        when(eventService.getAllEventsByEventType(eventType)).thenReturn(events);
        Assert.assertEquals(200, eventRestControllerPublic.findEventsByType(eventType).getStatusCodeValue());
    }

    @Test
    public void testUpdate() {
        when(eventService.updateEvent(event2.getId(), eventRequest2.getEvent())).thenReturn(event2);
        Assert.assertEquals(200, eventRestControllerPublic.updateEvent(eventRequest2, 1L).getStatusCodeValue());
    }

    @Test
    public void testDelete() {
        when(eventService.getEventById(1L)).thenReturn(event2);
        Assert.assertEquals(200, eventRestControllerPublic.deleteEvent(1L).getStatusCodeValue());
    }

    @Test
    public void testAddEventParticipant() {
        when(eventService.addParticipant(joinRequest.getEventId(), joinRequest.getUserId())).thenReturn(event2);
        Assert.assertEquals(200, eventRestControllerPublic.addEventParticipant(joinRequest).getStatusCodeValue());
    }

    @Test
    public void testDeleteEventParticipant() {
        when(eventService.deleteParticipant(joinRequest.getEventId(), joinRequest.getUserId())).thenReturn(event2);
        Assert.assertEquals(200, eventRestControllerPublic.deleteEventParticipant(joinRequest).getStatusCodeValue());
    }
}
