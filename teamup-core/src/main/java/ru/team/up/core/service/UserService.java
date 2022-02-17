package ru.team.up.core.service;

import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Alexey Tkachenko
 */
public interface UserService {
    List<Account> getAllUsers();

    Optional<Account> getOneUser(Long id);

    Account saveUser(Account user);

    void deleteUser(Long id);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsername(String username);

    List<Account> getTopUsersInCity(String city);
}
