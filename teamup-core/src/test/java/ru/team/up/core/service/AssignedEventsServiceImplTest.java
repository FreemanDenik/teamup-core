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
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        assignedEvents = AssignedEvents.builder()
                .eventId(1L)
                .moderatorId(1L)
                .build();
    }

    List<Long> assignedEventsList = new ArrayList<>();

    // TODO Не проходит из-за финальных полей (убрать)
    @Test
    void removeAssignedEvent() {
        assignedEventsList.add(assignedEvents.getId());
        when(assignedEventsRepository.getIdAssignedEventsByModeratorId(1L)).thenReturn(assignedEventsList);
        assignedEventsService.removeAssignedEvent(1L);
        verify(assignedEventsRepository, times(1)).deleteById(1L);
    }

    // TODO Не проходит из-за финальных полей (убрать)
    @Test
    void saveAssignedEvent() {
        when(assignedEventsRepository.saveAndFlush(assignedEvents)).thenReturn(assignedEvents);
        assertEquals(assignedEvents, assignedEventsService.saveAssignedEvent(assignedEvents));
    }
}