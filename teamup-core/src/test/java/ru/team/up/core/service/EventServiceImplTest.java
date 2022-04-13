package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.team.up.core.entity.Event;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.StatusRepository;
import ru.team.up.core.repositories.UserRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    public void setUpEntity() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEvents() {
        assertThrows(NoContentException.class, ()-> eventService.getAllEvents());
        List<Event> eventList = new LinkedList<>();
        when(eventRepository.findAll()).thenReturn(eventList);
        assertNotNull(eventService.getAllEvents());
    }

    @Test
    void getOneEvent() {
    }

    @Test
    void saveEvent() {
    }

    @Test
    void addParticipantEvent() {
    }

    @Test
    void eventApprovedByModerator() {
    }

    @Test
    void eventClosedByModerator() {
    }

    @Test
    void updateNumberOfViews() {
    }

    @Test
    void getEventsUsers() {
    }
}