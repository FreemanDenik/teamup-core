package ru.team.up.input.payload.request;

import lombok.Builder;
import lombok.Data;
import ru.team.up.core.entity.Event;

/**
 * @author Pavel Kondrashov
 */

@Data
@Builder
public class EventRequest {
    private Event event;
}
