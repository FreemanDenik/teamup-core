package ru.team.up.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.*;
import ru.team.up.core.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationServiceImplTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    private Application applicationTest;

    private Event eventTest;

    private EventType eventTypeTest;

    private User userTest;

    private Status statusTest;


    @BeforeEach
    void setUpEntity() {
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

    @Test
    @Transactional
    void getAllApplicationsByEventId() {
        statusRepository.save(statusTest);
        eventTypeRepository.save(eventTypeTest);
        userRepository.save(userTest);
        eventRepository.save(eventTest);
        applicationRepository.save(applicationTest);

        System.out.println("+++++++++++++++++++++" + applicationTest);
        // *********** метод ждет статус 5.

        applicationService.saveApplication(applicationTest, userTest);
        //assertNotNull(applicationRepository.findAllByEventId(eventTest.getId()));
    }

    @Test
    @Transactional
    void getAllApplicationsByUserId() {
    }

    @Test
    @Transactional
    void getApplication() {
    }

    @Test
    @Transactional
    void saveApplication() {
    }

    @Test
    @Transactional
    void deleteApplication() {
    }
}