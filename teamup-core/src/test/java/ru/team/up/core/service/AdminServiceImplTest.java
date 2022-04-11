package ru.team.up.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.entity.Role;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.repositories.AccountRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AdminService adminService;

    private Account admin;

    @BeforeEach
    public void setUpEntity() {
        admin = Account.builder()
                .email("adminka@mail.ru")
                .username("NeoTheAdmin")
                .firstName("Neo")
                .lastName("Neon")
                .password("Neo12345")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .role(Role.ROLE_ADMIN)
                .build();
    }

    @Test
    @Transactional
    void saveAdmin() {
        // Добавление данных
        adminService.saveAdmin(admin);
        // Проверка наличия данных после добавления
        assertNotNull(adminService.getAllAdmins());
        // Попытка добавления некорректных данных
        assertThrows(DataIntegrityViolationException.class, ()-> adminService.saveAdmin(Admin.builder().build()));
    }

    @Test
    @Transactional
    void getAllAdmins() {
        // Проверка выброса исключения при отсутствии данных
        assertThrows(NoContentException.class, ()-> adminService.getAllAdmins());
        // Добавление данных
        adminService.saveAdmin(admin);
        // Проверка наличия данных после добавления
        assertNotNull(adminService.getAllAdmins());
        // Проверка корректности данных
        assertEquals("Neo", adminService.getAllAdmins().get(0).getFirstName());
    }

    @Test
    @Transactional
    void getOneAdmin() {
        // Проверка выброса исключения при отсутствии данных
        assertThrows(UserNotFoundIDException.class, ()-> adminService.getOneAdmin(1L));
        // Добавление данных
        adminService.saveAdmin(admin);
        // Проверка наличия данных после добавления
        assertNotNull(adminService.getOneAdmin(admin.getId()));
        // Проверка ввода некорректных данных
        assertThrows(UserNotFoundIDException.class, ()-> adminService.getOneAdmin(44L));
        // Проверка корректности данных
        assertEquals("Neo", adminService.getOneAdmin(admin.getId()).getFirstName());
    }

    @Test
    @Transactional
    void deleteAdmin() {
        // Добавление данных
        adminService.saveAdmin(admin);
        // Проверка наличия данных после добавления
        assertNotNull(adminService.getAllAdmins());
        // Проверка попытки удаления некорректных данных
        assertThrows(UserNotFoundIDException.class, ()-> adminService.deleteAdmin(44L));
        // Удаление корректных данных
        adminService.deleteAdmin(1L);
        // Проверка удаления данных
        assertThrows(NoContentException.class, ()-> adminService.getAllAdmins());
    }
}