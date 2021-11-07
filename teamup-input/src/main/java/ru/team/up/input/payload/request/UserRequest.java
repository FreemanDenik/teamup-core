package ru.team.up.input.payload.request;

import lombok.Builder;
import lombok.Data;
import ru.team.up.core.entity.User;

/**
 * @author Pavel Kondrashov
 */

@Data
@Builder
public class UserRequest {
    private User user;
}
