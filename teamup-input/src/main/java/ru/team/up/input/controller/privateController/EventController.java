package ru.team.up.input.controller.privateController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Event;
import ru.team.up.core.service.EventService;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author Alexey Tkachenko
 * @link localhost:8080/swagger-ui.html
 * Документация API
 */

@Slf4j
@RestController
@RequestMapping(value = "private/event", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Private Event RestController", description = "Private Event API")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {
    private EventService eventService;

    /**
     * @return Результат работы метода eventService.getAllEvents()) в виде коллекции мероприятий
     * в теле ResponseEntity
     */
    @GetMapping
    @Operation(summary = "Получить все мероприятия")
    public ResponseEntity<List<Event>> getAllEvents() {
        log.debug("Старт метода ResponseEntity<List<Event>> getAllEvents()");

        ResponseEntity<List<Event>> responseEntity = ResponseEntity.ok(eventService.getAllEvents());
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param id Значение ID мероприятия
     * @return Результат работы метода eventService.getOneEvent(id) в виде объекта Event
     * в теле ResponseEntity
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получить одно мероприятие")
    public ResponseEntity<Event> getOneEvent(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Event> getOneEvent(@PathVariable Long id) с параметром {}", id);

        ResponseEntity<Event> responseEntity = ResponseEntity.ok(eventService.getOneEvent(id));
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param event Создаваемый объект класса Event
     * @return Результат работы метода eventService.saveEvent(event) в виде объекта Event
     * в теле ResponseEntity
     */
    @PostMapping
    @Operation(summary = "Создать мероприятие")
    public ResponseEntity<Event> createEvent(@RequestParam String event, @RequestBody @NotNull Event eventCreate) {
        log.debug("Старт метода ResponseEntity<Event> createEvent(@RequestBody @NotNull Event event) с параметром {}", eventCreate);

        ResponseEntity<Event> responseEntity = new ResponseEntity<>(eventService.saveEvent(eventCreate), HttpStatus.CREATED);
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param event Обновляемый объект класса Event
     * @return Результат работы метода eventService.saveEvent(event)) в виде объекта Event
     * в теле ResponseEntity
     */
    @PatchMapping
    @Operation(summary = "Изменить мероприятие")
    public ResponseEntity<Event> updateEvent(@RequestBody @NotNull Event event) {
        log.debug("Старт метода ResponseEntity<Event> updateEvent(@RequestBody @NotNull Event event) с параметром {}", event);

        ResponseEntity<Event> responseEntity = ResponseEntity.ok(eventService.saveEvent(event));
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param id объект класса Event
     * @return Объект ResponseEntity со статусом OK
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить мероприятие")
    public ResponseEntity<Event> deleteAdmin(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Event> deleteAdmin(@PathVariable Long id) с параметром {}", id);

        eventService.deleteEvent(id);

        ResponseEntity<Event> responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }
}
