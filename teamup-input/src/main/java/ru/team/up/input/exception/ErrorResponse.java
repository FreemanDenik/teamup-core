package ru.team.up.input.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Pavel Kondrashov on 23.10.2021
 */

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private final String message;
    private final String status;
}
