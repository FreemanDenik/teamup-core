package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.entity.Event;

import java.util.List;

public interface AssignedEventsRepository extends JpaRepository<AssignedEvents, Long> {

    /**
     * Метод меняет статус (STATUS_ID) мероприятия
     */
    @Query(value = "UPDATE Event e SET e.status = :statusId WHERE e.id = :eventId",
            nativeQuery = true)
    public void updateEventIdBeforeDeleting(@Param("statusId") Long statusId,
                                            @Param("eventId") Long eventId);

    /**
     * Метод получает ID новых мероприятий со статусом 2 (на проверке) нераспределенных на модераторов
     */
    @Query(value = "SELECT e.id FROM Event e LEFT JOIN AssignedEvents ae ON e.id=ae.eventId " +
            "WHERE ae.eventId IS NULL AND e.status = 2")
    public List<Long> getIdNotAssignedEvents();

    /**
     * Метод получает список ID мероприятий назначенных на модератора
     * @param id модератора
     * @return лист проверяющихся мероприятий конкретного модератора
     */
    @Query(value = "SELECT ae.id FROM AssignedEvents ae WHERE ae.moderatorId = :moderatorId")
    public List<Long> getIdAssignedEventsByModeratorId(@Param("moderatorId") Long id);

    /**
     * Метод получает все мероприятия, которые находятся на проверке по ID модератора
     * @param id модератора
     * @return лист проверяющихся мероприятий конкретного модератора
     */
    @Query("FROM Event e, AssignedEvents ae WHERE ae.moderatorId = :moderatorId AND ae.eventId = e.id")
    public List<Event> getAllEventsByModeratorId(@Param("moderatorId") Long id);
}
