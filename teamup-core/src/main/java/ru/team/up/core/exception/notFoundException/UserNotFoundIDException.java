package ru.team.up.core.exception.notFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Alexey Tkachenko
 */

public class UserNotFoundIDException extends ResponseStatusException {

    public UserNotFoundIDException(Long id) {
        super(HttpStatus.NOT_FOUND, "Пользователь не найден. ID = " + id);
    }
}
