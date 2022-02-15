package ru.team.up.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Alexey Tkachenko
 */

public class UserNotFoundUsernameException extends ResponseStatusException {

    public UserNotFoundUsernameException(String username) {
        super(HttpStatus.NOT_FOUND, "Пользователь не найден. Username = " + username);
    }
}
