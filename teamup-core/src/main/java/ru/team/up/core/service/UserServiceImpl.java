package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Role;
import ru.team.up.core.entity.User;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.repositories.UserRepository;
import ru.team.up.dto.NotifyDto;
import ru.team.up.dto.NotifyStatusDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private UserRepository userRepository;
    private NotifyService notifyService;
    private PasswordEncoder encoder;

    /**
     * @return Возвращает коллекцию User.
     * Если коллекция пуста, генерирует исключение со статусом HttpStatus.NO_CONTENT
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        log.debug("Старт метода List<Account> getAllUsers()");

        List<User> users = Optional.of(userRepository.findAllUsersByRole(Role.ROLE_USER))
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
    public Optional<User> getOneUser(Long id) {
        log.debug("Старт метода User getOneUser(Long id) с параметром {}", id);

        return Optional.ofNullable(userRepository.findUserById(id));
    }

    /**
     * @param user Объект класса ru.team.up.core.entity.User
     * @return Возвращает сохраненный в БД объект user
     */
    @Override
    @Transactional
    public User saveUser(User user) {
        log.debug("Старт метода User saveUser(User user) с параметром {}", user);
        user.setPassword(encoder.encode(user.getPassword()));
        // не стал добавлять в Account аннотации @CreationTimestamp и @UpdateTimestamp чтобы не поломать другой код
        user.setAccountCreatedTime(LocalDate.now());
        user.setLastAccountActivity(LocalDateTime.now());
        user.setRole(Role.ROLE_USER);
        User save = userRepository.save(user);

        log.debug("Сохранили юзера в БД {}", save);
        return save;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        log.debug("Старт метода User updateUser(User user)");

        log.debug("Поиск обновляемого пользователя в базе...");
        Long id = user.getId();
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null) {
            log.error("Пользователь с id = {} не найден", id);
            throw new UserNotFoundIDException(id);
        }
        log.debug("Отправляем уведомления пользователю о новых подписчиках, если они есть");
        Set<User> newSetOfSubscribers = user.getSubscribers();
        newSetOfSubscribers.removeAll(userRepository.findUserById(user.getId()).getSubscribers());
        String userEmail = user.getEmail();

        notifyService.notify(newSetOfSubscribers.stream().map(s -> {
            return NotifyDto.builder()
                    .subject("Новый подписчик")
                    .text("Пользователь " + s.getUsername() + " подписался на вас")
                    .email(userEmail)
                    .creationTime(LocalDateTime.now())
                    .status(NotifyStatusDto.NOT_SENT)
                    .build();
        }).collect(Collectors.toSet()));
        // предполагается, что основная информация (имя, фамилия...) приходят в запросе, кроме коллекций
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAccountCreatedTime(oldUser.getAccountCreatedTime());
        user.setLastAccountActivity(LocalDateTime.now());
        user.setUserInterests(oldUser.getUserInterests());
        user.setSubscribers(oldUser.getSubscribers());
        user.setUserEvent(oldUser.getUserEvent());
        user.setUserMessages(oldUser.getUserMessages());

        log.debug("Сохранение в БД {}", user);
        return userRepository.save(user);
    }

    /**
     * @param id Объекта класса ru.team.up.core.entity.User
     *           Метод удаляет пользователя из БД
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.debug("Старт метода void deleteUser(User user) с параметром {}", id);

        userRepository.deleteById(id);
        log.debug("Удалили юзера из БД с ID {}", id);
    }

    /**
     * @param email электронная почта
     * @return Возвращает сохраненный в БД объект user
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        log.debug("Старт метода findByEmail(String email) с параметром {}", email);

        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    /**
     * @param username логин
     * @return Возвращает сохраненный в БД объект user
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        log.debug("Старт метода findByUsername(String username) с параметром {}", username);

        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    /**
     * @param city наименование города
     * @return Список "Топ популярных пользователей в городе"
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getTopUsersInCity(String city) {
        return userRepository.findUsersByCity(city).stream().
                filter(u -> u.getSubscribers().size() > 0).
                sorted((u1, u2) -> Integer.compare(u2.getSubscribers().size(), u1.getSubscribers().size())).
                limit(10).
                collect(Collectors.toList());
    }

    /**
     * @param city наименование города
     * @param limit ограничение на количество отображения
     * @return Список "Поиск пользователей по городу с указанием лимита на страницу"
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsersByCityByLimit(String city, int limit) {
        return userRepository.findUsersByCity(city).stream().
                limit(limit).
                collect(Collectors.toList());
    }
}
