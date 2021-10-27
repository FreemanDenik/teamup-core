package ru.team.up.input.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.team.up.input.entity.Event;

/**
 * @author Pavel Kondrashov on 27.10.2021
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateEventRequest {
    private Event event;
}
