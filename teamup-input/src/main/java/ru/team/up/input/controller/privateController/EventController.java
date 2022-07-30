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
@Tag(name = "Event Private Controller", description = "Приватный адрес для работы с events")
public class EventController {
    private EventService eventService;
    private MonitorProducerService monitoringProducerService;

    /**
     * @return Результат работы метода eventService.getAllEvents()) в виде коллекции EventDto
     */
    @Operation(
            summary = "Просмотр ивентов"
    )
    @ApiResponse(responseCode = "200", description = "Event получен", content = {
            @Content(mediaType = "application/json")
    })
    @GetMapping
    public List<EventDto> getAllEvents() {
        log.debug("Старт метода List<EventDto> getAllEvents()");
        if (!ParameterService.getAllEventsPrivateEnabled.getValue()) {
            log.debug("Метод getAllEvents выключен параметром getAllEventsPrivateEnabled = false");
            throw new RuntimeException("Method getAllEvents is disabled by parameter getAllEventsPrivateEnabled");
        }

        List<Event> events = eventService.getAllEvents();
        List<EventDto> eventsDtoList =
                EventMapper.INSTANCE.mapDtoEventToEvent(events);
        log.debug("Сформирован ответ getAllEvents - SUCCESSFULLY");

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto eventsSize = ParametersDto.builder()
                .description("Количество всех мероприятий")
                .value(events.size())
                .build();

        monitoringParameters.put("Количество всех мероприятий ", eventsSize);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return eventsDtoList;
    }

    /**
     * @param id Значение ID мероприятия
     * @return Результат работы метода eventService.getOneEvent(id) в виде объекта EventDto
     */
    @Operation(
            summary = "Просмотр ивента"
    )
    @ApiResponse(responseCode = "200", description = "Event получен", content = {
            @Content(mediaType = "application/json")
    })
    @GetMapping("/{id}")
    public EventDto getOneEvent(@PathVariable Long id) {
        log.debug("Старт метода EventDto getOneEvent(@PathVariable Long id) с параметром {}", id);
        if (!ParameterService.getOneEventEnabled.getValue()) {
            log.debug("Метод getOneEvent выключен параметром getOneEventEnabled = false");
            throw new RuntimeException("Method getOneEvent is disabled by parameter getOneEventEnabled");
        }
        Event event = eventService.getOneEvent(id);
        EventDto eventDtoId =
                EventMapper.INSTANCE.mapEventToDto(event);
        log.debug("Сформирован ответ getOneEvent - SUCCESSFULLY");

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto oneEventId = ParametersDto.builder()
                .description("Id мероприятия ")
                .value(event.getId())
                .build();

        ParametersDto oneEventName = ParametersDto.builder()
                .description("Название мероприятия ")
                .value(event.getEventName())
                .build();

        monitoringParameters.put("Id мероприятия ", oneEventId);
        monitoringParameters.put("Название мероприятия ", oneEventName);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return eventDtoId;
    }

    /**
     * @param id Значение ID мероприятия
     * @return Результат работы метода eventService.updateNumberOfViews(id)
     */
    @Operation(
            summary = "Увеличение числа участников мероприятия на 1"
    )
    @ApiResponse(responseCode = "202", description = "Число участников увеличено", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = EventDto.class))
    })
    @GetMapping("viewEvent/{id}")
    public EventDto updateNumberOfParticipants(@PathVariable Long id) {
        log.debug("Старт метода EventDto updateNumberOfViews(@PathVariable Long id) с параметром {}", id);
        if (!ParameterService.updateNumberOfParticipantsEnabled.getValue()) {
            log.debug("Метод updateNumberOfParticipants выключен параметром updateNumberOfParticipantsEnabled = false");
            throw new RuntimeException("Method updateNumberOfParticipants is disabled by parameter updateNumberOfParticipantsEnabled");
        }
        Event event = eventService.getOneEvent(id);
        eventService.updateNumberOfViews(id);
        EventDto eventDtoNumOfPartic =
                EventMapper.INSTANCE.mapEventToDto(eventService.getOneEvent(id));
        log.debug("Сформирован ответ updateNumberOfParticipants- SUCCESSFULLY");

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto eventId = ParametersDto.builder()
                .description("Id мероприятия ")
                .value(event.getId())
                .build();

        ParametersDto eventName = ParametersDto.builder()
                .description("Название мероприятия ")
                .value(event.getEventName())
                .build();

        monitoringParameters.put("Id мероприятия ", eventId);
        monitoringParameters.put("Название мероприятия ", eventName);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return eventDtoNumOfPartic;
    }

    /**
     * @param id Значение ID мероприятия
     * @return Результат работы метода eventService.incrementCountViewEvent(id)
     */
    @Operation(
            summary = "Увеличение количества просмотров мероприятия на 1"
    )
    @ApiResponse(responseCode = "202", description = "Количество просмотров мероприятитя увеличено на 1", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = EventDto.class))
    })
    @GetMapping("countEvent/{id}")
    public EventDto incrementCountViewEvent(@PathVariable Long id) {
        log.debug("Старт метода Event incrementCountViewEvent(@PathVariable Long id) с параметром {}", id);

        Event event = eventService.getOneEvent(id);
        eventService.incrementCountViewEvent(id);
        EventDto eventDtoIncrCountOfViewEvent =
                EventMapper.INSTANCE.mapEventToDto(eventService.getOneEvent(id));
        log.debug("Сформирован ответ incrementCountViewEvent - SUCCESSFULLY");

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto eventId = ParametersDto.builder()
                .description("Id мероприятия ")
                .value(event.getId())
                .build();

        ParametersDto eventName = ParametersDto.builder()
                .description("Название мероприятия ")
                .value(event.getEventName())
                .build();

        monitoringParameters.put("Id мероприятия ", eventId);
        monitoringParameters.put("Название мероприятия ", eventName);
        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));
        return eventDtoIncrCountOfViewEvent;
    }

    /**
     * @param eventCreate Создаваемый объект класса EventDto
     * @return Результат работы метода eventService.saveEvent(event) в виде объекта EventDto
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
    public EventDto createEvent(@RequestBody @NotNull Event eventCreate) {
        log.debug("Старт метода EventDto createEvent(@RequestBody @NotNull Event event) с параметром {}", eventCreate);
        if (!ParameterService.createEventEnabled.getValue()) {
            log.debug("Метод createEvent выключен параметром createEventEnabled = false");
            throw new RuntimeException("Method createEvent is disabled by parameter createEventEnabled");
        }
        try {
            Event event = eventService.saveEvent(eventCreate);
            EventDto eventDtoNew =
                    EventMapper.INSTANCE.mapEventToDto(event);

            Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

            ParametersDto eventId = ParametersDto.builder()
                    .description("Id мероприятия ")
                    .value(eventCreate.getId())
                    .build();

            ParametersDto eventName = ParametersDto.builder()
                    .description("Название мероприятия ")
                    .value(eventCreate.getEventName())
                    .build();

            monitoringParameters.put("Id мероприятия ", eventId);
            monitoringParameters.put("Название мероприятия ", eventName);


            monitoringProducerService.send(
                    monitoringProducerService.constructReportDto(
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                            this.getClass(), monitoringParameters));

            log.debug("Сформирован ответ createEvent - SUCCESSFULLY");
            return eventDtoNew;
        } catch (PersistenceException e) {
            log.debug(e.getMessage());
            throw new PersistenceException("Нет прав на создание мероприятия");
        } catch (SecurityException e) {
            log.debug(e.getMessage());
            throw new SecurityException("Нет прав на создание мероприятия");
        }
    }

    /**
     * @param id    id обновляемого ивента
     * @param event Обновляемый объект класса EventDto
     * @return Результат работы метода eventService.saveEvent(event)) в виде объекта EventDto
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
    public EventDto updateEvent(@PathVariable Long id, @RequestBody @NotNull Event event) {
        log.debug("Старт метода EventDto updateEvent(@RequestBody @NotNull Event event) с параметром {}", event);
        if (!ParameterService.updateEventEnabled.getValue()) {
            log.debug("Метод updateEvent выключен параметром updateEventEnabled = false");
            throw new RuntimeException("Method updateEvent is disabled by parameter updateEventEnabled");
        }
        if (!id.equals(event.getId())) {
            log.warn("Введен некорректный id");
            throw new RuntimeException("Введен некорректный id");
        }

        try {
            Event eventUp = eventService.updateEvent(event);
            EventDto eventDtoUpdate =
                    EventMapper.INSTANCE.mapEventToDto(eventUp);

            Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

            ParametersDto eventId = ParametersDto.builder()
                    .description("Id мероприятия ")
                    .value(event.getId())
                    .build();

            ParametersDto eventName = ParametersDto.builder()
                    .description("Название мероприятия ")
                    .value(event.getEventName())
                    .build();

            monitoringParameters.put("Id мероприятия ", eventId);
            monitoringParameters.put("Название мероприятия ", eventName);

            monitoringProducerService.send(
                    monitoringProducerService.constructReportDto(
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                            this.getClass(), monitoringParameters));

            log.debug("Сформирован ответ updateEvent - SUCCESSFULLY");

            return eventDtoUpdate;
        } catch (PersistenceException e) {
            log.debug(e.getMessage());
            throw new PersistenceException("Нет прав на создание мероприятия");
        } catch (SecurityException e) {
            log.debug(e.getMessage());
            throw new SecurityException("Нет прав на создание мероприятия");
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

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto eventId = ParametersDto.builder()
                .description("Id мероприятия ")
                .value(event.getId())
                .build();

        ParametersDto eventName = ParametersDto.builder()
                .description("Название мероприятия ")
                .value(event.getEventName())
                .build();

        monitoringParameters.put("Id мероприятия ", eventId);
        monitoringParameters.put("Название мероприятия ", eventName);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return responseEntity;
    }
}
