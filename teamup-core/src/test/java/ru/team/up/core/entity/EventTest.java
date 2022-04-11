package ru.team.up.core.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Тест сущности мероприятия
 */
@SpringBootTest
class EventTest extends Assertions {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private InterestsRepository interestsRepository;

    private Interests interestsTest = Interests.builder()
            .title("Football")
            .shortDescription("Like to play football")
            .build();
    private Status statusTest = Status.builder()
            .status("Examination")
            .build();
    private EventType typeTest = EventType.builder()
            .type("Game")
            .build();

    @Test
    @Transactional
    void builder(){
        eventTypeRepository.save(typeTest);
        interestsRepository.save(interestsTest);
        statusRepository.save(statusTest);

        Set<Interests> interestsSet = new HashSet<>();
        interestsSet.add(interestsTest);

        User userTest = User.builder()
                .firstName("testUser")
                .lastName("testUserLastName")
                .middleName("testUserMiddleName")
                .username("testUserLogin")
                .email("testUser@mail.ru")
                .password("3")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Moskow")
                .birthday(LocalDate.of (1992, 1, 20))
                .aboutUser("testUser")
                .userInterests(interestsSet)
                .build();
        userRepository.save(userTest);

        Set<User> testListUser = new HashSet<>();
        testListUser.add(userTest);

        Event eventTest = Event.builder()
                .eventName("Football")
                .descriptionEvent("совместная игра Core и Input ))")
                .city("Jira")
                .placeEvent("где-то на просторах Jira")
                .timeEvent(LocalDateTime.of(2021, 11, 10, 21, 00))
                .eventUpdateDate(LocalDate.now())
                .participantsEvent(testListUser)
                .eventNumberOfParticipant((byte)testListUser.size())
                .eventType(typeTest)
                .eventPrivacy(true)
                .authorId(userTest)
                .eventInterests(interestsSet)
                .status(statusTest).build();
        eventRepository.save(eventTest);

        assertTrue(eventRepository.findById(1L).isPresent());

        Event userTestBD = eventRepository.findById(1L).get();

        assertNotNull(userTestBD.getEventName());
        assertNotNull(userTestBD.getDescriptionEvent());
        assertNotNull(userTestBD.getPlaceEvent());
        assertNotNull(userTestBD.getTimeEvent());
        assertNotNull(userTestBD.getEventUpdateDate());
        assertFalse(userTestBD.getParticipantsEvent().isEmpty());
        assertFalse(userTestBD.getEventType().getType().isEmpty());
        assertNotNull(userTestBD.getAuthorId());
        assertFalse(userTestBD.getEventInterests().isEmpty());
        assertFalse(userTestBD.getStatus().getStatus().isEmpty());

        eventRepository.deleteById(1L);
        assertFalse(eventRepository.findById(1L).isPresent());
    }
}