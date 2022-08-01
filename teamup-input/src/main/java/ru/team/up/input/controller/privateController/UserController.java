package ru.team.up.input.controller.privateController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.entity.User;
import ru.team.up.core.mappers.UserMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.UserService;
import ru.team.up.dto.*;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.response.UserDtoListResponse;
import ru.team.up.input.response.UserDtoResponse;
import ru.team.up.input.service.UserServiceRest;
import ru.team.up.sup.service.ParameterService;

import javax.persistence.PersistenceException;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private UserService userService;
    private UserServiceRest userServiceRest;
    private MonitorProducerService monitoringProducerService;

    /**
     * @return Результат работы метода userService.getAllUsers() в виде UserDtoListResponse
     * метод сервиса сам генерирует ислючения HttpStatus.NO_CONTENT, если коллекция пуста
     */
    @GetMapping  //
    @Operation(summary = "Получение списка всех юзеров")
    public UserDtoListResponse getAllUsers() {
        if (!ParameterService.getAllUsersEnabled.getValue()) {
            log.debug("Метод getAllUsers выключен параметром getAllUsersEnabled = false");
            throw new RuntimeException("Method getAllUsers is disabled by parameter getAllUsersEnabled");
        }
        log.debug("Старт метода List<User> getAllUsers()");
        List<UserDto> users = UserMapper.INSTANCE.mapUserListToUserDtoList(userServiceRest.getAllUsers());
        Map<String, ParametersDto> monitoringParameters = new HashMap<>();
        ParametersDto userSize = ParametersDto.builder()
                .description("Количество всех пользователей ")
                .value(users.size())
                .build();

        monitoringParameters.put("Количество всех пользователей ", userSize);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return UserDtoListResponse.builder().userDtoList(users).build();
    }

    /**
     * @param id Значение ID юзера
     *           userServiceRest обрабатывает ошибки ответа
     * @return Возвращаес конкретный класс UserDtoResponse
     */
    @Operation(summary = "Получение нашего юзера по id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse getUserById(@PathVariable("id") Long id) {
        log.debug("Запрос на получение usera по id ");
        if (!ParameterService.getUserByIdPrivateEnabled.getValue()) {
            log.debug("Метод getUserById выключен параметром getUserByIdPrivateEnabled = false");
            throw new RuntimeException("Method getUserById is disabled by parameter getUserByIdPrivateEnabled");
        }
        UserDto user = UserMapper.INSTANCE
                .mapUserToDto(userServiceRest.getUserById(id));
        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto userId = ParametersDto.builder()
                .description("ID")
                .value(user.getId())
                .build();

        ParametersDto userEmail = ParametersDto.builder()
                .description("Email")
                .value(user.getEmail())
                .build();

        ParametersDto userName = ParametersDto.builder()
                .description("Имя")
                .value(user.getUsername())
                .build();

        monitoringParameters.put("ID ", userId);
        monitoringParameters.put("Email ", userEmail);
        monitoringParameters.put("Имя ", userName);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return UserDtoResponse.builder().userDto(user).build();
    }

    /**
     * @param userCreate Создаваемый объект класса User
     * @return Результат работы метода userService.saveUser(user) в виде UserDtoResponse
     */
    @PostMapping
    @Operation(summary = "Создание юзера")
    public UserDtoResponse createUser(@RequestBody @NotNull User userCreate) {
        if (!ParameterService.createUserEnabled.getValue()) {
            log.debug("Метод createUser выключен параметром createUserEnabled = false");
            throw new RuntimeException("Method createUser is disabled by parameter createUserEnabled");
        }
        log.debug("Старт метода createUser(@RequestBody @NotNull User user) с параметром");

        UserDto account = UserMapper.INSTANCE.mapUserToDto(userServiceRest.saveUser(userCreate));
        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto userId = ParametersDto.builder()
                .description("ID")
                .value(userCreate.getId())
                .build();

        ParametersDto userEmail = ParametersDto.builder()
                .description("Email")
                .value(userCreate.getEmail())
                .build();

        ParametersDto userName = ParametersDto.builder()
                .description("Имя")
                .value(userCreate.getUsername())
                .build();

        monitoringParameters.put("ID ", userId);
        monitoringParameters.put("Email ", userEmail);
        monitoringParameters.put("Имя ", userName);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return UserDtoResponse.builder().userDto(account).build();
    }

    /**
     * @param id   id обновляемого пользователя
     * @param user Обновляемый объект класса User
     * @return Результат работы метода userService.saveUser(user) в виде объекта User
     * в теле ResponseEntity
     */
    //TODO методы изменения и сохранения юзера не работают
    @PutMapping("/{id}")
    @Operation(summary = "Обновление юзера")
    public UserDtoResponse updateUser(@PathVariable Long id, @RequestBody @NotNull User user) {
        log.debug("Старт метода updateUser(@RequestBody @NotNull User user) с параметром {}", user);
        if (!ParameterService.updateUserEnabled.getValue()) {
            log.debug("Метод updateUser выключен параметром updateUserEnabled = false");
            throw new RuntimeException("Method updateUser is disabled by parameter updateUserEnabled");
        }

        Long userId = user.getId();
        log.debug("нашли id нашего юзера");
        if (!haveRightsToUpdate(SecurityContextHolder.getContext().getAuthentication(), userId) || !id.equals(userId)) {
            log.debug("Попытка изменить пользователя с id = {}, не имея на это прав", userId);
            throw new RuntimeException(String.valueOf(HttpStatus.BAD_REQUEST));
        }

        UserDto account = UserMapper.INSTANCE.mapUserToDto(userServiceRest.updateUserError(user, id));

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto userIdUpdate = ParametersDto.builder()
                .description("ID")
                .value(user.getId())
                .build();

        ParametersDto userEmailUpdate = ParametersDto.builder()
                .description("Email")
                .value(user.getEmail())
                .build();

        ParametersDto userNameUpdate = ParametersDto.builder()
                .description("Имя")
                .value(user.getUsername())
                .build();

        monitoringParameters.put("ID ", userIdUpdate);
        monitoringParameters.put("Email ", userEmailUpdate);
        monitoringParameters.put("Имя ", userNameUpdate);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return UserDtoResponse.builder().userDto(account).build();
    }

    /**
     * @param id Удаляемого объекта класса User
     * @return Объект ResponseEntity со статусом OK
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление юзера")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<User> deleteUser(@PathVariable Long id) с параметром {}", id);
        if (!ParameterService.deleteUserEnabled.getValue()) {
            log.debug("Метод deleteUser выключен параметром deleteUserEnabled = false");
            throw new RuntimeException("Method deleteUser is disabled by parameter deleteUserEnabled");
        }

        User user = userService.getOneUser(id).orElse(null);
        if (user == null) {
            log.error("Пользователя с id = {} не существует", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ResponseEntity<User> responseEntity;
        try {
            userService.deleteUser(id);
            responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.debug("Сформирован ответ {}", responseEntity);

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto userId = ParametersDto.builder()
                .description("ID")
                .value(user.getId())
                .build();

        ParametersDto userEmail = ParametersDto.builder()
                .description("Email")
                .value(user.getEmail())
                .build();

        ParametersDto userName = ParametersDto.builder()
                .description("Имя")
                .value(user.getUsername())
                .build();

        monitoringParameters.put("ID ", userId);
        monitoringParameters.put("Email ", userEmail);
        monitoringParameters.put("Имя ", userName);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return responseEntity;
    }

    /**
     * Метод проверки права пользователя на редактирование информации
     *
     * @param authentication параметр из SecurityContext текущей сессии
     * @param id             id редактируемого пользователя из запроса
     * @return true, если изменения производит админ, модератор, или юзер сам над собой
     */
    private boolean haveRightsToUpdate(Authentication authentication, Long id) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof Admin || principal instanceof Moderator) {
            return true;
        } else if (principal instanceof User) {
            User currentUser = (User) principal;
            return currentUser.getId().equals(id);
        }
        return false;
    }
}
