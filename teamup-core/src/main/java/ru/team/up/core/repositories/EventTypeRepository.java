package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team.up.core.entity.EventType;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {
}
