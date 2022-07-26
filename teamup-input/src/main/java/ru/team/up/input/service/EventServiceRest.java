package ru.team.up.input.service;

import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.EventType;

import java.util.List;

/**
 * Сервис для поиска, создания, обновления, удаления мероприятий
 *
 * @author Pavel Kondrashov on 23.10.2021
 */
public interface EventServiceRest {

    /**
     * Метод для поиска мероприятия по идентификатору
     *
     * @param id Идентификатор мероприятия
     * @return Мероприятие по заданному идентификатору
     */
    Event getEventById(Long id);

    /**
     * Метод для получния мероприятия по названию
     *
     * @param eventName Название мероприятия
     * @return Мероприятие по заданному названию
     */
    List<Event> getEventByName(String eventName);

    /**
     * Метод для получения списка мероприятий
     *
     * @return Список мероприятий
     */
    List<Event> getAllEvents();

    /**
     * Метод получения мероприятий по автору
     *
     * @param authorId ID Автора(создателя) мероприятия
     * @return Список мероприятий по автору
     */
    List<Event> getAllEventsByAuthor(Long authorId);

    /**
     * Метод получения мероприятий по типу
     *
     * @param eventType тип мероприятия
     * @return Список мероприятий
     */
    List<Event> getAllEventsByEventType(EventType eventType);

    /**
     * Метод сохранения\создания мероприятия
     *
     * @param event Мероприятие
     * @return Сохранненное мероприятие
     */
    Event saveEvent(Event event);

    /**
     * Метод обновления мероприятия
     *
     * @param id    Идентификатор мероприятия
     * @param event Мероприятие для изменений
     * @return Обновленное мероприятие
     */
    Event updateEvent(Long id, Event event);

    /**
     * Метод для удаления мероприятия по идентификатору
     *
     * @param id Идентификатор мероприятия
     */
    void deleteEvent(Long id);

    /**
     * Метод добавления участника мероприятия
     *
     * @param eventId Идентификатор мероприятия
     * @param userId  Идентификатор участника
     * @return Обновленное мероприятие
     */
    Event addParticipant(Long eventId, Long userId);

    /**
     * Метод удаления участника мероприятия
     *
     * @param eventId Идентификатор мероприятия
     * @param userId  Идентификатор участника
     * @return Обновленное мероприятие
     */
    Event deleteParticipant(Long eventId, Long userId);

    /**
     * @param city город проведения мероприятий
     * @return Получение всех мероприятий в городе
     */
    List<Event> getAllEventsByCity(String city);

    /**
     * @param city город проведения мероприятий
     * @param limit лимит на отображение
     * @return Получение всех мероприятий в городе с лимитом отображение на страницу
     */
    List<Event> getAllEventsByCityByLimit(String city, int limit);
}
