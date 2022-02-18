package ru.team.up.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EventsNotFoundIdException extends ResponseStatusException {

    public EventsNotFoundIdException(Long id) {
        super(HttpStatus.NOT_FOUND, "Мероприятие не найдено у пользователя . Id = " + id);
    }
}
