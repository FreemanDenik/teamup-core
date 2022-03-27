package ru.team.up.moderator.service;

import org.springframework.data.repository.query.Param;
import ru.team.up.core.entity.ModeratorSession;
import ru.team.up.core.repositories.ModeratorSessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface ModeratorSessionsService {
    /**
     *
     * @param id
     * @return
     */
   ModeratorSession getModeratorsSessionByModeratorId(Long id);

    /**
     *
     * @param id
     * @return
     */
    ModeratorSession createModeratorsSession(Long id);

    /**
     *
     * @param id
     */
    void removeModeratorSession(Long id);

    Long getFreeModerator();

    List<Long> getInactiveModerators(@Param("downtime") LocalDateTime downtime);

}
