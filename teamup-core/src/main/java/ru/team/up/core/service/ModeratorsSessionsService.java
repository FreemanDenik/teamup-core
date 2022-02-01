package ru.team.up.core.service;

import ru.team.up.core.entity.ModeratorsSessions;

import java.time.LocalDateTime;

/**
 * Интерфейс для создания сессии модеоатора
 * Author Ibraimov Mirseit
 */
public interface ModeratorsSessionsService {
    ModeratorsSessions getModeratorsSession(Long id);
    ModeratorsSessions getModeratorsSessionByModerator(Long id);
    ModeratorsSessions createModeratorsSession(Long id, LocalDateTime localDateTime);
    void removeModeratorSession(Long id);
}
