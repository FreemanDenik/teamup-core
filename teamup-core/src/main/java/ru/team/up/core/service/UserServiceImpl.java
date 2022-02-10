package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Role;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.exception.UserNotFoundException;
import ru.team.up.core.repositories.AdminRepository;
import ru.team.up.core.repositories.ModeratorRepository;
import ru.team.up.core.repositories.AccountRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Alexey Tkachenko
 * <p>
 * Класс сервиса для управления пользователями ru.team.up.core.entity.User
 */

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private AdminRepository adminRepository;
    private ModeratorRepository moderatorRepository;

    /**
     * @return Возвращает коллекцию User.
     * Если коллекция пуста, генерирует исключение со статусом HttpStatus.NO_CONTENT
     */
    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllUsers() {
        log.debug("Старт метода List<User> getAllUsers()");

        List<Account> users = Optional.of(accountRepository.findAllByRole(Role.ROLE_USER))
                .orElseThrow(NoContentException::new);
        log.debug("Получили список всех юзеров из БД {}", users);

        return users;
    }

    /**
     * @param id Уникальный ключ ID пользователя
     * @return Находит в БД пользователя по ID и возвращает его.
     * Если пользователь с переданным ID не найден в базе, генерирует исключение со статусом HttpStatus.NOT_FOUND
     */
    @Override
    @Transactional(readOnly = true)
    public Account getOneUser(Long id) {
        log.debug("Старт метода User getOneUser(Long id) с параметром {}", id);

        Account user = Optional.of(accountRepository.findById(id)).get()
                .orElseThrow(() -> new UserNotFoundException(id));
        log.debug("Получили юзера из БД {}", user);

        return user;
    }

    /**
     * @param user Объект класса ru.team.up.core.entity.User
     * @return Возвращает сохраненный в БД объект user
     */
    @Override
    @Transactional
    public Account saveUser(Account user) {
        log.debug("Старт метода User saveUser(User user) с параметром {}", user);

        Account save = accountRepository.save(user);
        log.debug("Сохранили юзера в БД {}", save);

        return save;
    }

    /**
     * @param id Объекта класса ru.team.up.core.entity.User
     *           Метод удаляет пользователя из БД
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.debug("Старт метода void deleteUser(User user) с параметром {}", id);

        accountRepository.deleteById(id);
        log.debug("Удалили юзера из БД с ID {}", id);
    }
}
