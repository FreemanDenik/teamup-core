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
import ru.team.up.core.mappers.EventMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.dto.*;
import ru.team.up.input.exception.EventCheckException;
import ru.team.up.input.exception.EventCreateRequestException;
import ru.team.up.input.payload.request.EventRequest;
import ru.team.up.input.payload.request.JoinRequest;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.response.EventDtoListResponse;
import ru.team.up.input.response.EventDtoResponse;
import ru.team.up.input.service.EventServiceRest;
import ru.team.up.input.wordmatcher.WordMatcher;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Date;
import java.util.HashMap;
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
    private final EventServiceRest eventServiceRest;
    private final WordMatcher wordMatcher;
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
        EventDtoListResponse eventDtoListResponse = EventDtoListResponse.builder().eventDtoList(
                        EventMapper.INSTANCE.mapDtoEventToEvent(eventServiceRest.getAllEvents()))
                .build();

        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("999 " + o);

        //log.debug("поискЖ " + accountDto.getFirstName() + "  " + accountDto.getId() + "  " + accountDto.getRole());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Количество мероприятий в списке: ", eventDtoListResponse.getEventDtoList().size());

        ReportDto reportDto = ReportDto.builder()
                .control(ControlDto.MANUAL)
                        .appModuleName(AppModuleNameDto.TEAMUP_CORE)
                                .initiatorType(InitiatorTypeDto.USER)
                                        .initiatorName("VASYA TEST")
                                                .initiatorId(777L)
                                                        .time(new Date())
                                                                .status(StatusDto.builder().SUCCESS("Success").build())
                                                                        .parameters(parameters).build();
        monitoringProducerService.send(reportDto);

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

        return EventDtoResponse.builder().eventDto(
                EventMapper.INSTANCE.mapEventToDto(
                        eventServiceRest.getEventById(eventId))).build();
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

        return EventDtoListResponse.builder().eventDtoList(
                EventMapper.INSTANCE.mapDtoEventToEvent(eventServiceRest.getAllEventsByCity(city)))
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

        return EventDtoListResponse.builder().eventDtoList(
                                EventMapper.INSTANCE.mapDtoEventToEvent(eventServiceRest.getEventByName(eventName)))
                        .build();
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
        List<Event> events = eventServiceRest.getAllEventsByAuthor(author.getUser().getId());

        if (events.isEmpty()) {
            log.error("Мероприятия по указанному автору {} не найдены", author);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.debug("Мероприятия от автора {} найдены", author);
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
        List<Event> events = eventServiceRest.getAllEventsByEventType(eventType);

        if (events.isEmpty()) {
            log.error("Мероприятия с типом {} не найдены", eventType);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        log.debug("Мероприятия с типом: {} найдены", eventType);
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

        checkEvent(event);

        log.debug("Мероприятие создано");
        Event upcomingEvent = eventServiceRest.saveEvent(event.getEvent());

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

        checkEvent(event);

        log.debug("Мероприятие {} обновлено", event);
        Event newEvent = eventServiceRest.updateEvent(eventId, event.getEvent());

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
        Event event = eventServiceRest.getEventById(eventId);

        if (event == null) {
            log.error("Мероприятие с id: {} не найдено", eventId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        eventServiceRest.deleteEvent(eventId);

        log.debug("Мероприятие с id: {} успешно удалено", eventId);
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
        Event event = eventServiceRest.addParticipant(joinRequest.getEventId(), joinRequest.getUserId());

        log.debug("Участник успешно добавлен");
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
        Event event = eventServiceRest.deleteParticipant(joinRequest.getEventId(), joinRequest.getUserId());

        log.debug("Участник успешно удален");
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
