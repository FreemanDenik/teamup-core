package ru.team.up.input.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.team.up.core.entity.User;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.service.UserService;

import java.util.List;

/**
 * REST-контроллер для пользователей
 * @author Pavel Kondrashov
 */

@Slf4j
@RestController("api/public/account/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserRestController {
    private final UserService userService;

    /**
     * Метод для поиска поьзователя по id
     * @param userId id пользователя
     * @return Ответ поиска и статус
     */
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
        log.debug("Запрос на поиск пользователя с id = {}", userId);

        if (userId == null) {
            log.error("Введен неверный id");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.debug("Пользователь с id = {} найден", userId);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    /**
     * Метод поиска пользователя по почте
     * @param userEmail почта пользователя
     * @return Ответ поиска и статус проверки
     */
    @GetMapping(value = "{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String userEmail) {
        log.debug("Запрос на поиск пользователя с почтой: {}", userEmail);
        User user = userService.getUserByEmail(userEmail);

        if (user == null) {
            log.error("Пользователь с почтой {} не найден", userEmail);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.debug("Пользователь с почтой {} найден", userEmail);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Метод поиска всех пользователей
     * @return Ответ поиска и статус проверки
     */
    @GetMapping("accounts")
    public ResponseEntity<List<User>> getUsersList() {
        log.debug("Получен запрос на список всех пользоватей");
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            log.error("Список пользователей пуст");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.debug("Список пользователей получен");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Метод обновления пользователя
     * @param user Данные пользователя для изменения
     * @param userId идентификатор пользователя
     * @return Ответ обновления и статус проверки
     */
    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody UserRequest user, @PathVariable("id") Long userId) {
        log.debug("Получен запрос на обновление пользователя");
        User existUser = userService.updateUser(userId, user);

        if (existUser == null) {
            log.error("Пользователь не найден");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.debug("Пользователь обновлен");
        return new ResponseEntity<>(existUser, HttpStatus.OK);
    }

    /**
     * Метод для удаления пользователя
     * @param userId идентификатор пользователя
     * @return Ответ удаления и статус проверки
     */
    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUserById(@PathVariable("id") Long userId) {
        log.debug("Получен запрос на удаления пользователя с id = {}", userId);
        User user = userService.getUserById(userId);

        if (user == null) {
            log.error("Пользователь с id = {} не найден", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(userId);

        log.debug("Пользователь с id = {} успешно удален", userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
