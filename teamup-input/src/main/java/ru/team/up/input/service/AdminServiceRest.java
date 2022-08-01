package ru.team.up.input.service;

import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Admin;

import java.util.List;

/**
 * Интерфейс для поиска, обновления и удаления админов
 *
 */
public interface AdminServiceRest {

    Account getAdminById (Long id);

    List<Account> getAllAdmin();
    Account saveAdmin (Account admin);

    Admin updateAdmin(Admin admin);

    void deleteAdmin (Long id);

}
