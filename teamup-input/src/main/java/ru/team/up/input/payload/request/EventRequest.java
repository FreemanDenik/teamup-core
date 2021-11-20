package ru.team.up.input.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.team.up.core.entity.Event;

/**
 * Класс для запроса сущности мероприятия
 *
 * @author Pavel Kondrashov
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    /**
     * Сущность мероприятия
     */
    private Event event;
}
