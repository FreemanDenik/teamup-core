package ru.team.up.input.service;

import ru.team.up.core.entity.Event;
import ru.team.up.input.payload.request.EventRequest;

import java.util.List;

/**
 * @author Pavel Kondrashov on 23.10.2021
 */
public interface EventService {

    Event getEventById(Long id);

    List<Event> getEventByName(String eventName);

    List<Event> getAllEvents();

    List<Event> getAllEventsByAuthor(Event author);

    List<Event> getAllEventsByEventType(Event eventType);

    Event saveEvent(Event event);

    Event updateEvent(Long id, Event event);

    void deleteEvent(Long id);

    Event addParticipant(Long eventId, Long userId);

    Event deleteParticipant(Long eventId, Long userId);
}
