package ru.team.up.input.controller.privateController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;
import ru.team.up.core.mappers.UserMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.UserService;
import ru.team.up.dto.*;
import ru.team.up.input.response.UserDtoResponse;
import ru.team.up.input.service.UserServiceRest;

import javax.persistence.PersistenceException;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alexey Tkachenko
 * @link localhost:8080/swagger-ui.html
 * Документация API
 */

@Slf4j
@RestController
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "User Private Controller", description = "User API")
@RequestMapping(value = "/private/account/user")
public class UserController {
    //TODO По правильному нужно перевести все на UserServiceRest! Это нужно сделать при подкручивании приватных контроллеров
    private UserService userService;
    private UserServiceRest userServiceRest;
    private MonitorProducerService monitoringProducerService;

    /**
     * @return Результат работы метода userService.getAllUsers() в виде коллекции юзеров
     * в теле ResponseEntity
     */
    @GetMapping
    @Operation(summary = "Получение списка всех юзеров")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.debug("Старт метода ResponseEntity<List<User>> getAllUsers()");
        List<User> users = userService.getAllUsers();
        ResponseEntity<List<UserDto>> responseEntity;
        try {
            responseEntity = ResponseEntity.ok(UserMapper.INSTANCE
                    .mapUserListToUserDtoList(users));
        } catch (PersistenceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.debug("Сформирован ответ {}", responseEntity);
        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("Количество всех пользователей ",
                users.size());
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return responseEntity;
    }

    /**
     * @param id Значение ID юзера
     * @return Результат работы метода userService.getOneUser(id) в виде объекта User
     * в теле ResponseEntity
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получение юзера по id")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<User> getOneUser(@PathVariable Long id) с параметром {}", id);
        User user = userServiceRest.getUserById(id);
        ResponseEntity<UserDtoResponse> response = new ResponseEntity<>(
                UserDtoResponse.builder().
                        userDto(UserMapper.INSTANCE.mapUserToDto(user)).build(),
                HttpStatus.OK);
        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("ID ", user.getId());
        monitoringParameters.put("Email ", user.getEmail());
        monitoringParameters.put("Имя ", user.getUsername());
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return response;
    }

    /**
     * @param user Создаваемый объект класса User
     * @return Результат работы метода userService.saveUser(user) в виде объекта User
     * в теле ResponseEntity
     */
    @PostMapping
    @Operation(summary = "Создание юзера")
    public ResponseEntity<Account> createUser(@RequestParam String user, @RequestBody @NotNull User userCreate) {
        log.debug("Старт метода ResponseEntity<User> createUser(@RequestBody @NotNull User user) с параметром {}", userCreate);

        ResponseEntity<Account> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(userService.saveUser(userCreate), HttpStatus.CREATED);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.debug("Сформирован ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param user Обновляемый объект класса User
     * @return Результат работы метода userService.saveUser(user) в виде объекта User
     * в теле ResponseEntity
     */
    @PatchMapping
    @Operation(summary = "Обновление юзера")
    public ResponseEntity<Account> updateUser(@RequestBody @NotNull User user) {
        log.debug("Старт метода ResponseEntity<User> updateUser(@RequestBody @NotNull User user) с параметром {}", user);

        ResponseEntity<Account> responseEntity;
        try {
            responseEntity = ResponseEntity.ok(userService.saveUser(user));
        } catch (PersistenceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.debug("Сформирован ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param id Удаляемого объекта класса User
     * @return Объект ResponseEntity со статусом OK
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление юзера")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<User> deleteUser(@PathVariable Long id) с параметром {}", id);

        ResponseEntity<User> responseEntity;
        try {
            userService.deleteUser(id);
            responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.debug("Сформирован ответ {}", responseEntity);

        return responseEntity;
    }
}
