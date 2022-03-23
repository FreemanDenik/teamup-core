package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.InterestsRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Component
@Transactional
@Profile("cdb")
public class EventInterestsDefaultCreator {

    private final InterestsRepository interestsRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventInterestsDefaultCreator(InterestsRepository interestsRepository, EventRepository eventRepository) {
        this.interestsRepository = interestsRepository;
        this.eventRepository = eventRepository;
    }


    @Bean("EventInterestsDefaultCreator")
    @DependsOn({"InterestsDefaultCreator", "EventsDefaultCreator"})
    public void eventInterestsDefaultCreator() {
        interestsRepository.getOne(1L).setEvent(Set.of(eventRepository.getOne(1L), eventRepository.getOne(2L)));
        interestsRepository.getOne(2L).setEvent(Set.of(eventRepository.getOne(3L)));
        interestsRepository.getOne(7L).setEvent(Set.of(eventRepository.getOne(4L)));
    }
}
