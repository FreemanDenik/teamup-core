package ru.team.up.moderator.service;

import org.springframework.data.repository.query.Param;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.entity.Event;
import ru.team.up.core.repositories.AssignedEventsRepository;
import ru.team.up.moderator.sheduleds.AssignEvents;

import java.util.List;

public interface AssignedEventsService {

    List<Event> getEventsForChecking();

    List<Integer> getEventsIds(@Param("eventList") List<Event> eventList);

    List<AssignedEvents> getAllEventsByModeratorId(@Param("moderatorId") Long id);

    void updateEventIdBeforeDeleting(@Param("statusId") Long statusId,
                                     @Param("eventId") Long eventId);

    AssignedEvents getAssignedEvent(Long id);

    AssignedEvents saveAssignedEvent(AssignedEvents assignedEvents);

    void removeAssignedEvent(Long id);

    /////
    List<AssignedEvents> getAssignedEvents();
    //////
}
