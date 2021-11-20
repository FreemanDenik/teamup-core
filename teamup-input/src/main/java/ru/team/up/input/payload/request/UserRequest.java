package ru.team.up.input.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.team.up.core.entity.User;

/**
 * Класс для запроса сущности пользователя
 *
 * @author Pavel Kondrashov
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    /**
     * Сущность пользователя
     */
    private User user;
}
