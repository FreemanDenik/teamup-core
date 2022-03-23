package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.team.up.core.entity.EventType;
import ru.team.up.core.repositories.EventTypeRepository;

import javax.transaction.Transactional;

@Component
@Transactional
@Profile("cdb")
public class EventTypeDefaultCreator {

    private final EventTypeRepository eventTypeRepository;

    @Autowired
    public EventTypeDefaultCreator(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    @Bean("EventsTypeDefaultCreator")
    public void eventsTypeDefaultCreator() {
        eventTypeRepository.save(EventType.builder()
                .id(1L)
                .type("Встреча")
                .build());

        eventTypeRepository.save(EventType.builder()
                .id(2L)
                .type("Выставка")
                .build());

        eventTypeRepository.save(EventType.builder()
                .id(3L)
                .type("Хакатон")
                .build());

        eventTypeRepository.save(EventType.builder()
                .id(4L)
                .type("Спорт")
                .build());

        eventTypeRepository.save(EventType.builder()
                .id(5L)
                .type("Танцы")
                .build());

        eventTypeRepository.save(EventType.builder()
                .id(6L)
                .type("Экскурсия")
                .build());
    }
}
