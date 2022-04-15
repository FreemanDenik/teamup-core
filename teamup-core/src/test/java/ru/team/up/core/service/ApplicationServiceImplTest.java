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
import java.util.List;

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


    @BeforeEach
    void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        statusTest = Status.builder()
                .status("New")
                .build();

        userTest = User.builder()
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

    List<Application> newApplicationList = new ArrayList<>();

    @Test
    void getAllApplicationsByEventId() {
        newApplicationList.add(applicationTest);
        when(applicationRepository.findAllByEventId(1L)).thenReturn(newApplicationList);
        log.debug("↓ Проверка соответствия данных ↓");
        assertEquals(newApplicationList, applicationService.getAllApplicationsByEventId(1L));
    }

    @Test
    void getAllApplicationsByUserId() {
        newApplicationList.add(applicationTest);
        when(applicationRepository.findAllByUserId(1L)).thenReturn(newApplicationList);
        log.debug("↓ Проверка соответствия данных ↓");
        assertEquals(newApplicationList, applicationService.getAllApplicationsByUserId(1L));
    }

    @Test
    void getApplication() {
        when(applicationRepository.getOne(1L)).thenReturn(applicationTest);
        log.debug("↓ Проверка соответствия данных ↓");
        assertEquals(applicationTest, applicationService.getApplication(1L));
    }

    // Плохой тест. java.util.NoSuchElementException: No value present
    @Test
    void saveApplication() {
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