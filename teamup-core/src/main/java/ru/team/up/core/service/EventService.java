package ru.team.up.core.service;

import ru.team.up.core.entity.Event;

import java.util.List;

/**
 * @author Alexey Tkachenko
 */
public interface EventService {
    List<Event> getAllEvents();

    Event getOneEvent(Long id);

    Event saveEvent(Event event);

    void deleteEvent(Long id);
}
