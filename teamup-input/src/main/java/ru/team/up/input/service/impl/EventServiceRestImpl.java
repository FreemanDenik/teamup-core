package ru.team.up.input.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.EventType;
import ru.team.up.core.entity.User;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.UserRepository;
import ru.team.up.input.service.EventServiceRest;

import java.util.List;
import java.util.Set;

/**
 * @author Pavel Kondrashov
 */

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceRestImpl implements EventServiceRest {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;


    @Override
    public Event getEventById(Long id) {
        return eventRepository.getOne(id);
    }

    @Override
    public List<Event> getEventByName(String eventName) {
        return eventRepository.findByEventNameContaining(eventName);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getAllEventsByAuthor(User author) {
        return eventRepository.findAllByAuthorId(author);
    }

    @Override
    public List<Event> getAllEventsByEventType(EventType eventType) {
        return eventRepository.findAllByEventType(eventType);
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        return eventRepository.saveAndFlush(event);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Event addParticipant(Long eventId, Long userId) {
        Event event = getEventById(eventId);
        Account participant = userRepository.getOne(userId);
        Set<User> participants = event.getParticipantsEvent();
        participants.add((User) participant);
        event.setParticipantsEvent(participants);
        return updateEvent(eventId, event);
    }

    @Override
    public Event deleteParticipant(Long eventId, Long userId) {
        Event event = getEventById(eventId);
        Account participant = userRepository.getOne(userId);
        Set<User> participants = event.getParticipantsEvent();
        participants.remove((User) participant);
        event.setParticipantsEvent(participants);
        return updateEvent(eventId, event);
    }
}
