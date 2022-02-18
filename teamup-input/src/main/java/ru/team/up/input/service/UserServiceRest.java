package ru.team.up.input.service;

import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.Role;
import ru.team.up.core.entity.User;
import ru.team.up.input.payload.request.UserRequest;

import java.util.List;

/**
 * Интерфейс для поиска, обновления и удаления пользователей
 *
 * @author Pavel Kondrashov
 */

public interface UserServiceRest {

    /**
     * Метод поиска пользователя по id
     *
     * @param id идентификатор поиска
     * @return Пользователь с указанным иднтификатором
     */
    Account getUserById(Long id);

    /**
     * Метод поиска пользователя по почте
     *
     * @param email почта для поиска
     * @return Пользователь с указанной почтой
     */
    Account getUserByEmail(String email);

    /**
     * Метод поиска пользователя по имени
     *
     * @param username имя для поиска
     * @return Пользователь с указанным именем
     */
    Account getUserByUsername(String username);

    /**
     * Метод получения всех пользователей
     *
     * @return Список пользователей
     */
    List<Account> getAllUsers();

    /**
     * Метод получения всех мероприятий пользователя
     *
     * @return Список мероприятий
     */
    List<Event> getAllEventsByAuthorId(Long id);

    /**
     * Метод обновления пользователя
     *
     * @param user Пользователь для обновления
     */
    Account updateUser(UserRequest user, Long id);

    /**
     * Метод для удаления пользователя
     *
     * @param id идентификатор поиска
     */
    void deleteUserById(Long id);
}
