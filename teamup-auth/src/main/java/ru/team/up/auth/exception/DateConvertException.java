package ru.team.up.auth.exception;

import java.time.DateTimeException;

public class DateConvertException extends DateTimeException {
    public DateConvertException(String message) {
        super(message);
    }
}
