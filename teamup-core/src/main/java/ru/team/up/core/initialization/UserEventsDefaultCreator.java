package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Component
@Transactional
@Profile("cdb")
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
        userRepository.findUserById(3L).setUserEvent(Set.of(eventRepository.getOne(4L)));
        userRepository.findUserById(5L).setUserEvent(Set.of(eventRepository.getOne(2L)));
        userRepository.findUserById(6L).setUserEvent(Set.of(eventRepository.getOne(3L), eventRepository.getOne(4L)));
        userRepository.findUserById(7L).setUserEvent(Set.of(eventRepository.getOne(1L), eventRepository.getOne(2L)));
        userRepository.findUserById(8L).setUserEvent(Set.of(eventRepository.getOne(4L)));
    }
}
