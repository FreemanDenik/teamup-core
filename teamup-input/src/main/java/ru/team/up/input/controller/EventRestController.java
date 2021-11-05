package ru.team.up.input.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.team.up.core.entity.Event;
import ru.team.up.input.exception.EventCheckException;
import ru.team.up.input.exception.EventCreateRequestException;
import ru.team.up.input.payload.request.EventRequest;
import ru.team.up.input.service.EventService;
import ru.team.up.input.wordmatcher.WordMatcher;

import java.time.LocalDate;
import java.time.Period;

/**
 * REST-контроллер для мероприятий
 * @author Pavel Kondrashov
 */

@Slf4j
@RestController("api/public/event")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventRestController {
    private final EventService eventService;
    private final WordMatcher wordMatcher;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> createEvent(@RequestBody EventRequest event) {
        log.debug("Получен запрос на создание мероприятия:\n {}", event);

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

        log.debug("Мероприятие созданно");
        Event upcomingEvent = eventService.saveEvent(event.getEvent());

        return new ResponseEntity<>(upcomingEvent, HttpStatus.CREATED);
    }
}
