package ru.team.up.input.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.User;
import ru.team.up.core.repositories.UserRepository;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.service.UserServiceRest;

import java.util.List;

/**
 * Сервис для работы с пользователями
 *
 * @author Pavel Kondrashov
 */

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceRestImpl implements UserServiceRest {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(UserRequest user, Long id) {
        User oldUser = getUserById(id);
        user.getUser().setId(oldUser.getId());
        userRepository.saveAndFlush(user.getUser());
        return getUserById(id);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
