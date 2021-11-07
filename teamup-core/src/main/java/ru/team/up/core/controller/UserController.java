package ru.team.up.core.controller;

import lombok.AllArgsConstructor;
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
 */

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/private/account/users")
public class UserController {
    private UserService userService;

    /**
     * @return Результат работы метода userService.getAllUsers() в виде коллекции юзеров
     * в теле ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * @param id Значение ID юзера
     * @return Результат работы метода userService.getOneUser(id) в виде объекта User
     * в теле ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getOneUser(id));
    }

    /**
     * @param user Создаваемый объект класса User
     * @return Результат работы метода userService.saveUser(user) в виде объекта User
     * в теле ResponseEntity
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @NotNull User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    /**
     * @param user Обновляемый объект класса User
     * @return Результат работы метода userService.saveUser(user) в виде объекта User
     * в теле ResponseEntity
     */
    @PatchMapping
    public ResponseEntity<User> updateUser(@RequestBody @NotNull User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    /**
     * @param user Удаляемый объект класса User
     * @return Объект ResponseEntity со статусом OK
     */
    @DeleteMapping
    public ResponseEntity<User> deleteUser(@RequestBody @NotNull User user) {
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
