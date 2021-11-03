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
import ru.team.up.input.entity.Event;
import ru.team.up.input.exception.EventCheckException;
import ru.team.up.input.exception.EventCreateRequestException;
import ru.team.up.input.payload.request.CreateEventRequest;
import ru.team.up.input.service.EventService;
import ru.team.up.input.wordmatcher.WordMatcher;

import java.time.LocalDate;
import java.time.Period;

/**
 * @author Pavel Kondrashov on 23.10.2021
 *
 *
 */

@Slf4j
@RestController("api/events/private")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventRestController {
    private final EventService eventService;
    private final WordMatcher wordMatcher;

    @PostMapping(value = "/event", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> createEvent(@RequestBody CreateEventRequest event) {
        log.debug("Получен запрос на создание мероприятия:\n {}", event.getEvent());

        if (wordMatcher.detectBadWords(event.getEvent().getEventName()) ||
                wordMatcher.detectBadWords(event.getEvent().getEventDescription())) {
            log.error("Имя или описание мероприятия содержит запрещенные слова:\n {}", event.getEvent());
            throw new EventCreateRequestException("Имя или описание мероприятия содержит запрещенные слова");
        }

        if (Period.between(event.getEvent().getDate(), LocalDate.now()).getYears() >= 1) {
            log.error("Дата создания мероприятия более 1 года:\n {}", event.getEvent());
            throw new EventCreateRequestException("Дата создания мероприятия более 1 года");
        }

        if (wordMatcher.detectUnnecessaryWords(event.getEvent().getEventName()) ||
                wordMatcher.detectUnnecessaryWords(event.getEvent().getEventDescription())) {
            log.debug("Мероприятие отправленно на проверку:\n {}", event.getEvent());
            throw new EventCheckException("Мероприятие отправленно на проверку");
        }

        log.debug("Мероприятие созданно");
        Event upcomingEvent = eventService.saveEvent(event.getEvent());

        return new ResponseEntity<>(upcomingEvent, HttpStatus.CREATED);
    }
}
