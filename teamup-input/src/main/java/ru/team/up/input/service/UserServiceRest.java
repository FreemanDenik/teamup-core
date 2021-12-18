package ru.team.up.input.service;

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
    User getUserById(Long id);

    /**
     * Метод поиска пользователя по почте
     *
     * @param email почта для поиска
     * @return Пользователь с указанной почтой
     */
    User getUserByEmail(String email);

    /**
     * Метод получения всех пользователей
     *
     * @return Список пользователей
     */
    List<User> getAllUsers();

    /**
     * Метод обновления пользователя
     *
     * @param user Пользователь для обновления
     */
    User updateUser(UserRequest user, Long id);

    /**
     * Метод для удаления пользователя
     *
     * @param id идентификатор поиска
     */
    void deleteUserById(Long id);
}
