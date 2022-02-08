package ru.team.up.core.service;

import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;

import java.util.List;

/**
 * @author Alexey Tkachenko
 */
public interface UserService {
    List<User> getAllUsers();

    User getOneUser(Long id);

    User saveUser(User user);

    void deleteUser(Long id);

}
