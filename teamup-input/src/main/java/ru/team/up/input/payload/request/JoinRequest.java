package ru.team.up.input.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.team.up.core.entity.Event;

/**
 * @author Pavel Kondrashov
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {
    private Long userId;
    private Long eventId;
}
