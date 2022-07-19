package ru.team.up.core.service;

import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Alexey Tkachenko
 */
public interface EventService {
    List<Event> getAllEvents();

    List<Event> getAllByAuthorId(Long authorId);

    List<Event> getAllEventsByCity(String city);

    Event getOneEvent(Long id);

    Event saveEvent(Event event);

    Event updateEvent(Event event);

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

    void incrementCountViewEvent(Long id);

    List<Event> getAllEventsBySubscriberId(Long subscriberId);

    /**
     * @author Nail Faizullin, Dmitry Koryanov
     * @param startDateTime Время события мероприятия от
     * @param endDateTime Время события мероприятия до
     *           Метод получает map-у с предстоящими в указанном интервале событиями и пользователями, которые
     *                    участвуют в этих событиях
     */
    Map<Event, List<User>> getEventsUsers(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
