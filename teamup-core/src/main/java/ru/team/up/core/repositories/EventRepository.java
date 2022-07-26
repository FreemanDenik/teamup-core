package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.EventType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("FROM Event e where e.authorId.id = :authorId")
    List<Event> findAllByAuthorId(@Param("authorId") Long authorId);

    List<Event> findAllByEventType(EventType eventType);

    List<Event> findByEventNameContaining(String eventName);

    @Modifying
    @Query("UPDATE Event SET eventNumberOfParticipant = eventNumberOfParticipant + 1 WHERE id = :id")
    void updateNumberOfViews(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Event SET countViewEvent = countViewEvent + 1 WHERE id = :id")
    void incrementCountViewEvent(@Param("id") Long id);

    List<Event> findAllByCity(String city);

    @Query(value = "SELECT * FROM Event e WHERE e.city_event = :city ORDER BY e.city_event LIMIT :limit", nativeQuery = true)
    List<Event> findAllByCityByLimit(@Param("city") String city, @Param("limit") int limit);

    @Query("FROM Event e join e.participantsEvent p where p.id = :subscriberId")
    List<Event> getAllEventsBySubscriberId(@Param("subscriberId") Long subscriberId);

    @Query(value = "SELECT uae.user_id " +
            "FROM event e INNER JOIN user_account_event uae ON e.id = uae.event_id " +
            "WHERE e.id = ?1", nativeQuery = true)
    List<Long> getEventUserIds(Long eventId);

    List<Event> findByTimeEventBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
