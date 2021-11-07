package ru.team.up.core.service;

import ru.team.up.core.entity.Admin;

import java.util.List;

/**
 * @author Alexey Tkachenko
 */
public interface AdminService {
    List<Admin> getAllAdmins();

    Admin getOneAdmin(Long id);

    Admin saveAdmin(Admin admin);

    void deleteAdmin(Admin admin);
}
