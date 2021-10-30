package ru.team.up.input.service;

import ru.team.up.input.entity.Event;

/**
 * @author Pavel Kondrashov on 23.10.2021
 */
public interface EventService {
    Event saveEvent(Event event);
}
