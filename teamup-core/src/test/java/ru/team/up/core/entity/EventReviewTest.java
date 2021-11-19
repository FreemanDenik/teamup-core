package ru.team.up.core.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.EventReviewRepository;
import ru.team.up.core.repositories.EventTypeRepository;
import ru.team.up.core.repositories.InterestsRepository;
import ru.team.up.core.repositories.StatusRepository;
import ru.team.up.core.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Тест сущности отзыва на мероприятие
 */

@SpringBootTest
class EventReviewTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private EventReviewRepository eventReviewRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private InterestsRepository interestsRepository;

    Interests testInterest = Interests.builder()
            .title("test")
            .shortDescription("123")
            .build();

    User testUser = User.builder()
            .name("User")
            .lastName("User")
            .middleName("User")
            .login("Usertest")
            .email("User@gmail.com")
            .password("user")
            .accountCreatedTime(LocalDate.of(2020, 5, 12))
            .lastAccountActivity(LocalDateTime.of(2021, 6, 23, 15,0))
            .city("Moscow")
            .age(30)
            .aboutUser("123")
            .userInterests(Collections.singleton(testInterest))
            .build();

    EventType testEventType = EventType.builder()
            .type("IT")
            .build();

    Status testStatus = Status.builder()
            .status("upcoming")
            .build();

    Event testEvent = Event.builder()
            .eventName("Test Event")
            .authorId(testUser)
            .descriptionEvent("Test description")
            .eventType(testEventType)
            .eventInterests(Collections.singleton(testInterest))
            .placeEvent("Moscow")
            .timeEvent(LocalDateTime.of(2021, 11, 3, 15, 0))
            .eventUpdateDate(LocalDate.of(2021, 11, 3))
            .participantsEvent(List.of(testUser))
            .status(testStatus)
            .build();

    EventReview testReview = EventReview.builder()
            .reviewForEvent(testEvent)
            .reviewer(testUser)
            .reviewTime(LocalDateTime.of(2021, 11, 5, 12, 0))
            .reviewMessage("test message")
            .eventGrade(10)
            .build();

    @Test
    void testEventReview() {
        interestsRepository.save(testInterest);
        userRepository.save(testUser);
        eventTypeRepository.save(testEventType);
        statusRepository.save(testStatus);
        eventRepository.save(testEvent);
        eventReviewRepository.save(testReview);

        EventReview eventReview = eventReviewRepository.findById(1L).orElse(new EventReview());
        Assertions.assertEquals(testEvent, eventReview.getReviewForEvent());
        Assertions.assertEquals(testUser, eventReview.getReviewer());
        Assertions.assertEquals("test message", eventReview.getReviewMessage());
        Assertions.assertEquals(testReview.getReviewId(), eventReview.getReviewId());
        Assertions.assertEquals(10, eventReview.getEventGrade());
    }
}