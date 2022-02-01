package ru.team.up.core.service;

import ru.team.up.core.entity.AssignedEvents;

public interface AssignedEventsService {

    AssignedEvents getAssignedEvent(Long id);
    AssignedEvents saveAssignedEvent(AssignedEvents assignedEvents);
    void removeAssignedEvent(Long id);
}
