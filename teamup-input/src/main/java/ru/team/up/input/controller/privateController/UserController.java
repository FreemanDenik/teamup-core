package ru.team.up.input.controller.privateController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.User;
import ru.team.up.core.service.UserService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Alexey Tkachenko
 *
 * @link localhost:8080/swagger-ui.html
 * Документация API
 */

@Slf4j
@RestController
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/private/account/user")
public class UserController {
    private UserService userService;

    /**
     * @return Результат работы метода userService.getAllUsers() в виде коллекции юзеров
     * в теле ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.debug("Старт метода ResponseEntity<List<User>> getAllUsers()");

        ResponseEntity<List<User>> responseEntity = ResponseEntity.ok(userService.getAllUsers());
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param id Значение ID юзера
     * @return Результат работы метода userService.getOneUser(id) в виде объекта User
     * в теле ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<User> getOneUser(@PathVariable Long id) с параметром {}", id);

        ResponseEntity<User> responseEntity = ResponseEntity.ok(userService.getOneUser(id));
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param user Создаваемый объект класса User
     * @return Результат работы метода userService.saveUser(user) в виде объекта User
     * в теле ResponseEntity
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestParam String user, @RequestBody @NotNull User userCreate) {
        log.debug("Старт метода ResponseEntity<User> createUser(@RequestBody @NotNull User user) с параметром {}", userCreate);

        ResponseEntity<User> responseEntity = new ResponseEntity<>(userService.saveUser(userCreate), HttpStatus.CREATED);
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param user Обновляемый объект класса User
     * @return Результат работы метода userService.saveUser(user) в виде объекта User
     * в теле ResponseEntity
     */
    @PatchMapping
    public ResponseEntity<User> updateUser(@RequestBody @NotNull User user) {
        log.debug("Старт метода ResponseEntity<User> updateUser(@RequestBody @NotNull User user) с параметром {}", user);

        ResponseEntity<User> responseEntity = ResponseEntity.ok(userService.saveUser(user));
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param id Удаляемого объекта класса User
     * @return Объект ResponseEntity со статусом OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<User> deleteUser(@PathVariable Long id) с параметром {}", id);

        userService.deleteUser(id);

        ResponseEntity<User> responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }
}
