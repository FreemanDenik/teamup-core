package ru.team.up.input.payload.request;

import lombok.Builder;
import lombok.Data;
import ru.team.up.core.entity.Event;

import java.time.LocalDate;

/**
 * @author Pavel Kondrashov
 */

@Data
@Builder
public class EventRequest {
    private Event event;

    public String getEventName() {
        return null;
    }

    public LocalDate getDate() {
        return null;
    }
}
