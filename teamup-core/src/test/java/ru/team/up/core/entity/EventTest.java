package ru.team.up.core.entity;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Тест сущности мероприятия
 */
@SpringBootTest
class EventTest{

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

    private Interests interestsTest = Interests.builder().title("Football")
            .shortDescription("Like to play football").build();
    private Status statusTest = Status.builder().status("Examination").build();
    private EventType typeTest = EventType.builder().type("Game").build();

    @Test
    @Transactional
    void builder(){
        eventTypeRepository.save(typeTest);
        interestsRepository.save(interestsTest);
        statusRepository.save(statusTest);

        Set<Interests> interestsSet = new HashSet<>();
        interestsSet.add(interestsTest);

        User userTest = User.builder().name("testUser").lastName("testUserLastName")
                .middleName("testUserMiddleName").login("testUserLogin").email("testUser@mail.ru")
                .password("3").accountCreatedTime("05.11.2021 18:00")
                .lastAccountActivity("05.11.2021 18:00").city("Moskow")
                .age(30).aboutUser("testUser").userInterests(interestsSet).build();
        userRepository.save(userTest);

        List<User> testListUser = new ArrayList<>();
        testListUser.add(userTest);

        Event eventTest = Event.builder().eventName("Football")
                .descriptionEvent("совместная игра Core и Input ))")
                .placeEvent("где-то на просторах Jira")
                .timeEvent(LocalDateTime.of(2021, 11, 10, 21, 00))
                .eventUpdateDate(LocalDate.now()).participantsEvent(testListUser).eventType(typeTest)
                .authorId(userTest).eventInterests(interestsSet).statusEvent(statusTest).build();
        eventRepository.save(eventTest);

        Assert.assertTrue(eventRepository.findById(1L).isPresent());

        Event userTestBD = eventRepository.findById(1L).get();

        Assert.assertNotNull(userTestBD.getEventName());
        Assert.assertNotNull(userTestBD.getDescriptionEvent());
        Assert.assertNotNull(userTestBD.getPlaceEvent());
        Assert.assertNotNull(userTestBD.getTimeEvent());
        Assert.assertNotNull(userTestBD.getEventUpdateDate());
        Assert.assertFalse(userTestBD.getParticipantsEvent().isEmpty());
        Assert.assertFalse(userTestBD.getEventType().getType().isEmpty());
        Assert.assertNotNull(userTestBD.getAuthorId());
        Assert.assertFalse(userTestBD.getEventInterests().isEmpty());
        Assert.assertFalse(userTestBD.getStatusEvent().getStatus().isEmpty());

        eventRepository.deleteById(1L);
        Assert.assertFalse(eventRepository.findById(1L).isPresent());
    }
}