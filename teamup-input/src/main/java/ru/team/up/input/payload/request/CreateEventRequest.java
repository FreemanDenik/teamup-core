package ru.team.up.input.payload.request;

import lombok.Builder;
import lombok.Data;
import ru.team.up.input.entity.Event;

/**
 * @author Pavel Kondrashov on 27.10.2021
 */

@Data
@Builder
public class CreateEventRequest {
    private Event event;
}
