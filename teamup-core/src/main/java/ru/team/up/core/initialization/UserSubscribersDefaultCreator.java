package ru.team.up.core.initialization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.team.up.core.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Component
@Transactional
public class UserSubscribersDefaultCreator {

    private final UserRepository userRepository;

    @Autowired
    public UserSubscribersDefaultCreator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


//    @Bean("UserSubscribersDefaultCreator")
//    public void userSubscribersDefaultCreator() {
//        userRepository.getUserById(9L).setSubscribers(Set.of(userRepository.getOne(3L)));
//        userRepository.getUserById(7L).setSubscribers(Set.of(userRepository.getOne(5L)));
//    }
}
