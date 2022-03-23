package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.team.up.core.repositories.InterestsRepository;
import ru.team.up.core.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Component
@Transactional
@Profile("cdb")
public class UserInterestsDefaultCreator {

    private final UserRepository userRepository;
    private final InterestsRepository interestsRepository;

    @Autowired
    public UserInterestsDefaultCreator(UserRepository userRepository, InterestsRepository interestsRepository) {
        this.userRepository = userRepository;
        this.interestsRepository = interestsRepository;
    }


    @Bean("UserInterestsDefaultCreator")
    public void userInterestsDefaultCreator() {
        userRepository.findUserById(2L).setUserInterests(Set.of(interestsRepository.getOne(9L)));
        userRepository.findUserById(3L).setUserInterests(Set.of(interestsRepository.getOne(2L), interestsRepository.getOne(7L)));
        userRepository.findUserById(4L).setUserInterests(Set.of(interestsRepository.getOne(10L)));
        userRepository.findUserById(5L).setUserInterests(Set.of(interestsRepository.getOne(1L)));
        userRepository.findUserById(6L).setUserInterests(Set.of(interestsRepository.getOne(2L), interestsRepository.getOne(3L),
                interestsRepository.getOne(7L), interestsRepository.getOne(11L)));
        userRepository.findUserById(7L).setUserInterests(Set.of(interestsRepository.getOne(1L), interestsRepository.getOne(9L),
                interestsRepository.getOne(13L)));
        userRepository.findUserById(8L).setUserInterests(Set.of(interestsRepository.getOne(8L), interestsRepository.getOne(12L)));
        userRepository.findUserById(9L).setUserInterests(Set.of(interestsRepository.getOne(6L), interestsRepository.getOne(7L),
                interestsRepository.getOne(11L), interestsRepository.getOne(12L)));
    }
}