package ru.team.up.core.service;

import org.springframework.data.repository.query.Param;
import ru.team.up.core.entity.AssignedEvents;

import java.util.List;

public interface AssignedEventsService {

    AssignedEvents getAssignedEvent(Long id);

    AssignedEvents saveAssignedEvent(AssignedEvents assignedEvents);

    void removeAssignedEvent(Long id);

    List<Long> getIdNotAssignedEvents();

    List<Long> getIdAssignedEventsByModeratorId(Long id);
}
