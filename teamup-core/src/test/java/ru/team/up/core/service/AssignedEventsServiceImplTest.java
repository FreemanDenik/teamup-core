package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.entity.ModeratorSession;
import ru.team.up.core.repositories.AssignedEventsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class AssignedEventsServiceImplTest {

    @Mock
    private AssignedEventsRepository assignedEventsRepository;

    @Mock
    private ModeratorsSessionsServiceImpl moderatorsSessionsServiceImpl;

    @InjectMocks
    private AssignedEventsService assignedEventsService = new AssignedEventsServiceImpl(assignedEventsRepository,
            moderatorsSessionsServiceImpl);

    private AssignedEvents assignedEvents;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        assignedEvents = AssignedEvents.builder()
                .eventId(1L)
                .moderatorId(1L)
                .build();
    }

    List<Long> assignedEventsList = new ArrayList<>();

    // Не проходит из-за финальных полей :D
    @Test
    void removeAssignedEvent() {
        assignedEventsList.add(assignedEvents.getId());
        when(assignedEventsRepository.getIdAssignedEventsByModeratorId(1L)).thenReturn(assignedEventsList);
        assignedEventsService.removeAssignedEvent(1L);
        verify(assignedEventsRepository, times(1)).deleteById(1L);
    }

    // проблема с сохранением модератора. Не разобрался
    @Test
    void saveAssignedEvent() {
        when(assignedEventsService.saveAssignedEvent(any(AssignedEvents.class))).thenReturn(assignedEvents);
        assignedEventsService.saveAssignedEvent(assignedEvents);
        assertEquals(assignedEvents, assignedEventsService.saveAssignedEvent(assignedEvents));
    }
}