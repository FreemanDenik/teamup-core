package ru.team.up.input.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.service.AdminService;
import ru.team.up.input.service.AdminServiceRest;
import java.util.List;

/**
 * Сервис для работы с админами
 * специально для использования в контроллере
 *
 */

@Service
@AllArgsConstructor
public class AdminServiceRestImpl implements AdminServiceRest {

    private AdminService adminService;


    @Override
    public Account getAdminById(Long id) {
        return adminService.getOneAdmin(id);
    }

    @Override
    public List<Account> getAllAdmin() {
        return adminService.getAllAdmins();
    }

    @Override
    public Account saveAdmin(Account admin) {
        return adminService.saveAdmin(admin);
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        return adminService.updateAdmin(admin);
    }

    @Override
    public void deleteAdmin(Long id) {
        adminService.deleteAdmin(id);

    }
}
