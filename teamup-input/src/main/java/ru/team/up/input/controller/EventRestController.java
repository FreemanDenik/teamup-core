package ru.team.up.input.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.EventType;
import ru.team.up.input.exception.EventCheckException;
import ru.team.up.input.exception.EventCreateRequestException;
import ru.team.up.input.payload.request.EventRequest;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.service.EventService;
import ru.team.up.input.wordmatcher.WordMatcher;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

/**
 * REST-контроллер для мероприятий
 *
 * @author Pavel Kondrashov
 */

@Slf4j
@RestController("api/public/event")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventRestController {
    private final EventService eventService;
    private final WordMatcher wordMatcher;


    /**
     * Метод получения списка всех мероприятий
     *
     * @return Список мероприятий и статус ответа
     */
    @GetMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> getAllEvents() {
        log.debug("Получен запрос на список мероприятий");
        List<Event> events = eventService.getAllEvents();

        if (events.isEmpty()) {
            log.error("Список мероприятий пуст");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.debug("Список мероприятий получен");
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * Метод получения мероприятия по идентификатору
     *
     * @param eventId Идентификатор мероприятия
     * @return Ответ запроса и статус проверки
     */
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> findEventById(@PathVariable("id") Long eventId) {
        log.debug("Получен запрос на поиск мероприятия по id: {}", eventId);
        Optional<Event> eventOptional = Optional.ofNullable(eventService.getEventById(eventId));

        return eventOptional
                .map(event -> {
                    log.debug("Мероприятие с id: {} найдено", eventId);
                    return new ResponseEntity<>(event, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.error("Мероприятие с id: {} не найдено", eventId);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    /**
     * Метод получения мероприятий по названию
     *
     * @param eventName Название мероприятия
     * @return Ответ запроса и статус проверки
     */
    @GetMapping(value = "{eventName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> findEventsByName(@PathVariable("eventName") String eventName) {
        log.debug("Получен запрос на поиск мероприятий по названию {}", eventName);
        List<Event> events = eventService.getEventByName(eventName);

        if (events.isEmpty()) {
            log.error("Мероприятия не найдены");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.debug("Мероприятие с названием {} найдено", eventName);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * Метод получения мероприятий по автору
     *
     * @param author Автор мероприятия
     * @return Ответ запроса и статус проверки
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> findEventsByAuthor(@RequestBody UserRequest author) {
        log.debug("Получен запрос на поиск мероприятий по автору {}", author);
        List<Event> events = eventService.getAllEventsByAuthor(author.getUser());

        if (events.isEmpty()) {
            log.error("Мероприятия по указанному автору {} не найдены", author);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.debug("Мероприятия от автора {} найдены", author);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * Метод поиска мероприятия по типу
     *
     * @param eventType Тип мероприятия
     * @return Ответ запроса и статус проверки
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Event>> findEventsByType(@RequestBody EventType eventType) {
        log.debug("Получен запрос на поиск мероприятий по типу: {}", eventType);
        List<Event> events = eventService.getAllEventsByEventType(eventType);

        log.debug("Мероприятия с типом: {} найдены", eventType);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * Метод создания мероприятия
     *
     * @param event Данные мероприятия
     * @return Ответ запроса и статус проверки
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> createEvent(@RequestBody EventRequest event) {
        log.debug("Получен запрос на создание мероприятия:\n {}", event);

        checkEvent(event);

        log.debug("Мероприятие созданно");
        Event upcomingEvent = eventService.saveEvent(event.getEvent());

        return new ResponseEntity<>(upcomingEvent, HttpStatus.CREATED);
    }

    /**
     * Метод обновления мероприятия
     *
     * @param event Данные мероприятия
     * @param eventId Идентификатор мероприятия
     * @return Ответ запроса и статус проверки
     */
    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> updateEvent(@RequestBody EventRequest event, @PathVariable("id") Long eventId) {
        log.debug("Получен запрос на обновление мероприятия {}", event);

        checkEvent(event);

        log.debug("Мероприятие {} обновлено", event);
        Event newEvent = eventService.updateEvent(eventId, event.getEvent());

        return new ResponseEntity<>(newEvent, HttpStatus.OK);
    }

    /**
     * Метод удаления мероприятия по идентификатору
     *
     * @param eventId Идентификатор мероприятия
     * @return Ответ запроса и статус проверки
     */
    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> deleteEvent(@PathVariable("id") Long eventId) {
        log.debug("Получен запрос на удаление мероприятия с id: {}", eventId);
        Event event = eventService.getEventById(eventId);

        if (event == null) {
            log.error("Мероприятие с id: {} не найдено", eventId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        eventService.deleteEvent(eventId);

        log.debug("Мероприятие с id: {} успешно удалено", eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Метод добавления участника мероприятия
     *
     * @param eventId Идентификатор мероприятия
     * @param userId Идентификатор участника
     * @return Ответ запроса и статус проверки
     */
    @PostMapping(value = "join/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> addEventParticipant(@PathVariable("id") Long eventId, Long userId) {
        log.debug("Получен запрос на добавление участника мероприятия");
        Event event = eventService.addParticipant(eventId, userId);

        log.debug("Участник успешно добавлен");
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    /**
     * Метод удаления участника мероприятия
     *
     * @param eventId Идентификатор мероприятия
     * @param userId Идентификатор участника
     * @return Ответ запроса и статус проверки
     */
    @PostMapping("unjoin/{id}")
    public ResponseEntity<Event> deleteEventParticipant(@PathVariable("id") Long eventId, Long userId) {
        log.debug("Получен запрос на удаление участника мероприятия");
        Event event = eventService.deleteParticipant(eventId, userId);

        log.debug("Участник успешно удален");
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    /**
     * Метод проверки мероприятия
     *
     * @param event Данные мероприятия
     */
    private void checkEvent(EventRequest event) {
        if (wordMatcher.detectBadWords(event.getEventName()) ||
                wordMatcher.detectBadWords(event.getEvent().getDescriptionEvent())) {
            log.error("Имя или описание мероприятия содержит запрещенные слова:\n {}", event);
            throw new EventCreateRequestException("Имя или описание мероприятия содержит запрещенные слова");
        }

        if (Period.between(event.getDate(), LocalDate.now()).getYears() >= 1) {
            log.error("Дата создания мероприятия более 1 года:\n {}", event);
            throw new EventCreateRequestException("Дата создания мероприятия более 1 года");
        }

        if (wordMatcher.detectUnnecessaryWords(event.getEvent().getEventName()) ||
                wordMatcher.detectUnnecessaryWords(event.getEvent().getDescriptionEvent())) {
            log.debug("Мероприятие отправленно на проверку:\n {}", event);
            throw new EventCheckException("Мероприятие отправленно на проверку");
        }
    }
}
