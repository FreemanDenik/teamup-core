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
import ru.team.up.core.repositories.EventReviewRepository;
import ru.team.up.core.repositories.StatusRepository;
import ru.team.up.core.repositories.UserMessageRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class EventReviewServiceImplTest {

    @Mock
    private EventReviewRepository eventReviewRepository;

    @Mock
    private UserMessageRepository userMessageRepository;

    @Mock
    private NotifyService notifyService;

    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private EventReviewService eventReviewService = new EventReviewServiceImpl(eventReviewRepository,
            userMessageRepository, notifyService, statusRepository);

    private EventReview eventReviewTest;
    private User userTest;
    private Event eventTest;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

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
                .build();

        eventTest = Event.builder()
                .id(1L)
                .eventName("Football game")
                .descriptionEvent("Join people to play football math")
                .city("Moscow")
                .placeEvent("Stadium")
                .eventNumberOfParticipant((byte)20)
                .timeEvent(LocalDateTime.now())
                .status(new Status())
                .eventType(new EventType())
                .authorId(userTest)
                .build();

        eventReviewTest = EventReview.builder()
                .reviewForEvent(eventTest)
                .reviewer(userTest)
                .reviewTime(LocalDateTime.now())
                .reviewMessage("Message")
                .build();
    }

    @Test
    void saveEventReview() {
        when(eventReviewRepository.save(eventReviewTest)).thenReturn(eventReviewTest);
        assertEquals("Message", eventReviewService.saveEventReview(eventReviewTest).getReviewMessage());
    }
}