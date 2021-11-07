package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Event;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.exception.UserNotFoundException;
import ru.team.up.core.repositories.EventRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Alexey Tkachenko
 * <p>
 * Класс сервиса для управления пользователями ru.team.up.core.entity.Moderator
 */

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;

    /**
     * @return Возвращает коллекцию Event.
     * Если коллекция пуста, генерирует исключение со статусом HttpStatus.NO_CONTENT
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> getAllEvents() {
        log.debug("Старт метода List<Event> getAllEvents()");

        List<Event> events = Optional.of(eventRepository.findAll())
                .orElseThrow(NoContentException::new);
        log.debug("Получили список всех мероприятий из БД {}", events);

        return events;
    }

    /**
     * @param id Уникальный ключ ID мероприятия
     * @return Находит в БД мероприятие по ID и возвращает его.
     * Если мероприятие с переданным ID не найдено в базе, генерирует исключение со статусом HttpStatus.NOT_FOUND
     */
    @Override
    @Transactional(readOnly = true)
    public Event getOneEvent(Long id) {
        log.debug("Старт метода Event getOneEvent(Long id) с параметром {}", id);

        Event event = Optional.of(eventRepository.getById(id))
                .orElseThrow(() -> new UserNotFoundException(id));
        log.debug("Получили мероприятие из БД {}", event);

        return event;
    }

    /**
     * @param event Объект класса ru.team.up.core.entity.Event
     * @return Возвращает сохраненный в БД объект event
     */
    @Override
    @Transactional
    public Event saveEvent(Event event) {
        log.debug("Старт метода Event saveEvent(Event event) с параметром {}", event);

        Event save = eventRepository.save(event);
        log.debug("Сохранили мероприятие в БД {}", save);

        return save;
    }

    /**
     * @param event Объект класса ru.team.up.core.entity.Event
     *              Метод удаляет мероприятие из БД
     */
    @Override
    @Transactional
    public void deleteEvent(Event event) {
        log.debug("Старт метода void deleteEvent(Event event) с параметром {}", event);

        eventRepository.delete(event);
        log.debug("Удалили мероприятие из БД {}", event);
    }
}
