package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.EventType;
import ru.team.up.core.entity.User;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByAuthorId(User author);

    List<Event> findAllByEventType(EventType eventType);

    List<Event> findByEventNameContaining(String eventName);

    @Query(value = "UPDATE Event SET eventNumberOfParticipant = eventNumberOfParticipant + 1 WHERE id = :id")
    void updateNumberOfViews(Long id);

    List<Event> findAllByCity(String city);
}
