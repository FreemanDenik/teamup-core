package ru.team.up.input.payload.response;

import lombok.Builder;
import lombok.Value;

/**
 * Класс
 * @author Pavel Kondrashov on 27.10.2021
 */

@Value
@Builder
public class CreateEventResponse {
    String message;
    String status;
}
