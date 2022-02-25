package ru.team.up.core.service;

import org.springframework.data.jpa.repository.Query;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.User;

import java.util.List;

/**
 * @author Alexey Tkachenko
 */
public interface EventService {
    List<Event> getAllEvents();

    List<Event> getAllByAuthorId(Long authorId);

    List<Event> getAllEventsByCity(String city);

    Event getOneEvent(Long id);

    Event saveEvent(Event event);

    void deleteEvent(Long id);

    void addParticipantEvent(Long Id, User user);

    /**
     * @param eventId Уникальный ключ ID мероприятия
     *           Метод меняет статус мероприятия на одобренный
     */
    void eventApprovedByModerator(Long eventId);

    /**
     * @param eventId Уникальный ключ ID мероприятия
     *           Метод меняет стутус мероприятия на закрытый модератором
     */
    void eventClosedByModerator(Long eventId);

    void updateNumberOfViews(Long id);

    List<Event> getAllEventsBySubscriberId(Long subscriberId);
}
