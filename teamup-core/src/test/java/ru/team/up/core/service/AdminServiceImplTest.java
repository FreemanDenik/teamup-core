package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.entity.Role;
import ru.team.up.core.entity.User;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.repositories.AccountRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class AdminServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private AdminService adminService = new AdminServiceImpl(accountRepository, encoder);

    private Account admin;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

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
    void saveAdmin() {
        Account newAdmin = new Admin();
        log.debug("*** Добавление мока наличия данных ***");
        when(accountRepository.save(any(Account.class))).thenReturn(newAdmin);
        log.debug("↓ Проверка соответствия данных ↓");
        assertEquals(newAdmin, adminService.saveAdmin(admin));
        log.debug("↓ так должно работать? ↓");
        assertEquals(newAdmin, adminService.saveAdmin(new User()));
    }

    @Test
    void getAllAdmins() {
        log.debug("↓ Проверка выброса исключения при отсутствии данных ↓");
        assertThrows(NoContentException.class, ()-> adminService.getAllAdmins());
        log.debug("*** Добавление мока наличия данных ***");
        List<Account> admins = new ArrayList<>();
        admins.add(admin);
        when(accountRepository.findAllByRole(Role.ROLE_ADMIN)).thenReturn(admins);
        log.debug("↓ Проверка наличия данных ↓");
        assertNotNull(adminService.getAllAdmins());
        log.debug("↓ Проверка корректности данных ↓");
        assertEquals("Neo", adminService.getAllAdmins().get(0).getFirstName());
    }

    @Test
    void getOneAdmin() {
        log.debug("↓ Проверка выброса исключения при отсутствии данных ↓");
        assertThrows(UserNotFoundIDException.class, ()-> adminService.getOneAdmin(1L));
        log.debug("*** Добавление мока наличия данных ***");
        when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(admin));
        log.debug("↓ Проверка наличия данных ↓");
        assertNotNull(adminService.getOneAdmin(1L));
        log.debug("↓ Проверка корректности данных ↓");
        assertEquals("Neo", adminService.getOneAdmin(1L).getFirstName());
        log.debug("↓ Проверка ввода некорректных данных ↓");
        assertThrows(UserNotFoundIDException.class, ()-> adminService.getOneAdmin(44L));
    }

    @Test
    void deleteAdmin() {
        when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(admin));
        log.debug("↓ Проверка удаления данных ↓");
        adminService.deleteAdmin(1L);
        verify(accountRepository, atLeast(1)).deleteById(1L);
        log.debug("*** Произошло удаление 1 раз ***");
        log.debug("↓ Проверка выброса исключения при некорректных данных ↓");
        assertThrows(UserNotFoundIDException.class, ()-> adminService.deleteAdmin(44L));
    }
}