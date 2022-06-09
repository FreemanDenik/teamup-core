package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.team.up.core.entity.ModeratorSession;

import java.time.LocalDateTime;
import java.util.List;

public interface ModeratorSessionRepository extends JpaRepository<ModeratorSession, Long> {

    /**
     * Метод получает сессию модератора по ID модератора
     */
    public ModeratorSession findModeratorSessionByModeratorId(Long id);

    /**
     * Метод получает ID одного модератора с наименьшим количеством мероприятий, распределенных на него
     * Если есть несколько модераторов с одинаковым количестовм мероприятий, модератор выбирается ID случайно
     * SQL запрос работает с БД  PostgreSQL
     */
    @Query(value = "SELECT moderator_id FROM moderator_session " +
            "WHERE amount_of_moderators_events = (SELECT MIN(amount_of_moderators_events) " +
            "FROM moderator_session) ORDER BY RANDOM() LiMIT 1",
            nativeQuery = true)
    public Long getFreeModerator();

    /**
     * Метод получает ID одного модератора с наименьшим количеством мероприятий с ограничением на
     * максимальное клоичество мероприятий на 1 модераторе
     * Если есть несколько модераторов с одинаковым количестовм мероприятий, модератор выбирается ID случайно
     * SQL запрос работает с БД  PostgreSQL
     */
    @Query(value = "SELECT moderator_id FROM moderator_session " +
            "WHERE amount_of_moderators_events = (SELECT MIN(amount_of_moderators_events) " +
            "FROM moderator_session) AND amount_of_moderators_events <= :limitation ORDER BY RANDOM() LIMIT 1",
            nativeQuery = true)
    public Long getFreeModeratorWithLimitedEvents(@Param("limitation") int eventLimitation);

    /**
     * Метод получает список ID всех неактивных модераторов
     *
     * @param downtime - текущее время - указанное в расписании
     */
    @Query(value = "SELECT ms.id FROM ModeratorSession ms " +
            "WHERE ms.lastUpdateSessionTime <= :downtime")
    public List<Long> getInactiveModerators(@Param("downtime") LocalDateTime downtime);
}
