package ru.team.up.core.service;

import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;

import java.util.List;

/**
 * @author Alexey Tkachenko
 */
public interface UserService {
    List<Account> getAllUsers();

    Account getOneUser(Long id);

    Account saveUser(Account user);

    void deleteUser(Long id);
}
