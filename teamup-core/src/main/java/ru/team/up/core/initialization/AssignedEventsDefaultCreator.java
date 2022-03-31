package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.repositories.*;

import javax.transaction.Transactional;

@Component
@Transactional
@Profile("cdb")
public class AssignedEventsDefaultCreator {

    private final AssignedEventsRepository assignedEventsRepository;

    @Autowired
    public AssignedEventsDefaultCreator(AssignedEventsRepository assignedEventsRepository) {
        this.assignedEventsRepository = assignedEventsRepository;
    }


    @Bean("AssignedEventsDefaultCreator")
    public void assignedEventsDefaultCreator() {

        assignedEventsRepository.save(AssignedEvents.builder()
                .eventId(3L)
                .moderatorId(21L)
                .build());

    }
}
