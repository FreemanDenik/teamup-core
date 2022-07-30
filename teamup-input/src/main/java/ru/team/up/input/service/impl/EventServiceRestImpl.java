package ru.team.up.input.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.EventType;
import ru.team.up.core.entity.User;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.UserRepository;
import ru.team.up.core.service.NotifyService;
import ru.team.up.dto.NotifyDto;
import ru.team.up.dto.NotifyStatusDto;
import ru.team.up.input.service.EventServiceRest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author Pavel Kondrashov
 */

@Slf4j
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceRestImpl implements EventServiceRest {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private NotifyService notifyService;


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
    public List<Event> getAllEventsByAuthor(Long authorId) {
        return eventRepository.findAllByAuthorId(authorId);
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

        log.debug("Отправка уведомления создателю c ID: {} для мероприятия с ID: {}", event.getAuthorId(), eventId);

        String message = "Пользователь " + participant.getUsername()
                + " стал участником мероприятия " + event.getEventName();

        notifyService.notify(NotifyDto.builder()
                .email(event.getAuthorId().getEmail())
                .subject(message)
                .text(message)
                .status(NotifyStatusDto.NOT_SENT)
                .creationTime(LocalDateTime.now())
                .build());

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

    @Override
    public List<Event> getAllEventsByCity(String city) {
        return eventRepository.findAllByCity(city);
    }

    @Override
    public List<Event> getAllEventsByCityByLimit(String city, int limit) {
        return eventRepository.findAllByCityByLimit(city, limit);
    }
}
