package ru.team.up.input.payload.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author Pavel Kondrashov
 */

@Data
@Builder
public class JoinRequest {
    private Long userId;

    private Long eventId;
}
