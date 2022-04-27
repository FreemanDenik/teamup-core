package ru.team.up.core.service;

import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Admin;

import java.util.List;

/**
 * @author Alexey Tkachenko
 */
public interface AdminService {
    List<Account> getAllAdmins();

    Account getOneAdmin(Long id);

    Account saveAdmin(Account admin);

    Admin updateAdmin(Admin admin);

    void deleteAdmin(Long id);

    boolean existsById(Long id);
}
