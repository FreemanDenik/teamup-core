package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.team.up.core.entity.*;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.StatusRepository;
import ru.team.up.core.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Yura Katkov
 */
@Slf4j
@RunWith(MockitoJUnitRunner.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private NotifyService notifyService;

    @InjectMocks
    private EventService eventService = new EventServiceImpl(eventRepository, userRepository, statusRepository,
            notifyService);

    private User userTest, userTest1;
    private Event eventTest;
    private Set<User> userTestSet;


    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        userTest1 = User.builder()
                .id(2L)
                .firstName("testUser")
                .lastName("testUserLastName")
                .middleName("testUserMiddleName")
                .username("testUserLogin")
                .email("testUser@mail.ru")
                .password("user")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Moscow")
                .birthday(LocalDate.of (1992, 1, 20))
                .aboutUser("testUserAbout")
                .build();

        userTestSet = new HashSet<>();
        userTestSet.add(userTest1);

        userTest = User.builder()
                .id(1L)
                .firstName("testUser")
                .lastName("testUserLastName")
                .middleName("testUserMiddleName")
                .username("testUserLogin")
                .email("testUser@mail.ru")
                .password("user")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Moscow")
                .birthday(LocalDate.of (1992, 1, 20))
                .aboutUser("testUserAbout")
                .subscribers(userTestSet)
                .build();

        eventTest = Event.builder()
                .id(1L)
                .eventName("Football game")
                .descriptionEvent("Join people to play football math")
                .city("Moscow")
                .placeEvent("Stadium")
                .participantsEvent(userTestSet)
                .eventNumberOfParticipant((byte)userTestSet.size())
                .timeEvent(LocalDateTime.now())
                .status(new Status(8L, "ТестСтатус"))
                .eventType(new EventType(1L, "TestEventType"))
                .authorId(userTest)
                .build();
    }

    @Test
    void getAllEvents() {
        List<Event> eventList = new LinkedList<>();
        eventList.add(eventTest);
        assertTrue(eventService.getAllEvents().isEmpty());
        when(eventRepository.findAll()).thenReturn(eventList);
        assertFalse(eventService.getAllEvents().isEmpty());
    }

    // TODO В EventServiceImpl в методе saveEvent надо userRepository.findById поменять на userRepository.findUserById
    @Test
    void getOneEvent() {
        assertThrows(NullPointerException.class, () -> eventService.getOneEvent(1L));
        when(eventRepository.getOne(1L)).thenReturn(eventTest);
        assertNotNull(eventService.getOneEvent(1L));
    }

    @Test
    void saveEvent() {
        when(userRepository.findUserById(1L)).thenReturn(userTest);
        when(eventRepository.save(eventTest)).thenReturn(eventTest);
        assertEquals(eventTest, eventService.saveEvent(eventTest));
    }

    @Test
    void addParticipantEvent() {
        when(eventRepository.getOne(1L)).thenReturn(eventTest);
        when(eventRepository.save(eventTest)).thenReturn(eventTest);
        // Размер Сета подписчиков до
        assertEquals(1, eventRepository.getOne(1L).getParticipantsEvent().size());
        // Запуск метода добавления подписчика на мероприятие
        eventService.addParticipantEvent(1L, userTest);
        // Размер Сета подписчиков после
        assertEquals(2, eventRepository.getOne(1L).getParticipantsEvent().size());
    }

    @Test
    void eventApprovedByModerator() {
        when(eventRepository.getOne(1L)).thenReturn(eventTest);
        when(statusRepository.getOne(1L)).thenReturn(new Status(1L, "Одобренный"));
        // Статус ивента до
        assertEquals("ТестСтатус", eventService.getOneEvent(1L).getStatus().getStatus());
        // Запуск метода одобрения мероприятия модератором
        eventService.eventApprovedByModerator(1L);
        // Статус ивента после
        assertEquals("Одобренный", eventService.getOneEvent(1L).getStatus().getStatus());
    }

    @Test
    void eventClosedByModerator() {
        when(eventRepository.getOne(1L)).thenReturn(eventTest);
        when(statusRepository.getOne(4L)).thenReturn(new Status(4L, "Закрыто"));
        // Статус ивента до
        assertEquals("ТестСтатус", eventService.getOneEvent(1L).getStatus().getStatus());
        // Запуск метода одобрения мероприятия модератором
        eventService.eventClosedByModerator(1L);
        // Статус ивента после
        assertEquals("Закрыто", eventService.getOneEvent(1L).getStatus().getStatus());
    }
}