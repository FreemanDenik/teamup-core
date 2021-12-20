package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Set;


@Component
@Transactional
public class UserEventsDefaultCreator {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public UserEventsDefaultCreator(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Bean("UserEventsDefaultCreator")
    public void userEventsDefaultCreator() {
        userRepository.getUserById(3L).setUserEvent(Set.of(eventRepository.getOne(4L)));
        userRepository.getUserById(5L).setUserEvent(Set.of(eventRepository.getOne(2L)));
        userRepository.getUserById(6L).setUserEvent(Set.of(eventRepository.getOne(3L), eventRepository.getOne(4L)));
        userRepository.getUserById(7L).setUserEvent(Set.of(eventRepository.getOne(1L), eventRepository.getOne(2L)));
        userRepository.getUserById(8L).setUserEvent(Set.of(eventRepository.getOne(4L)));
    }
}
