package ru.team.up.input.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.team.up.core.entity.User;

/**
 * @author Pavel Kondrashov
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private User user;
}
