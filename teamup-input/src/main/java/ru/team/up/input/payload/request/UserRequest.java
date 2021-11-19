package ru.team.up.input.payload.request;

import lombok.Builder;
import lombok.Data;
import ru.team.up.core.entity.User;

/**
 * Класс для запроса сущности пользователя
 *
 * @author Pavel Kondrashov
 */

@Data
@Builder
public class UserRequest {

    /**
     * Сущность пользователя
     */
    private User user;
}
