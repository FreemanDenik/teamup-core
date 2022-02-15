package ru.team.up.moderator.service;

import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.repositories.AssignedEventsRepository;

public interface AssignedEventsService  extends AssignedEventsRepository {
    AssignedEvents getAssignedEvent(Long id);
    AssignedEvents saveAssignedEvent(AssignedEvents assignedEvents);
    void removeAssignedEvent(Long id);
}
