package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.exception.UserNotFoundException;
import ru.team.up.core.repositories.AdminRepository;

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
    private AdminRepository adminRepository;

    /**
     * @return Возвращает коллекцию Admin.
     * Если коллекция пуста, генерирует исключение со статусом HttpStatus.NO_CONTENT
     */
    @Override
    @Transactional(readOnly = true)
    public List<Admin> getAllAdmins() throws NoContentException {
        log.debug("Старт метода List<Admin> getAllAdmins()");

        List<Admin> admins = adminRepository.findAll();

        if (admins.isEmpty()){
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
    public Admin getOneAdmin(Long id) throws UserNotFoundException {
        log.debug("Старт метода Admin getOneAdmin(Long id) с параметром {}", id);

        Admin admin = Optional.of(adminRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id))).get();
        log.debug("Получили админа из БД {}", admin);

        return admin;
    }

    /**
     * @param admin Объект класса ru.team.up.core.entity.Admin
     * @return Возвращает сохраненный в БД объект admin
     */
    @Override
    @Transactional
    public Admin saveAdmin(Admin admin) {
        log.debug("Старт метода Admin saveAdmin(Admin admin) с параметром {}", admin);

        Admin save = adminRepository.save(admin);
        log.debug("Сохранили админа в БД {}", save);

        return save;
    }

    /**
     * @param id Объект класса ru.team.up.core.entity.Admin
     *           Метод удаляет админа из БД
     */
    @Override
    @Transactional
    public void deleteAdmin(Long id) throws UserNotFoundException {
        log.debug("Старт метода void deleteAdmin(Admin admin) с параметром {}", id);

        log.debug("Проверка существования админа в БД с id {}", id);
        Optional.of(adminRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));

        adminRepository.deleteById(id);
        log.debug("Удалили админа из БД {}", id);
    }
}