package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.entity.Event;

import java.util.List;

public interface AssignedEventsRepository extends JpaRepository<AssignedEvents, Long> {

    public List<Integer> getOtherEvents();
}
