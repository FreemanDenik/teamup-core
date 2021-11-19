package ru.team.up.input.payload.response;

import lombok.Builder;
import lombok.Value;

/**
 * Класс ответа на создание мероприятия
 *
 * @author Pavel Kondrashov on 27.10.2021
 */

@Value
@Builder
public class CreateEventResponse {

    /**
     * Сообщение ответа
     */
    String message;

    /**
     * Статус ответа
     */
    String status;
}
