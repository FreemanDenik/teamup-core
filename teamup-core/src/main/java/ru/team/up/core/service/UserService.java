package ru.team.up.core.service;

import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Alexey Tkachenko
 */
public interface UserService {
    List<User> getAllUsers();

    Optional<User> getOneUser(Long id);

    User saveUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> getTopUsersInCity(String city);

    List<User> getAllUsersByCityByLimit(String city, int limit);
}
