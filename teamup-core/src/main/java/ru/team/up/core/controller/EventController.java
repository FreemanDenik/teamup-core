package ru.team.up.core.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Event;
import ru.team.up.core.service.EventService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Alexey Tkachenko
 */

@RestController
@RequestMapping("private/event")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {
    private EventService eventService;

    /**
     * @return Результат работы метода eventService.getAllEvents()) в виде коллекции мероприятий
     * в теле ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    /**
     * @param id Значение ID мероприятия
     * @return Результат работы метода eventService.getOneEvent(id) в виде объекта Event
     * в теле ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Event> getOneEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getOneEvent(id));
    }

    /**
     * @param event Создаваемый объект класса Event
     * @return Результат работы метода eventService.saveEvent(event) в виде объекта Event
     * в теле ResponseEntity
     */
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody @NotNull Event event) {
        return new ResponseEntity<>(eventService.saveEvent(event), HttpStatus.CREATED);
    }

    /**
     * @param event Обновляемый объект класса Event
     * @return Результат работы метода eventService.saveEvent(event)) в виде объекта Event
     * в теле ResponseEntity
     */
    @PatchMapping
    public ResponseEntity<Event> updateEvent(@RequestBody @NotNull Event event) {
        return ResponseEntity.ok(eventService.saveEvent(event));
    }

    /**
     * @param event Удаляемый объект класса Event
     * @return Объект ResponseEntity со статусом OK
     */
    @DeleteMapping
    public ResponseEntity<Event> deleteAdmin(@RequestBody @NotNull Event event) {
        eventService.deleteEvent(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
