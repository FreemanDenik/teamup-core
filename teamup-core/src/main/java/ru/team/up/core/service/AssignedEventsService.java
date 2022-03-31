package ru.team.up.core.service;

import ru.team.up.core.entity.AssignedEvents;

import java.util.List;

public interface AssignedEventsService {

    AssignedEvents saveAssignedEvent(AssignedEvents assignedEvents);

    void removeAssignedEvent(Long id);

    List<Long> getIdNotAssignedEvents();

    List<Long> getIdAssignedEventsByModeratorId(Long id);
}
