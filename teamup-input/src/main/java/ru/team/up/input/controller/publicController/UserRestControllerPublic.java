package ru.team.up.input.controller.publicController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;
import ru.team.up.core.mappers.EventMapper;
import ru.team.up.core.mappers.UserMapper;
import ru.team.up.dto.SupParameterDto;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.response.EventDtoListResponse;
import ru.team.up.input.response.UserDtoListResponse;
import ru.team.up.input.response.UserDtoResponse;
import ru.team.up.input.service.UserServiceRest;
import ru.team.up.sup.service.ParameterService;

import java.util.List;

/**
 * REST-контроллер для пользователей
 *
 * @author Pavel Kondrashov
 */

@Slf4j
@Tag(name = "User Public Rest Controller", description = "User API")
@RestController
@RequestMapping("public/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserRestControllerPublic {
    private final UserServiceRest userServiceRest;
    private final ParameterService parameterService;

    /**
     * Метод для поиска пользователя по id
     *
     * @param userId id пользователя
     * @return Ответ поиска и статус
     */
    @Operation(summary = "Получение пользователя по id")
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable("id") Long userId) {
        log.debug("Запрос на поиск пользователя с id = {}", userId);
        SupParameterDto<?> param = parameterService.getParamByName("TEAMUP_CORE_GET_USER_BY_ID_ENABLED");
        if (param != null) {
            if (!(boolean) param.getParameterValue()) {
                return new ResponseEntity<>(HttpStatus.LOCKED);
            }
        }
        return new ResponseEntity<>(
                UserDtoResponse.builder().userDto(UserMapper.INSTANCE.mapUserToDto(userServiceRest.getUserById(userId))).build(),
                HttpStatus.OK);
    }

    /**
     * Метод поиска пользователя по почте
     *
     * @param userEmail почта пользователя
     * @return Ответ поиска и статус проверки
     */
    @Operation(summary = "Поиск пользователя по email")
    @GetMapping(value = "/email/{email:.+}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDtoResponse> getUserByEmail(@PathVariable(value = "email") String userEmail) {
        log.debug("Запрос на поиск пользователя с почтой: {}", userEmail);

        return new ResponseEntity<>(
                UserDtoResponse.builder().userDto(UserMapper.INSTANCE.mapUserToDto(userServiceRest.getUserByEmail(userEmail)))
                        .build(), HttpStatus.OK);
    }

    /**
     * Метод поиска пользователя по имени
     *
     * @param userUsername имя пользователя
     * @return Ответ поиска и статус проверки
     */
    @Operation(summary = "Поиск пользователя по имени")
    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDtoResponse> getUserByUsername(@PathVariable(value = "username") String userUsername) {
        log.debug("Запрос на поиск пользователя с именем: {}", userUsername);

        return new ResponseEntity<>(
                UserDtoResponse.builder().userDto(UserMapper.INSTANCE.mapUserToDto(userServiceRest.getUserByUsername(userUsername)))
                        .build(), HttpStatus.OK);
    }

    /**
     * Метод поиска всех пользователей
     *
     * @return Ответ поиска и статус проверки
     */
    @Operation(summary = "Получение списка всех пользователей")
    @GetMapping("/")
    public ResponseEntity<List<User>> getUsersList() {
        log.debug("Получен запрос на список всех пользоватей");
        List<User> users = userServiceRest.getAllUsers();

        if (users.isEmpty()) {
            log.error("Список пользователей пуст");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.debug("Список пользователей получен");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Метод поиска мероприятий пользователя
     *
     * @param id id пользователя
     * @return Ответ поиска и статус проверки
     */
    @Operation(summary = "Поиск мероприятий по id пользователя")
    @GetMapping(value = "/event/{id}/owner", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDtoListResponse> getEventsByOwnerId(@PathVariable Long id) {
        log.debug("Запрос на поиск мероприятий пользователя с id: {}", id);

        return new ResponseEntity<>(
                EventDtoListResponse.builder()
                        .eventDtoList(EventMapper.INSTANCE.mapDtoEventToEvent(userServiceRest.getEventsByOwnerId(id)))
                        .build(), HttpStatus.OK);
    }

    /**
     * Метод поиска мероприятий на которые подписан пользователь
     *
     * @param id id пользователя
     * @return Ответ поиска и статус проверки
     */
    @Operation(summary = "Поиск мероприятий на которые подписан пользователь")
    @GetMapping(value = "/event/{id}/subscriber", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDtoListResponse> getEventsBySubscriberId(@PathVariable Long id) {
        log.debug("Запрос на поиск мероприятий на которые подписан пользователь с id: {}", id);

        return new ResponseEntity<>(
                EventDtoListResponse.builder()
                        .eventDtoList(EventMapper.INSTANCE.mapDtoEventToEvent(userServiceRest.getEventsBySubscriberId(id)))
                        .build(), HttpStatus.OK);
    }

    /**
     * Метод обновления пользователя
     *
     * @param user   Данные пользователя для изменения
     * @param userId идентификатор пользователя
     * @return Ответ обновления и статус проверки
     */
    @Operation(summary = "Изменение пользователя")
    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateUser(@RequestBody UserRequest user, @PathVariable("id") Long userId) {
        log.debug("Получен запрос на обновление пользователя");
        Account existUser = userServiceRest.getUserById(userId);

        if (existUser == null) {
            log.error("Пользователь не найден");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Account newUser = userServiceRest.updateUser(user, existUser.getId());
        log.debug("Пользователь обновлен");
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    /**
     * Метод для удаления пользователя
     *
     * @param userId идентификатор пользователя
     * @return Ответ удаления и статус проверки
     */
    @Operation(summary = "Удаление пользователя")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> deleteUserById(@PathVariable("id") Long userId) {
        log.debug("Получен запрос на удаления пользователя с id = {}", userId);
        Account user = userServiceRest.getUserById(userId);

        if (user == null) {
            log.error("Пользователь с id = {} не найден", userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        userServiceRest.deleteUserById(userId);

        log.debug("Пользователь с id = {} успешно удален", userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Метод поиска топ популярных пользователей в городе
     *
     * @param city название города
     * @return Список UserDto
     */
    @Operation(summary = "Получение списка \"Топ популярных пользователей в городе\"")
    @GetMapping(value = "/top/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDtoListResponse> getTopUsersListInCity(@PathVariable(value = "city") String city) {
        log.debug("Получен запрос на список \"Топ популярных пользователей в городе\" в городе: {}", city);

        return new ResponseEntity<>(
                UserDtoListResponse.builder().userDtoList(UserMapper.INSTANCE.mapUserListToUserDtoList(userServiceRest.getTopUsersInCity(city))).build(),
                HttpStatus.OK);
    }
}
