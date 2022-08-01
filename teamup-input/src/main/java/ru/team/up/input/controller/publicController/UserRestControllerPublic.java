package ru.team.up.input.controller.publicController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;
import ru.team.up.core.mappers.EventMapper;
import ru.team.up.core.mappers.UserMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.dto.ControlDto;
import ru.team.up.dto.EventDto;
import ru.team.up.dto.ParametersDto;
import ru.team.up.dto.UserDto;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.response.EventDtoListResponse;
import ru.team.up.input.response.UserDtoListResponse;
import ru.team.up.input.response.UserDtoResponse;
import ru.team.up.input.service.UserServiceRest;
import ru.team.up.sup.service.ParameterService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private UserServiceRest userServiceRest;
    private MonitorProducerService monitoringProducerService;

    /**
     * Метод для поиска пользователя по id
     *
     * @param userId id пользователя
     * @return Ответ поиска и статус
     */
    @Operation(summary = "Получение пользователя по id")
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse getUserById(@PathVariable("id") Long userId) {
        log.debug("Запрос на поиск пользователя с id = {}", userId);
        if (!ParameterService.getUserByIdEnabled.getValue()) {
            log.debug("Метод getUserById выключен параметром getUserByIdEnabled = false");
            throw new RuntimeException("Method getUserById disabled by parameter getUserByIdEnabled");
        }
        UserDto user = UserMapper.INSTANCE
                .mapUserToDto(userServiceRest.getUserById(userId));
        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto userIdParam = ParametersDto.builder()
                .description("ID ")
                .value(user.getId())
                .build();

        ParametersDto userEmailParam = ParametersDto.builder()
                .description("Email ")
                .value(user.getEmail())
                .build();

        ParametersDto userNameParam = ParametersDto.builder()
                .description("Имя ")
                .value(user.getUsername())
                .build();
        monitoringParameters.put("ID ", userIdParam);
        monitoringParameters.put("Email ", userEmailParam);
        monitoringParameters.put("Имя ", userNameParam);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return UserDtoResponse.builder()
                .userDto(user)
                .build();
    }

    /**
     * Метод поиска пользователя по почте
     *
     * @param userEmail почта пользователя
     * @return Ответ поиска и статус проверки
     */
    @Operation(summary = "Поиск пользователя по email")
    @GetMapping(value = "/email/{email:.+}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse getUserByEmail(@PathVariable(value = "email") String userEmail) {
        log.debug("Запрос на поиск пользователя с почтой: {}", userEmail);
        if (!ParameterService.getUserByEmailEnabled.getValue()) {
            log.debug("Метод getUserByEmail выключен параметром getUserByEmailEnabled = false");
            throw new RuntimeException("Method getUserByEmail is disabled by parameter getUserByEmailEnabled");
        }
        UserDto user = UserMapper.INSTANCE
                .mapUserToDto(userServiceRest.getUserByEmail(userEmail));

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto userIdParam = ParametersDto.builder()
                .description("ID ")
                .value(user.getId())
                .build();

        ParametersDto userEmailParam = ParametersDto.builder()
                .description("Email ")
                .value(user.getEmail())
                .build();

        ParametersDto userNameParam = ParametersDto.builder()
                .description("Имя ")
                .value(user.getUsername())
                .build();
        monitoringParameters.put("ID ", userIdParam);
        monitoringParameters.put("Email ", userEmailParam);
        monitoringParameters.put("Имя ", userNameParam);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return UserDtoResponse.builder()
                .userDto(user)
                .build();
    }

    /**
     * Метод поиска пользователя по имени
     *
     * @param userUsername имя пользователя
     * @return Ответ поиска и статус проверки
     */
    @Operation(summary = "Поиск пользователя по имени")
    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoResponse getUserByUsername(@PathVariable(value = "username") String userUsername) {
        log.debug("Запрос на поиск пользователя с именем: {}", userUsername);
        if (!ParameterService.getUserByUsernameEnabled.getValue()) {
            log.debug("Метод getUserByUsername выключен параметром getUserByUsernameEnabled = false");
            throw new RuntimeException("Method getUserByUsername is disabled by parameter getUserByUsernameEnabled");
        }
        UserDto user = UserMapper.INSTANCE
                .mapUserToDto(userServiceRest.getUserByUsername(userUsername));

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto userIdParam = ParametersDto.builder()
                .description("ID ")
                .value(user.getId())
                .build();

        ParametersDto userEmailParam = ParametersDto.builder()
                .description("Email ")
                .value(user.getEmail())
                .build();

        ParametersDto userNameParam = ParametersDto.builder()
                .description("Имя ")
                .value(user.getUsername())
                .build();
        monitoringParameters.put("ID ", userIdParam);
        monitoringParameters.put("Email ", userEmailParam);
        monitoringParameters.put("Имя ", userNameParam);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return UserDtoResponse.builder()
                .userDto(user)
                .build();
    }

    /**
     * Метод поиска всех пользователей
     *
     * @return Ответ поиска и статус проверки
     */
    @Operation(summary = "Получение списка всех пользователей")
    @GetMapping("/")
    public List<User> getUsersList() {
        log.debug("Получен запрос на список всех пользоватей");
        if (!ParameterService.getUsersListEnabled.getValue()) {
            log.debug("Метод getUsersList выключен параметром getUsersListEnabled = false");
            throw new RuntimeException("Method getUsersList is disabled by parameter getUsersListEnabled");
        }
        List<User> users = userServiceRest.getAllUsers();

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto usersSize = ParametersDto.builder()
                .description("Количество всех пользователей ")
                .value(users.size())
                .build();

        if (users.isEmpty()) {
            log.error("Список пользователей пуст");
            throw new RuntimeException("Список пользователей пуст");
        }
        monitoringParameters.put("Количество всех пользователей ", usersSize);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        log.debug("Список пользователей получен");
        return users;
    }

    /**
     * Метод поиска мероприятий пользователя
     *
     * @param id id пользователя
     * @return Ответ поиска и статус проверки
     */
    @Operation(summary = "Поиск мероприятий по id пользователя")
    @GetMapping(value = "/event/{id}/owner", produces = MediaType.APPLICATION_JSON_VALUE)
    public EventDtoListResponse getEventsByOwnerId(@PathVariable Long id) {
        log.debug("Запрос на поиск мероприятий пользователя с id: {}", id);
        if (!ParameterService.getEventsByOwnerIdEnabled.getValue()) {
            log.debug("Метод getEventsByOwnerId выключен параметром getEventsByOwnerIdEnabled = false");
            throw new RuntimeException("Method getEventsByOwnerId is disabled by parameter getEventsByOwnerIdEnabled");
        }
        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        List<EventDto> eventList = EventMapper.INSTANCE
                .mapDtoEventToEvent(userServiceRest.getEventsByOwnerId(id));

        ParametersDto eventListParam = ParametersDto.builder()
                .description("Количество всех мероприятий по id пользователя " + id)
                .value(eventList.size())
                .build();

        monitoringParameters.put("Количество всех мероприятий по id пользователя " + id, eventListParam);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return EventDtoListResponse.builder().eventDtoList(eventList)
                .build();
    }

    /**
     * Метод поиска мероприятий на которые подписан пользователь
     *
     * @param id id пользователя
     * @return Ответ поиска и статус проверки
     */
    @Operation(summary = "Поиск мероприятий на которые подписан пользователь")
    @GetMapping(value = "/event/{id}/subscriber", produces = MediaType.APPLICATION_JSON_VALUE)
    public EventDtoListResponse getEventsBySubscriberId(@PathVariable Long id) {
        log.debug("Запрос на поиск мероприятий на которые подписан пользователь с id: {}", id);
        if (!ParameterService.getEventsBySubscriberIdEnabled.getValue()) {
            log.debug("Метод getEventsBySubscriberId выключен параметром getEventsBySubscriberIdEnabled = false");
            throw new RuntimeException("Method getEventsBySubscriberId is disabled by parameter getEventsBySubscriberIdEnabled");
        }
        Map<String, ParametersDto> monitoringParameters = new HashMap<>();
        List<EventDto> eventList = EventMapper.INSTANCE
                .mapDtoEventToEvent(userServiceRest.getEventsBySubscriberId(id));

        ParametersDto eventListParam = ParametersDto.builder()
                .description("Количество мероприятий, на которые подписан пользователь id " + id)
                .value(eventList.size())
                .build();

        monitoringParameters.put("Количество мероприятий, на которые подписан пользователь id " + id, eventListParam);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return EventDtoListResponse.builder().eventDtoList(eventList)
                .build();
    }

    /**
     * Метод обновления пользователя
     *
     * @param user   Данные пользователя для изменения
     * @param userId идентификатор пользователя
     * @return Ответ обновления и статус проверки
     */
    //TODO методы изменения и сохранения юзера не работают
    @Operation(summary = "Изменение пользователя")
    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Account updateUser(@RequestBody UserRequest user, @PathVariable("id") Long userId) {
        log.debug("Получен запрос на обновление пользователя");
        if (!ParameterService.getUpdateUserEnabled.getValue()) {
            log.debug("Метод updateUser выключен параметром getUpdateUserEnabled = false");
            throw new RuntimeException("Method updateUser is disabled by parameter getUpdateUserEnabled");
        }
        Account existUser = userServiceRest.getUserById(userId);
        if (existUser == null) {
            log.error("Пользователь не найден");
            throw new RuntimeException("Пользователь не найден");
        }
        Account newUser = userServiceRest.updateUser(user, existUser.getId());
        log.debug("Пользователь обновлен");
        return newUser;
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
        if (!ParameterService.getDeleteUserByIdEnabled.getValue()) {
            log.debug("Метод deleteUserById выключен параметром getDeleteUserByIdEnabled = false");
            throw new RuntimeException("Method deleteUserById is disabled by parameter getDeleteUserByIdEnabled");
        }
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
    public UserDtoListResponse getTopUsersListInCity(@PathVariable(value = "city") String city) {
        log.debug("Получен запрос на список \"Топ популярных пользователей в городе\" в городе: {}", city);
        if (!ParameterService.getTopUsersListInCityEnabled.getValue()) {
            log.debug("Метод getTopUsersListInCity выключен параметром getTopUsersListInCityEnabled = false");
            throw new RuntimeException("Method getTopUsersListInCity is disabled by parameter getTopUsersListInCityEnabled");
        }
        return UserDtoListResponse.builder().userDtoList(
                        UserMapper.INSTANCE.mapUserListToUserDtoList(userServiceRest.getTopUsersInCity(city)))
                .build();
    }

    /**
     * Метод поиска пользователей в городе с указанием лимита
     *
     * @param city название города
     * @param limit ограничение на количество отображения
     * @return Список UserDto
     */
    @Operation(summary = "Получение списка пользователей в городе с указанием лимита")
    @GetMapping(value = "/{city}/{limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDtoListResponse getAllUsersByCityByLimit(@PathVariable String city, @PathVariable int limit) {
        return UserDtoListResponse.builder().userDtoList(
                        UserMapper.INSTANCE.mapUserListToUserDtoList(userServiceRest.getAllUsersByCityByLimit(city, limit)))
                .build();
    }
}
