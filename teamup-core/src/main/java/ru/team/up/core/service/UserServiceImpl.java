package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Role;
import ru.team.up.core.entity.User;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.repositories.AccountRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /**
     * @return Возвращает коллекцию User.
     * Если коллекция пуста, генерирует исключение со статусом HttpStatus.NO_CONTENT
     */
    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllUsers() {
        log.debug("Старт метода List<Account> getAllUsers()");

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
    public Optional<Account> getOneUser(Long id) {
        log.debug("Старт метода User getOneUser(Long id) с параметром {}", id);

        return accountRepository.findById(id);
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

    /**
     * @param email электронная почта
     * @return Возвращает сохраненный в БД объект user
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findByEmail(String email) {
        log.debug("Старт метода findByEmail(String email) с параметром {}", email);

        return Optional.ofNullable(accountRepository.findByEmail(email));
    }

    /**
     * @param username логин
     * @return Возвращает сохраненный в БД объект user
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findByUsername(String username) {
        log.debug("Старт метода findByUsername(String username) с параметром {}", username);

        return Optional.ofNullable(accountRepository.findByUsername(username));
    }

    /**
     * @param city наименование города
     * @return Список "Топ популярных пользователей в городе"
     */
    @Override
    @Transactional(readOnly = true)
    public List<Account> getTopUsersInCity(String city) {
        return accountRepository.findUsersByCity(city).stream().
                map(u -> (User) u).
                filter(u -> u.getSubscribers().size() > 0).
                sorted((u1, u2) -> Integer.compare(u2.getSubscribers().size(), u1.getSubscribers().size())).
                limit(10).
                collect(Collectors.toList());
    }
}
