package ru.team.up.input.controller.privateController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Event;
import ru.team.up.core.mappers.EventMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.EventService;
import ru.team.up.dto.*;
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
@RequestMapping("private/event")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "EventController", description = "Приватный адрес для работы с events")
public class EventController {
    private EventService eventService;
    private MonitorProducerService monitoringProducerService;

    /**
     * @return Результат работы метода eventService.getAllEvents()) в виде коллекции EventDto
     * в теле ResponseEntity
     */
    @Operation(
            summary = "Просмотр ивентов"
    )
    @ApiResponse(responseCode = "200", description = "Event получен", content = {
            @Content(mediaType = "application/json")
    })
    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        log.debug("Старт метода ResponseEntity<List<EventDto>> getAllEvents()");
        if (!ParameterService.getAllEventsEnabled.getValue()) {
            log.debug("Метод getAllEvents выключен параметром getAllEventsEnabled = false");
            throw new RuntimeException("Method getAllEvents is disabled by parameter getAllEventsEnabled");
        }
        List<Event> events = eventService.getAllEvents();
        ResponseEntity<List<EventDto>> responseEntity = ResponseEntity.ok(
                EventMapper.INSTANCE.mapDtoEventToEvent(events));
        log.debug("Сформирован ответ {}", responseEntity);
        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("Количество всех мероприятий ",
                events.size());
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return responseEntity;
    }

    /**
     * @param id Значение ID мероприятия
     * @return Результат работы метода eventService.getOneEvent(id) в виде объекта EventDto
     * в теле ResponseEntity
     */
    @Operation(
            summary = "Просмотр ивента"
    )
    @ApiResponse(responseCode = "200", description = "Event получен", content = {
            @Content(mediaType = "application/json")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getOneEvent(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<EventDto> getOneEvent(@PathVariable Long id) с параметром {}", id);
        if (!ParameterService.getOneEventEnabled.getValue()) {
            log.debug("Метод getOneEvent выключен параметром getOneEventEnabled = false");
            throw new RuntimeException("Method getOneEvent is disabled by parameter getOneEventEnabled");
        }
        Event event = eventService.getOneEvent(id);
        ResponseEntity<EventDto> responseEntity = ResponseEntity.ok(
                EventMapper.INSTANCE.mapEventToDto(event)
        );
        log.debug("Сформирован ответ {}", responseEntity);
        Map<String, Object> monitoringParameters = new LinkedHashMap<>();
        monitoringParameters.put("Id мероприятия ", event.getId());
        monitoringParameters.put("Название мероприятия ", event.getEventName());
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return responseEntity;
    }

    /**
     * @param id Значение ID мероприятия
     * @return Результат работы метода eventService.updateNumberOfViews(id)
     * в виде объекта ResponseEntity со статусом OK
     */
    @Operation(
            summary = "Увеличение числа участников мероприятия на 1"
    )
    @ApiResponse(responseCode = "202", description = "Число участников увеличено", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = EventDto.class))
    })
    @GetMapping("viewEvent/{id}")
    public ResponseEntity<EventDto> updateNumberOfParticipants(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Event> updateNumberOfViews(@PathVariable Long id) с параметром {}", id);
        if (!ParameterService.updateNumberOfParticipantsEnabled.getValue()) {
            log.debug("Метод updateNumberOfParticipants выключен параметром updateNumberOfParticipantsEnabled = false");
            throw new RuntimeException("Method updateNumberOfParticipants is disabled by parameter updateNumberOfParticipantsEnabled");
        }
        Event event = eventService.getOneEvent(id);
        eventService.updateNumberOfViews(id);
        ResponseEntity<EventDto> responseEntity = new ResponseEntity<>(
                EventMapper.INSTANCE.mapEventToDto(eventService.getOneEvent(id)), HttpStatus.ACCEPTED);
        log.debug("Сформирован ответ {}", responseEntity);
        Map<String, Object> monitoringParameters = new LinkedHashMap<>();
        monitoringParameters.put("Id мероприятия ", event.getId());
        monitoringParameters.put("Название мероприятия ", event.getEventName());
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return responseEntity;
    }

    /**
     * @param eventCreate Создаваемый объект класса Event
     * @return Результат работы метода eventService.saveEvent(event) в виде объекта Event
     * в теле ResponseEntity
     */
    @Operation(
            summary = "Создание ивента",
            description = "Создание ивента"
    )
    @ApiResponse(responseCode = "201", description = "Event добавлен", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))
    })
    @ApiResponse(responseCode = "400", description = "Event не добавлен", content = {
            @Content(mediaType = "application/json")
    })
    @ApiResponse(responseCode = "403", description = "Попытка сохранения события как другой пользователь", content = {
            @Content(mediaType = "application/json")
    })
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody @NotNull Event eventCreate) {
        log.debug("Старт метода ResponseEntity<Event> createEvent(@RequestBody @NotNull Event event) с параметром {}", eventCreate);
        if (!ParameterService.createEventEnabled.getValue()) {
            log.debug("Метод createEvent выключен параметром createEventEnabled = false");
            throw new RuntimeException("Method createEvent is disabled by parameter createEventEnabled");
        }
        try {
            ResponseEntity<Event> responseEntity = new ResponseEntity<>(eventService.saveEvent(eventCreate), HttpStatus.CREATED);

            Map<String, Object> monitoringParameters = new LinkedHashMap<>();
            monitoringParameters.put("Id мероприятия", eventCreate.getId());
            monitoringParameters.put("Название мероприятия", eventCreate.getEventName());

            monitoringProducerService.send(
                    monitoringProducerService.constructReportDto(
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                            this.getClass(), monitoringParameters));

            log.debug("Сформирован ответ {}", responseEntity);
            return responseEntity;
        } catch (PersistenceException e) {
            ResponseEntity<Event> responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            log.debug("Сформирован ответ {}", responseEntity);
            return responseEntity;
        } catch (SecurityException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * @param id id обновляемого ивента
     * @param event Обновляемый объект класса Event
     * @return Результат работы метода eventService.saveEvent(event)) в виде объекта Event
     * в теле ResponseEntity
     */
    @Operation(
            summary = "Редактирование ивента",
            description = "Редактирование ивента"
    )
    @ApiResponse(responseCode = "200", description = "Event отредактирован", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))
    })
    @ApiResponse(responseCode = "400", description = "Event не отредактирован", content = {
            @Content(mediaType = "application/json")
    })
    @ApiResponse(responseCode = "403", description = "Попытка изменения чужого события", content = {
            @Content(mediaType = "application/json")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody @NotNull Event event) {
        log.debug("Старт метода ResponseEntity<Event> updateEvent(@RequestBody @NotNull Event event) с параметром {}", event);
        if (!ParameterService.updateEventEnabled.getValue()) {
            log.debug("Метод updateEvent выключен параметром updateEventEnabled = false");
            throw new RuntimeException("Method updateEvent is disabled by parameter updateEventEnabled");
        }
        if (!id.equals(event.getId())) {
            log.warn("Введен некорректный id");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            ResponseEntity<Event> responseEntity = ResponseEntity.ok(eventService.updateEvent(event));

            Map<String, Object> monitoringParameters = new LinkedHashMap<>();
            monitoringParameters.put("Id мероприятия", event.getId());
            monitoringParameters.put("Название мероприятия", event.getEventName());

            monitoringProducerService.send(
                    monitoringProducerService.constructReportDto(
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                            this.getClass(), monitoringParameters));

            log.debug("Сформирован ответ {}", responseEntity);

            return responseEntity;
        } catch (PersistenceException e) {
            ResponseEntity<Event> responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            log.debug("Сформирован ответ {}", responseEntity);
            return responseEntity;
        } catch (SecurityException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * @param id объект класса Event
     * @return Объект ResponseEntity со статусом OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteAdmin(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Event> deleteAdmin(@PathVariable Long id) с параметром {}", id);
        if (!ParameterService.deleteAdminEnabled.getValue()) {
            log.debug("Метод deleteAdmin выключен параметром deleteAdminEnabled = false");
            throw new RuntimeException("Method deleteAdmin is disabled by parameter deleteAdminEnabled");
        }

        Event event = eventService.getOneEvent(id);

        eventService.deleteEvent(id);
        log.debug("Событие с id = {} было успешно удалено", id);

        ResponseEntity<Event> responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        log.debug("Сформирован ответ {}", responseEntity);

        Map<String, Object> monitoringParameters = new LinkedHashMap<>();
        monitoringParameters.put("Id мероприятия", event.getId());
        monitoringParameters.put("Название мероприятия", event.getEventName());

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return responseEntity;
    }
}
