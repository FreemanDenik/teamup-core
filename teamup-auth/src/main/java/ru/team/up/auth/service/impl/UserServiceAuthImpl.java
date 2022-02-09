//TODO Аргунов М.С. Не забыть удалить, так как данный класс по идее нигде не используется
//package ru.team.up.auth.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.stereotype.Service;
//import ru.team.up.auth.exception.IncorrectDataRegistrationException;
//import ru.team.up.auth.service.UserServiceAuth;
//import ru.team.up.core.entity.Role;
//import ru.team.up.core.entity.User;
//import ru.team.up.core.repositories.UserRepository;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Objects;
//
//
//@Service
//public class UserServiceAuthImpl implements UserServiceAuth {
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserServiceAuthImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public void saveUser(User user) throws IncorrectDataRegistrationException {
//        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
//        user.setRole(Role.ROLE_USER);
//        user.setAccountCreatedTime(LocalDate.now());
//        user.setLastAccountActivity(LocalDateTime.now());
//        userRepository.save(user);
//    }
//
//    @Override
//    public boolean checkLogin(String login) {
//        if (Objects.nonNull(userRepository.findByUsername(login))) {
//            return true;
//        }
//        return false;
//    }
//}
