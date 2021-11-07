package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team.up.core.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
