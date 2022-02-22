package ru.team.up.core.repositories;

import org.dom4j.rule.Mode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.team.up.core.entity.ModeratorSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ModeratorSessionRepository extends JpaRepository<ModeratorSession, Long> {

    Optional<ModeratorSession> findModeratorSessionByModeratorId(Long id);

    /**
     * Метод получает лист сессий модератора, которые нужно удалить из-за неактивности модератора
     * @param date - -30 минут от текущего времени
     * @param date1 - +30 минут от текущего времени
     * @return лист сессий модераторов, которые нужно удалить
     */
    @Query("select m from ModeratorSession m where m.lastUpdateSessionTime between :date and :date1")
    List<ModeratorSession> getInvalidateSession(@Param("date") LocalDateTime date,
                                                @Param("date1") LocalDateTime date1);

}
