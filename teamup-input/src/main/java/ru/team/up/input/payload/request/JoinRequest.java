package ru.team.up.input.payload.request;

import lombok.Builder;
import lombok.Data;

/**
 * Класс для запроса идентификатора пользователя и мероприятия
 *
 * @author Pavel Kondrashov
 */

@Data
@Builder
public class JoinRequest {

    /**
     * Идентификатор пользователя
     */
    private Long userId;

    /**
     * Идентификатор мероприятия
     */
    private Long eventId;
}
