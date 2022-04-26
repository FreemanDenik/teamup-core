package ru.team.up.input.controller.privateController;

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
@RequestMapping("private/event")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {
    private EventService eventService;
    private MonitorProducerService monitoringProducerService;

    /**
     * @return Результат работы метода eventService.getAllEvents()) в виде коллекции EventDto
     * в теле ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        log.debug("Старт метода ResponseEntity<List<EventDto>> getAllEvents()");
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
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getOneEvent(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<EventDto> getOneEvent(@PathVariable Long id) с параметром {}", id);
        Event event = eventService.getOneEvent(id);
        ResponseEntity<EventDto> responseEntity = ResponseEntity.ok(
                EventMapper.INSTANCE.mapEventToDto(event)
        );
        log.debug("Сформирован ответ {}", responseEntity);
        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("ID мероприятия ", event.getId());
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
    @GetMapping("viewEvent/{id}")
    public ResponseEntity<Event> updateNumberOfViews(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Event> updateNumberOfViews(@PathVariable Long id) с параметром {}", id);
        Event event = eventService.getOneEvent(id);
        eventService.updateNumberOfViews(id);
        ResponseEntity<Event> responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        log.debug("Сформирован ответ {}", responseEntity);
        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("ID мероприятия ", event.getId());
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
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody @NotNull Event eventCreate) {
        log.debug("Старт метода ResponseEntity<Event> createEvent(@RequestBody @NotNull Event event) с параметром {}", eventCreate);

        try {
            ResponseEntity<Event> responseEntity = new ResponseEntity<>(eventService.saveEvent(eventCreate), HttpStatus.CREATED);
            log.debug("Сформирован ответ {}", responseEntity);
            return responseEntity;
        } catch (PersistenceException e) {
            ResponseEntity<Event> responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            log.debug("Сформирован ответ {}", responseEntity);
            return responseEntity;
        }
    }

    /**
     * @param event Обновляемый объект класса Event
     * @return Результат работы метода eventService.saveEvent(event)) в виде объекта Event
     * в теле ResponseEntity
     */
    @PutMapping
    public ResponseEntity<Event> updateEvent(@RequestBody @NotNull Event event) {
        log.debug("Старт метода ResponseEntity<Event> updateEvent(@RequestBody @NotNull Event event) с параметром {}", event);
        try {
            ResponseEntity<Event> responseEntity = ResponseEntity.ok(eventService.saveEvent(event));
            log.debug("Сформирован ответ {}", responseEntity);
            return responseEntity;

        } catch (PersistenceException e) {
            ResponseEntity<Event> responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            log.debug("СформированСформирован ответ {}", responseEntity);
            return responseEntity;
        }
    }

    /**
     * @param id объект класса Event
     * @return Объект ResponseEntity со статусом OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteAdmin(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Event> deleteAdmin(@PathVariable Long id) с параметром {}", id);

        eventService.deleteEvent(id);

        ResponseEntity<Event> responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        log.debug("Сформирован ответ {}", responseEntity);

        return responseEntity;
    }
}
