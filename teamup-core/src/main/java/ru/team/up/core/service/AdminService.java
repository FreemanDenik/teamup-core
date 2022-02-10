package ru.team.up.core.service;

import ru.team.up.core.entity.Account;

import java.util.List;

/**
 * @author Alexey Tkachenko
 */
public interface AdminService {
    List<Account> getAllAdmins();

    Account getOneAdmin(Long id);

    Account saveAdmin(Account admin);

    void deleteAdmin(Long id);

}
