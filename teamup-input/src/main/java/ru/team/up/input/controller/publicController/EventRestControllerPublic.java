package ru.team.up.input.controller.publicController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.EventType;
import ru.team.up.core.entity.User;
import ru.team.up.core.mappers.EventMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.UserService;
import ru.team.up.dto.ControlDto;
import ru.team.up.dto.ParametersDto;
import ru.team.up.input.exception.EventCheckException;
import ru.team.up.input.exception.EventCreateRequestException;
import ru.team.up.input.payload.request.EventRequest;
import ru.team.up.input.payload.request.JoinRequest;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.response.EventDtoListResponse;
import ru.team.up.input.response.EventDtoResponse;
import ru.team.up.input.service.EventServiceRest;
import ru.team.up.input.wordmatcher.WordMatcher;
import ru.team.up.sup.service.ParameterService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * REST-контроллер для мероприятий
 *
 * @author Pavel Kondrashov
 * @link localhost:8080/swagger-ui.html
 * Документация API
 */
@Slf4j
@Tag(name = "Event Public Controller", description = "Event API")
@RestController
@RequestMapping(value = "public/event")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventRestControllerPublic {
    private EventServiceRest eventServiceRest;
    private WordMatcher wordMatcher;
    private UserService userService;
    private MonitorProducerService monitoringProducerService;


    /**
     * Метод получения списка всех мероприятий
     *
     * @return Список мероприятий и статус ответа
     */
    @Operation(summary = "Получение списка всех мероприятий")
    @GetMapping
    public EventDtoListResponse getAllEvents() {
        log.debug("Получение запроса на список мероприятий");
        if (!ParameterService.getAllEventsEnabled.getValue()) {
            log.debug("Метод getAllEvents выключен параметром getAllEventsEnabled = false");
            throw new RuntimeException("Method getAllEvents is disabled by parameter getAllEventsEnabled");
        }
        EventDtoListResponse eventDtoListResponse = EventDtoListResponse.builder().eventDtoList(
                        EventMapper.INSTANCE.mapDtoEventToEvent(eventServiceRest.getAllEvents()))
                .build();
        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto amountEvents = ParametersDto.builder()
                .description("Количество всех мероприятий ")
                .value(eventDtoListResponse.getEventDtoList().size())
                .build();

        monitoringParameters.put("Количество всех мероприятий ", amountEvents);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return eventDtoListResponse;
    }

    /**
     * Метод получения мероприятия по идентификатору
     *
     * @param eventId Идентификатор мероприятия
     * @return Ответ запроса и статус проверки
     */
    @Operation(summary = "Получение мероприятия по идентификатору")
    @GetMapping(value = "/id/{id}")
    public EventDtoResponse findEventById(@PathVariable("id") Long eventId) {
        log.debug("Получен запрос на поиск мероприятия по id: {}", eventId);
        if (!ParameterService.getEventByIdEnabled.getValue()) {
            log.debug("Метод findEventById выключен параметром getEventByIdEnabled = false");
            throw new RuntimeException("Method findEventById is disabled by parameter getEventByIdEnabled");
        }
        EventDtoResponse eventDtoResponse = EventDtoResponse.builder().eventDto(
                EventMapper.INSTANCE.mapEventToDto(
                        eventServiceRest.getEventById(eventId))).build();

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto findEventId = ParametersDto.builder()
                .description("ID мероприятия ")
                .value(eventDtoResponse.getEventDto().getId())
                .build();

        ParametersDto eventName = ParametersDto.builder()
                .description("азвание мероприятия ")
                .value(eventDtoResponse.getEventDto().getEventName())
                .build();

        monitoringParameters.put("ID мероприятия", findEventId);
        monitoringParameters.put("Название мероприятия", eventName);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return eventDtoResponse;
    }

    /**
     * Метод поиска мероприятий по городу
     *
     * @param city название города
     * @return список мероприятий в городе
     */
    @Operation(summary = "Поиск мероприятий по city")
    @GetMapping(value = "/city/{city}")
    public EventDtoListResponse getAllEventByCity(@PathVariable String city) {
        log.debug("Запрос на поиск мероприятий по городу city: {}", city);
        if (!ParameterService.getAllEventByCityEnabled.getValue()) {
            log.debug("Метод getAllEventByCity выключен параметром getAllEventByCityEnabled = false");
            throw new RuntimeException("Method getAllEventByCity is disabled by parameter getAllEventByCityEnabled");
        }
        EventDtoListResponse eventDtoListResponse = EventDtoListResponse.builder().eventDtoList(
                        EventMapper.INSTANCE.mapDtoEventToEvent(eventServiceRest.getAllEventsByCity(city)))
                .build();

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto eventList = ParametersDto.builder()
                .description("Количество всех мероприятий по городу: " + city)
                .value(eventDtoListResponse.getEventDtoList().size())
                .build();

        monitoringParameters.put("Количество всех мероприятий по городу: " + city,
                eventList);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return eventDtoListResponse;
    }

    /**
     * Метод поиска мероприятий по городу с лимитом
     *
     * @param city название города
     * @param limit лимит на отображение
     * @return список мероприятий в городе учитывая лимит
     */
    @Operation(summary = "Поиск мероприятий по city с limit")
    @GetMapping(value = "/city/{city}/{limit}")
    public EventDtoListResponse getAllEventByCityByLimit(@PathVariable String city, @PathVariable int limit) {
        return EventDtoListResponse.builder().eventDtoList(
                        EventMapper.INSTANCE.mapDtoEventToEvent(eventServiceRest.getAllEventsByCityByLimit(city, limit)))
                .build();
    }

    /**
     * Метод получения мероприятий по названию
     *
     * @param eventName Название мероприятия
     * @return Ответ запроса и статус проверки
     */
    @Operation(summary = "Получение мероприятий по названию")
    @GetMapping(value = "/name/{eventName}")
    public EventDtoListResponse findEventsByName(@PathVariable("eventName") String eventName) {
        log.debug("Получен запрос на поиск мероприятий по названию {}", eventName);
        if (!ParameterService.getFindEventsByNameEnabled.getValue()) {
            log.debug("Метод findEventsByName выключен параметром getfindEventsByNameEnabled = false");
            throw new RuntimeException("Method findEventsByName is disabled by parameter getfindEventsByNameEnabled");
        }

        EventDtoListResponse eventDtoListResponse = EventDtoListResponse.builder().eventDtoList(
                        EventMapper.INSTANCE.mapDtoEventToEvent(eventServiceRest.getEventByName(eventName)))
                .build();
        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto eventListByName = ParametersDto.builder()
                .description("Количество всех мероприятий по названию: " + eventName)
                .value(eventDtoListResponse.getEventDtoList().size())
                .build();
        monitoringParameters.put("Количество всех мероприятий по названию: " + eventName,
                eventListByName);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return eventDtoListResponse;
    }

    /**
     * Метод получения мероприятий по автору
     *
     * @param author Автор мероприятия
     * @return Ответ запроса и статус проверки
     */
    @Operation(summary = "Получение мероприятий по автору")
    @GetMapping(value = "/author")
    public ResponseEntity<List<Event>> findEventsByAuthor(@RequestBody UserRequest author) {
        log.debug("Получен запрос на поиск мероприятий по автору {}", author);
        if (!ParameterService.getFindEventsByAuthorEnabled.getValue()) {
            log.debug("Метод findEventsByAuthor выключен параметром getFindEventsByAuthorEnabled = false");
            throw new RuntimeException("Method findEventsByAuthor is disabled by parameter getFindEventsByAuthorEnabled");
        }
        List<Event> events = eventServiceRest.getAllEventsByAuthor(author.getUser().getId());
        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto eventListByAuthor = ParametersDto.builder()
                .description("Количество всех мероприятий по автору: " + author)
                .value(events.size())
                .build();
        if (events.isEmpty()) {
            log.error("Мероприятия по указанному автору {} не найдены", author);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.debug("Мероприятия от автора {} найдены", author);
        monitoringParameters.put("Количество всех мероприятий по автору: " + author, eventListByAuthor);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * Метод поиска мероприятия по типу
     *
     * @param eventType Тип мероприятия
     * @return Ответ запроса и статус проверки
     */
    @Operation(summary = "Получение мероприятий по типу")
    @GetMapping(value = "/type")
    public ResponseEntity<List<Event>> findEventsByType(@RequestBody EventType eventType) {
        log.debug("Получен запрос на поиск мероприятий по типу: {}", eventType);
        if (!ParameterService.getFindEventsByTypeEnabled.getValue()) {
            log.debug("Метод findEventsByType выключен параметром getFindEventsByTypeEnabled = false");
            throw new RuntimeException("Method findEventsByType is disabled by parameter getFindEventsByTypeEnabled");
        }
        List<Event> events = eventServiceRest.getAllEventsByEventType(eventType);
        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto eventListByType = ParametersDto.builder()
                .description("Количество всех мероприятий по типу: " + eventType)
                .value(events.size())
                .build();
        if (events.isEmpty()) {
            log.error("Мероприятия с типом {} не найдены", eventType);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.debug("Мероприятия с типом: {} найдены", eventType);
        monitoringParameters.put("Количество всех мероприятий по типу: " + eventType,
                eventListByType);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * Метод создания мероприятия
     *
     * @param event Данные мероприятия
     * @return Ответ запроса и статус проверки
     */
    @Operation(summary = "Создание нового мероприятия")
    @PostMapping(value = "/")
    public ResponseEntity<Event> createEvent(@RequestBody EventRequest event) {
        log.debug("Получен запрос на создание мероприятия:\n {}", event);
        if (!ParameterService.getCreateEventEnabled.getValue()) {
            log.debug("Метод createEvent выключен параметром getCreateEventEnabled = false");
            throw new RuntimeException("Method createEvent is disabled by parameter getCreateEventEnabled");
        }

        checkEvent(event);

        Event upcomingEvent = eventServiceRest.saveEvent(event.getEvent());
        log.debug("Мероприятие создано");

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto eventIdNew = ParametersDto.builder()
                .description("ID мероприятия ")
                .value(upcomingEvent.getId())
                .build();

        ParametersDto eventNameNew = ParametersDto.builder()
                .description("Название мероприятия ")
                .value(upcomingEvent.getEventName())
                .build();

        monitoringParameters.put("ID мероприятия ", eventIdNew);
        monitoringParameters.put("Название мероприятия ", eventNameNew);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return new ResponseEntity<>(upcomingEvent, HttpStatus.CREATED);
    }

    /**
     * Метод обновления мероприятия
     *
     * @param event   Данные мероприятия
     * @param eventId Идентификатор мероприятия
     * @return Ответ запроса и статус проверки
     */
    @Operation(summary = "Обновление мероприятия")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Event> updateEvent(@RequestBody EventRequest event, @PathVariable("id") Long eventId) {
        log.debug("Получен запрос на обновление мероприятия {}", event);
        if (!ParameterService.getUpdateEventEnabled.getValue()) {
            log.debug("Метод updateEvent выключен параметром getUpdateEventEnabled = false");
            throw new RuntimeException("Method updateEvent is disabled by parameter getUpdateEventEnabled");
        }

        checkEvent(event);

        Event newEvent = eventServiceRest.updateEvent(eventId, event.getEvent());
        log.debug("Мероприятие {} обновлено", event);

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto eventIdUpdate = ParametersDto.builder()
                .description("ID мероприятия ")
                .value(newEvent.getId())
                .build();

        ParametersDto eventNameUpdate = ParametersDto.builder()
                .description("Название мероприятия ")
                .value(newEvent.getEventName())
                .build();
        monitoringParameters.put("ID мероприятия ", eventIdUpdate);
        monitoringParameters.put("Название мероприятия ", eventNameUpdate);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));


        return new ResponseEntity<>(newEvent, HttpStatus.OK);
    }

    /**
     * Метод удаления мероприятия по идентификатору
     *
     * @param eventId Идентификатор мероприятия
     * @return Ответ запроса и статус проверки
     */
    @Operation(summary = "Удаление мероприятия по идентификатору")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable("id") Long eventId) {
        log.debug("Получен запрос на удаление мероприятия с id: {}", eventId);
        if (!ParameterService.getDeleteEventEnabled.getValue()) {
            log.debug("Метод deleteEvent выключен параметром getDeleteEventEnabled = false");
            throw new RuntimeException("Method deleteEvent is disabled by parameter getDeleteEventEnabled");
        }
        Event event = eventServiceRest.getEventById(eventId);

        if (event == null) {
            log.error("Мероприятие с id: {} не найдено", eventId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        eventServiceRest.deleteEvent(eventId);
        log.debug("Мероприятие с id: {} успешно удалено", eventId);

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto eventIdDel = ParametersDto.builder()
                .description("ID мероприятия ")
                .value(event.getId())
                .build();

        ParametersDto eventNameDel = ParametersDto.builder()
                .description("Название мероприятия ")
                .value(event.getEventName())
                .build();

        monitoringParameters.put("ID мероприятия ", eventIdDel);
        monitoringParameters.put("Название мероприятия ", eventNameDel);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Метод добавления участника мероприятия
     *
     * @param joinRequest Данные запроса для добавление участника
     * @return Ответ запроса и статус проверки
     */
    @Operation(summary = "Добавление участника мероприятия")
    @PostMapping(value = "/join")
    public ResponseEntity<Event> addEventParticipant(@RequestBody JoinRequest joinRequest) {
        log.debug("Получен запрос на добавление участника мероприятия");
        if (!ParameterService.getAddEventParticipantEnabled.getValue()) {
            log.debug("Метод addEventParticipant выключен параметром getAddEventParticipantEnabled = false");
            throw new RuntimeException("Method addEventParticipant is disabled by parameter getAddEventParticipantEnabled");
        }

        Event event = eventServiceRest.addParticipant(joinRequest.getEventId(), joinRequest.getUserId());
        Long userId = joinRequest.getUserId();
        User user = userService.getOneUser(userId).orElse(null);
        log.debug("Участник успешно добавлен");

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto eventIdAdd = ParametersDto.builder()
                .description("ID мероприятия ")
                .value(event.getId())
                .build();

        ParametersDto eventNameAdd = ParametersDto.builder()
                .description("Название мероприятия ")
                .value(event.getEventName())
                .build();

        ParametersDto userIdAdd = ParametersDto.builder()
                .description("ID участника ")
                .value(user.getId())
                .build();

        ParametersDto userEmailAdd = ParametersDto.builder()
                .description("Email участника ")
                .value(user.getEmail())
                .build();

        ParametersDto userNameAdd = ParametersDto.builder()
                .description("Имя участника ")
                .value(user.getUsername())
                .build();

        monitoringParameters.put("ID мероприятия ", eventIdAdd);
        monitoringParameters.put("Название мероприятия ", eventNameAdd);
        monitoringParameters.put("ID участника ", userIdAdd);
        monitoringParameters.put("Email участника ", userEmailAdd);
        monitoringParameters.put("Имя участника ", userNameAdd);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    /**
     * Метод удаления участника мероприятия
     *
     * @param joinRequest Данные запроса для удаления участника
     * @return Ответ запроса и статус проверки
     */
    @Operation(summary = "Удаление участника мероприятия")
    @PostMapping("/unjoin")
    public ResponseEntity<Event> deleteEventParticipant(@RequestBody JoinRequest joinRequest) {
        log.debug("Получен запрос на удаление участника мероприятия");
        if (!ParameterService.getDeleteEventParticipantEnabled.getValue()) {
            log.debug("Метод deleteEventParticipant выключен параметром getDeleteEventParticipantEnabled = false");
            throw new RuntimeException("Method deleteEventParticipant is disabled by parameter getDeleteEventParticipantEnabled");
        }

        Long userId = joinRequest.getUserId();
        User user = userService.getOneUser(userId).orElse(null);
        if (user == null) {
            log.debug("Участника с id = {} не существует", userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Event event = eventServiceRest.deleteParticipant(joinRequest.getEventId(), joinRequest.getUserId());
        log.debug("Участник успешно удален");

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto eventIdDel = ParametersDto.builder()
                .description("ID мероприятия ")
                .value(event.getId())
                .build();

        ParametersDto eventNameDel = ParametersDto.builder()
                .description("Название мероприятия ")
                .value(event.getEventName())
                .build();

        ParametersDto userIdDel = ParametersDto.builder()
                .description("ID участника ")
                .value(user.getId())
                .build();

        ParametersDto userEmailDel = ParametersDto.builder()
                .description("Email участника ")
                .value(user.getEmail())
                .build();

        ParametersDto userNameDel = ParametersDto.builder()
                .description("Имя участника ")
                .value(user.getUsername())
                .build();

        monitoringParameters.put("ID мероприятия ", eventIdDel);
        monitoringParameters.put("Название мероприятия ", eventNameDel);
        monitoringParameters.put("ID участника ", userIdDel);
        monitoringParameters.put("Email участника ", userEmailDel);
        monitoringParameters.put("Имя участника ", userNameDel);



        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    /**
     * Метод проверки мероприятия
     *
     * @param event Данные мероприятия
     */
    private void checkEvent(EventRequest event) {
        if (wordMatcher.detectBadWords(event.getEvent().getEventName()) ||
                wordMatcher.detectBadWords(event.getEvent().getDescriptionEvent())) {
            log.error("Имя или описание мероприятия содержит запрещенные слова:\n {}", event);
            throw new EventCreateRequestException("Имя или описание мероприятия содержит запрещенные слова");
        }

        if (ChronoUnit.YEARS.between(event.getEvent().getTimeEvent(), LocalDateTime.now()) >= 1) {
            log.error("Дата создания мероприятия более 1 года:\n {}", event);
            throw new EventCreateRequestException("Дата создания мероприятия более 1 года");
        }

        if (wordMatcher.detectUnnecessaryWords(event.getEvent().getEventName()) ||
                wordMatcher.detectUnnecessaryWords(event.getEvent().getDescriptionEvent())) {
            log.debug("Мероприятие отправлено на проверку:\n {}", event);
            throw new EventCheckException("Мероприятие отправлено на проверку");
        }
    }
}
