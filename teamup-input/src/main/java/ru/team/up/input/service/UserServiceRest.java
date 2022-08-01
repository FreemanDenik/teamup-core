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
    User getUserById(Long id);

    /**
     * Метод поиска пользователя по почте
     *
     * @param email почта для поиска
     * @return Пользователь с указанной почтой
     */
    User getUserByEmail(String email);

    /**
     * Метод поиска пользователя по имени
     *
     * @param username имя для поиска
     * @return Пользователь с указанным именем
     */
    User getUserByUsername(String username);

    /**
     * Метод получения всех пользователей
     *
     * @return Список пользователей
     */
    List<User> getAllUsers();

    /**
     * Метод сохранения пользователя
     *
     * @return Список пользователей
     */
    User saveUser(User user);

    /**
     * Метод получения всех мероприятий пользователя
     *
     * @return Список мероприятий
     */
    List<Event> getEventsByOwnerId(Long id);

    /**
     * Метод получения всех мероприятий на которые подписан пользователь
     *
     * @return Список мероприятий
     */
    List<Event> getEventsBySubscriberId(Long id);

    /**
     * Метод обновления пользователя
     *
     * @param user Пользователь для обновления
     */
    User updateUserError (User user,Long id);

    User updateUser(UserRequest userRequest, Long id);

    /**
     * Метод для удаления пользователя
     *
     * @param id идентификатор поиска
     */
    void deleteUserById(Long id);

    /**
     * Метод поиска пользователя по id
     *
     * @param city название города
     * @return Список "Топ популярных пользователей в городе"
     */
    List<User> getTopUsersInCity(String city);

    /**
     * Метод поиска пользователя по городам с лимитом
     *
     * @param city название города
     * @param limit ограничение на количество отображения
     * @return Список "Пользователей по городам с лимитом"
     */
    List<User> getAllUsersByCityByLimit(String city, int limit);
}
