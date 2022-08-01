package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.entity.Role;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.repositories.AccountRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexey Tkachenko
 * <p>
 * Класс сервиса для управления пользователями ru.team.up.core.entity.Admin
 */
@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminServiceImpl implements AdminService {
    private AccountRepository accountRepository;
    private PasswordEncoder encoder;

    /**
     * @return Возвращает коллекцию Admin.
     * Если коллекция пуста, генерирует исключение со статусом HttpStatus.NO_CONTENT
     */
    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAdmins() throws NoContentException {
        log.debug("Старт метода List<Admin> getAllAdmins()");

        List<Account> admins = accountRepository.findAllByRole(Role.ROLE_ADMIN);

        if (admins.isEmpty()) {
            throw new NoContentException();
        }
        log.debug("Получили список всех админов из БД {}", admins);

        return admins;
    }

    /**
     * @param id Уникальный ключ ID админа
     * @return Находит в БД админа по ID и возвращает его.
     * Если админ с переданным ID не найден в базе, генерирует исключение со статусом HttpStatus.NOT_FOUND
     */
    @Override
    @Transactional(readOnly = true)
    public Account getOneAdmin(Long id) throws UserNotFoundIDException {
        log.debug("Старт метода Admin getOneAdmin(Long id) с параметром {}", id);
        Account admin = Optional.of(accountRepository.findById(id).orElseThrow(() -> new UserNotFoundIDException(id))).get();
        log.debug("Получили админа из БД {}", admin);

        return admin;
    }

    /**
     * @param admin Объект класса ru.team.up.core.entity.Admin
     * @return Возвращает сохраненный в БД объект admin
     */
    @Override
    @Transactional
    public Account saveAdmin(Account admin) {
        log.debug("Старт метода Admin saveAdmin(Admin admin) с параметром {}", admin);
        admin.setAccountCreatedTime(LocalDate.now());
        admin.setLastAccountActivity(LocalDateTime.now());
        admin.setPassword(encoder.encode(admin.getPassword()));
        admin.setRole(Role.ROLE_ADMIN);
        Account save = accountRepository.save(admin);
        log.debug("Сохранили админа в БД {}", save);
        return save;
    }

    @Override
    @Transactional
    public Admin updateAdmin(Admin admin) {
        log.debug("Старт метода Admin updateAdmin(Admin admin) с параметром {}", admin);
        Admin oldAdmin = (Admin) getOneAdmin(admin.getId());
        admin.setAccountCreatedTime(oldAdmin.getAccountCreatedTime());
        admin.setLastAccountActivity(LocalDateTime.now());
        admin.setRole(Role.ROLE_ADMIN);
        if (admin.getPassword() == null) {
            admin.setPassword(oldAdmin.getPassword());
        } else {
            admin.setPassword(encoder.encode(admin.getPassword()));
        }
        accountRepository.save(admin);
        return admin;
    }

    /**
     * @param id Объект класса ru.team.up.core.entity.Admin
     *           Метод удаляет админа из БД
     */
    @Override
    @Transactional
    public void deleteAdmin(Long id) throws UserNotFoundIDException {
        log.debug("Старт метода void deleteAdmin(Admin admin) с параметром {}", id);

        log.debug("Проверка существования админа в БД с id {}", id);
        Optional.of(accountRepository.findById(id).orElseThrow(() -> new UserNotFoundIDException(id)));

        accountRepository.deleteById(id);
        log.debug("Удалили админа из БД {}", id);
    }

    @Override
    @Transactional
    public boolean existsById(Long id) {
        log.debug("Старт метода boolean existsById(Long id) с параметром {}", id);
        return accountRepository.existsById(id);
    }
}