package ru.team.up.core.controller;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Event;
import ru.team.up.core.repositories.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexey Tkachenko
 */
@RestController
@RequestMapping("private/event")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {
    private EventRepository eventRepository;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        try {
            List<Event> events = new ArrayList<>(eventRepository.findAll());

            if (events.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getOneEvent(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        try {
            Event newEvent = eventRepository.save(event);
            return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Event> updateEvent(@RequestBody @NotNull Event event) {
        Optional<Event> eventOptional = eventRepository.findById(event.getId());
        if (eventOptional.isPresent()) {
            Event updateEvent = eventRepository.save(event);
            return new ResponseEntity<>(updateEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<Event> deleteAdmin(@RequestBody Event event) {
        try {
            eventRepository.delete(event);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
