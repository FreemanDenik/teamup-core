package ru.team.up.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Alexey Tkachenko
 */

public class UserNotFoundEmailException extends ResponseStatusException {

    public UserNotFoundEmailException(String email) {
        super(HttpStatus.NOT_FOUND, "Пользователь не найден. Email = " + email);
    }
}
