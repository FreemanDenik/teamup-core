package ru.team.up.input.exception;

/**
 * @author Pavel Kondrashov on 23.10.2021
 */
public class ApiCreateRequestException extends RuntimeException {
    public ApiCreateRequestException(String message) {
        super(message);
    }
}
