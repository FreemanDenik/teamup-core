package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.team.up.core.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Component
@Transactional
@Profile("cdb")
public class UserSubscribersDefaultCreator {

    private final UserRepository userRepository;

    @Autowired
    public UserSubscribersDefaultCreator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Bean("UserSubscribersDefaultCreator")
    public void userSubscribersDefaultCreator() {
        userRepository.findUserById(4L).setSubscribers(Set.of(userRepository.findUserById(3L)));
        userRepository.findUserById(7L).setSubscribers(Set.of(userRepository.findUserById(5L), userRepository.findUserById(6L), userRepository.findUserById(3L)));
        userRepository.findUserById(10L).setSubscribers(Set.of(userRepository.findUserById(5L), userRepository.findUserById(6L), userRepository.findUserById(3L), userRepository.findUserById(9L)));
        userRepository.findUserById(11L).setSubscribers(Set.of(userRepository.findUserById(10L), userRepository.findUserById(2L)));
        userRepository.findUserById(13L).setSubscribers(Set.of(userRepository.findUserById(12L), userRepository.findUserById(10L)));
        userRepository.findUserById(15L).setSubscribers(Set.of(userRepository.findUserById(11L), userRepository.findUserById(3L)));
    }
}
