package ru.team.up.input.exception;

/**
 * @author Pavel Kondrashov on 23.10.2021
 */
public class ApiCheckRequest extends RuntimeException {
    public ApiCheckRequest(String message) {
        super(message);
    }
}
