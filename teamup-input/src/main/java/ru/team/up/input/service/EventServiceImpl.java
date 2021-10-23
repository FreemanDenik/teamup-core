package ru.team.up.input.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team.up.input.entity.Event;
import ru.team.up.input.repository.EventRepository;

/**
 * @author Pavel Kondrashov on 23.10.2021
 */

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }
}
