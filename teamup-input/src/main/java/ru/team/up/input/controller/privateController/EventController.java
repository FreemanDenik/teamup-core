package ru.team.up.input.controller.privateController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Event;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.EventService;
import ru.team.up.dto.AppModuleNameDto;
import ru.team.up.dto.ControlDto;
import ru.team.up.dto.ReportDto;
import ru.team.up.dto.ReportStatusDto;

import javax.persistence.PersistenceException;
import javax.validation.constraints.NotNull;
import java.util.List;


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
     * @return Результат работы метода eventService.getAllEvents()) в виде коллекции мероприятий
     * в теле ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        log.debug("Старт метода ResponseEntity<List<Event>> getAllEvents()");

        ResponseEntity<List<Event>> responseEntity = ResponseEntity.ok(eventService.getAllEvents());
        log.debug("Получили ответ {}", responseEntity);

        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReportDto reportDto = monitoringProducerService.constructReportDto(o, ControlDto.MANUAL,
                "Название контроллера", AppModuleNameDto.TEAMUP_CORE, ReportStatusDto.SUCCESS,
                "Количество всех мероприятий", responseEntity.getBody().size());
        monitoringProducerService.send(reportDto);

        return responseEntity;
    }

    /**
     * @param id Значение ID мероприятия
     * @return Результат работы метода eventService.getOneEvent(id) в виде объекта Event
     * в теле ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getOneEvent(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Event> getOneEvent(@PathVariable Long id) с параметром {}", id);

        ResponseEntity<Event> responseEntity = ResponseEntity.ok(eventService.getOneEvent(id));
        log.debug("Получили ответ {}", responseEntity);
        String dataEvent = responseEntity.getBody().getId() + " "
                + responseEntity.getBody().getEventName();
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReportDto reportDto = monitoringProducerService.constructReportDto(o, ControlDto.MANUAL,
                "Название контроллера", AppModuleNameDto.TEAMUP_CORE, ReportStatusDto.SUCCESS,
                "Id и name Мероприятия полученного по идентификатору ", dataEvent);
        monitoringProducerService.send(reportDto);
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

        eventService.updateNumberOfViews(id);

        ResponseEntity<Event> responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        log.debug("Получили ответ {}", responseEntity);
        String dataEvent = responseEntity.getBody().getId() + " "
                + responseEntity.getBody().getEventName();
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ReportDto reportDto = monitoringProducerService.constructReportDto(o, ControlDto.MANUAL,
                "Название контроллера", AppModuleNameDto.TEAMUP_CORE, ReportStatusDto.SUCCESS,
                "Id и name Мероприятия полученного по идентификатору ", dataEvent);
        monitoringProducerService.send(reportDto);
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
            log.debug("Получили ответ {}", responseEntity);
            return responseEntity;
        } catch (PersistenceException e) {
            ResponseEntity<Event> responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            log.debug("Получили ответ {}", responseEntity);
            return responseEntity;
        }
    }

    /**
     * @param event Обновляемый объект класса Event
     * @return Результат работы метода eventService.saveEvent(event)) в виде объекта Event
     * в теле ResponseEntity
     */
    @PatchMapping
    public ResponseEntity<Event> updateEvent(@RequestBody @NotNull Event event) {
        log.debug("Старт метода ResponseEntity<Event> updateEvent(@RequestBody @NotNull Event event) с параметром {}", event);
        try {
            ResponseEntity<Event> responseEntity = ResponseEntity.ok(eventService.saveEvent(event));
            log.debug("Получили ответ {}", responseEntity);
            return responseEntity;

        } catch (PersistenceException e) {
            ResponseEntity<Event> responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            log.debug("Получили ответ {}", responseEntity);
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
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }
}
