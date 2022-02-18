package ru.team.up.core.service;

import ru.team.up.core.entity.ModeratorSession;

import java.time.LocalDateTime;

/**
 * Интерфейс для создания сессии модеоатора
 * Author Ibraimov Mirseit
 */
public interface ModeratorsSessionsService {
    ModeratorSession getModeratorsSession(Long id);
    ModeratorSession getModeratorsSessionByModerator(Long id);
    ModeratorSession createModeratorsSession(Long id);
    void removeModeratorSession(Long id);
}
