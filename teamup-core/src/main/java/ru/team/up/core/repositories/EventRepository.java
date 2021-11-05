package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.team.up.core.entity.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByAuthorId(Event author);

    List<Event> findAllByEventType(Event eventType);

    List<Event> findByNameContaining(String eventName);
}
