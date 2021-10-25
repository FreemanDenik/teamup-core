package ru.team.up.input.controller;

import lombok.AllArgsConstructor;
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
import ru.team.up.input.service.EventService;
import ru.team.up.input.wordmatcher.WordMatcher;

import java.time.LocalDate;
import java.time.Period;

/**
 * @author Pavel Kondrashov on 23.10.2021
 */

@RestController("api/events/private")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventRestController {
    private final EventService eventService;
    private final WordMatcher wordMatcher;

    @PostMapping(value ="/event", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        if (wordMatcher.detectBadWords(event.getEventName()) ||
                wordMatcher.detectBadWords(event.getEventDescription())) {
            throw new EventCreateRequestException("Имя или описание мероприятия содержит запрещенные слова");
        }

        if (Period.between(event.getDate(), LocalDate.now()).getYears() >= 1) {
            throw new EventCreateRequestException("Дата создания мероприятия более 1 года");
        }

        if (wordMatcher.detectUnnecessaryWords(event.getEventName()) ||
                wordMatcher.detectUnnecessaryWords(event.getEventDescription())) {
            throw new EventCheckException("Мероприятие отправленно на проверку");
        }

        Event upcomingEvent = eventService.saveEvent(event);
        return new ResponseEntity<>(upcomingEvent, HttpStatus.CREATED);
    }
}
