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
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;
import ru.team.up.core.mappers.EventMapper;
import ru.team.up.core.mappers.UserMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.dto.ControlDto;
import ru.team.up.dto.EventDto;
import ru.team.up.dto.ReportDto;
import ru.team.up.dto.UserDto;
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

        String dataUser = user.getId() + " " + user.getEmail() + " " + user.getUsername();
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReportDto reportDto = monitoringProducerService.constructReportDto(o, ControlDto.MANUAL,
                this.getClass(), "Id, Email и Username Юзера полученного по id ", dataUser);
        monitoringProducerService.send(reportDto);

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
        UserDto user = UserMapper.INSTANCE
                .mapUserToDto(userServiceRest.getUserByEmail(userEmail));

        String dataUser = user.getId() + " " + user.getEmail() + " " + user.getUsername();

        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReportDto reportDto = monitoringProducerService.constructReportDto(o, ControlDto.MANUAL,
                this.getClass(),
                "Id, Email и Username Юзера полученного по email ", dataUser);
        monitoringProducerService.send(reportDto);

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

        UserDto user = UserMapper.INSTANCE
                .mapUserToDto(userServiceRest.getUserByUsername(userUsername));
        String dataUser = user.getId() + " " + user.getEmail() + " " + user.getUsername();


        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReportDto reportDto = monitoringProducerService.constructReportDto(o, ControlDto.MANUAL,
                this.getClass(),
                "Id, Email и Username Юзера полученного по имени ", dataUser);
        monitoringProducerService.send(reportDto);

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
        List<User> users = userServiceRest.getAllUsers();
        if (users.isEmpty()) {
            log.error("Список пользователей пуст");
            throw new RuntimeException("Список пользователей пуст");
        }

        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReportDto reportDto = monitoringProducerService.constructReportDto(o, ControlDto.MANUAL,
                this.getClass(),
                "Количество всех Юзеров", users.size());
        monitoringProducerService.send(reportDto);

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

        List<EventDto> eventList = EventMapper.INSTANCE
                .mapDtoEventToEvent(userServiceRest.getEventsByOwnerId(id));

        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReportDto reportDto = monitoringProducerService.constructReportDto(o, ControlDto.MANUAL,
                this.getClass(),
                "Количество всех мероприятий полученных по id пользователя", eventList.size());
        monitoringProducerService.send(reportDto);
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

        List<EventDto> eventList = EventMapper.INSTANCE
                .mapDtoEventToEvent(userServiceRest.getEventsBySubscriberId(id));

        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReportDto reportDto = monitoringProducerService.constructReportDto(o, ControlDto.MANUAL,
                this.getClass(),
                "Количество всех мероприятий на которые подписан пользователь", eventList.size());
        monitoringProducerService.send(reportDto);
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
    @Operation(summary = "Изменение пользователя")
    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Account updateUser(@RequestBody UserRequest user, @PathVariable("id") Long userId) {
        log.debug("Получен запрос на обновление пользователя");
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
        return UserDtoListResponse.builder().userDtoList(
                        UserMapper.INSTANCE.mapUserListToUserDtoList(userServiceRest.getTopUsersInCity(city)))
                .build();
    }
}
