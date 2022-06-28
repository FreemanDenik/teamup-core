package ru.team.up.core.service;

import org.springframework.data.repository.query.Param;
import ru.team.up.core.entity.ModeratorSession;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс для создания сессии модеоатора
 * Author Ibraimov Mirseit
 */
public interface ModeratorsSessionsService {
    ModeratorSession getModeratorsSession(Long id);
    ModeratorSession getModeratorsSessionByModerator(Long id);
    ModeratorSession createModeratorsSession(Long id);
    void removeModeratorSession(Long id);
    Long getFreeModerator();
    Long getFreeModeratorWithLimitedEvents(int eventLimitation);
    List<Long> getInactiveModerators(LocalDateTime downtime);
    ModeratorSession findModeratorSessionByModeratorId(Long id);
    void incrementModeratorEventCounter(Long id);
}
