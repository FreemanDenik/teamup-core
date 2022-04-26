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
import ru.team.up.core.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class ApplicationServiceImplTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMessageRepository userMessageRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventTypeRepository eventTypeRepository;

    @InjectMocks
    private ApplicationService applicationService = new ApplicationServiceImpl(
            applicationRepository, userRepository, userMessageRepository, statusRepository);

    private Application applicationTest;

    private Event eventTest;

    private EventType eventTypeTest;

    private User userTest;

    private Status statusTest;

    private UserMessage userMessageTest;

    private List<Application> newApplicationListTest;

    private Set<UserMessage> userMessagesSetTest;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        userMessagesSetTest = new HashSet<>();
        newApplicationListTest = new ArrayList<>();

        userMessageTest = UserMessage.builder()
                .id(1L)
                .message("HelLo :D")
                .build();

        userMessagesSetTest.add(userMessageTest);

        statusTest = Status.builder()
                .status("New")
                .build();

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
                .userMessages(userMessagesSetTest)
                .build();

        eventTypeTest = EventType.builder()
                .type("Game")
                .build();

        eventTest = Event.builder()
                .eventName("Football game")
                .descriptionEvent("Join people to play football math")
                .city("Moscow")
                .placeEvent("Stadium")
                .eventNumberOfParticipant((byte)20)
                .timeEvent(LocalDateTime.now())
                .status(new Status())
                .eventType(eventTypeTest)
                .authorId(userTest)
                .build();

        applicationTest = Application.builder()
                .event(eventTest)
                .user(userTest)
                .build();
    }

    @Test
    void getAllApplicationsByEventId() {
        newApplicationListTest.add(applicationTest);
        when(applicationRepository.findAllByEventId(1L)).thenReturn(newApplicationListTest);
        log.debug("↓ Проверка соответствия данных ↓");
        assertEquals(newApplicationListTest, applicationService.getAllApplicationsByEventId(1L));
    }

    @Test
    void getAllApplicationsByUserId() {
        newApplicationListTest.add(applicationTest);
        when(applicationRepository.findAllByUserId(1L)).thenReturn(newApplicationListTest);
        log.debug("↓ Проверка соответствия данных ↓");
        assertEquals(newApplicationListTest, applicationService.getAllApplicationsByUserId(1L));
    }

    @Test
    void getApplication() {
        when(applicationRepository.getOne(1L)).thenReturn(applicationTest);
        log.debug("↓ Проверка соответствия данных ↓");
        assertEquals(applicationTest, applicationService.getApplication(1L));
    }

    // TODO поменять на findUserById в сервисе
    @Test
    void saveApplication() {
        when(userRepository.findUserById(userTest.getId())).thenReturn(userTest);
        when(statusRepository.getOne(5L)).thenReturn(new Status(5L, "Создано"));
        when(userMessageRepository.save(userMessageTest)).thenReturn(userMessageTest);
        when(applicationRepository.save(applicationTest)).thenReturn(applicationTest);
        applicationService.saveApplication(applicationTest, userTest);
        verify(applicationRepository, times(1)).save(applicationTest);
    }

    @Test
    void deleteApplication() {
        applicationService.deleteApplication(1L);
        verify(applicationRepository, times(1)).deleteById(1L);
    }
}