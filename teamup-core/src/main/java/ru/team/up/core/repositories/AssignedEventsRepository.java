package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.entity.Event;

import java.util.List;

public interface AssignedEventsRepository extends JpaRepository<AssignedEvents, Long> {
    /**
     * Метод получаем лист мероприятий, у которых status_id = 2 (статус на проверке)
     *
     * @return лист мероприятий, нуждающихся в проверке
     */
    @Query(value = "SELECT * FROM event AS E WHERE E.status_id = 2",
            nativeQuery = true)
    public List<Event> getEventsForChecking();

    /**
     * Метод получает лист айдишников мероприятий, которые нуждаются в проверке
     *
     * @param eventList лист мероприятий, нуждающихся в проверке
     * @return лист айдишников мероприятий, нуждающихся в проверке
     */
    @Query(value = "SELECT eventId FROM event WHERE eventId = :eventList",
            nativeQuery = true)
    public List<Integer> getEventsIds(@Param("eventList") List<Event> eventList);

    /**
     * Метод получает все мероприятия, которые находятся на проверке по ID модератора
     *
     * @param id модератора
     * @return лист проверяющихся мероприятий конкретного модератора
     */
    @Query(value = "SELECT * FROM assigned_events AS AE WHERE AE.moderator_id = :moderatorId",
            nativeQuery = true)
    public List<AssignedEvents> getAllEventsByModeratorId(@Param("moderatorId") Long id);

    /**
     * Метод меняет статус (STATUS_ID) мероприятия на 1 (доступно) перед тем, как открепить это мероприятие
     * от сессии модератора и перед тем, как удалить эту сессию модератора
     */
    @Query(value = "UPDATE Event e SET e.status = :statusId WHERE e.id = :eventId",
            nativeQuery = true)
    public void updateEventIdBeforeDeleting(@Param("statusId") Long statusId,
                                            @Param("eventId") Long eventId);

    /**
     * Метод получает ID новых мероприятий нераспределенных на модераторов и со статусом 'на проверке'
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
}
