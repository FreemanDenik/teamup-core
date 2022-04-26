package ru.team.up.core.service;

import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.entity.Event;

import java.util.List;

public interface AssignedEventsService {

    AssignedEvents saveAssignedEvent(AssignedEvents assignedEvents);

    void removeAssignedEvent(Long id);

    List<Long> getIdNotAssignedEvents();

    List<Long> getIdAssignedEventsByModeratorId(Long id);

    List<Event> getAllEventsByModeratorId(Long id);
}
