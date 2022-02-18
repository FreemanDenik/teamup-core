package ru.team.up.moderator.service;

import ru.team.up.core.entity.ModeratorsSessions;
import ru.team.up.core.repositories.ModeratorRepository;
import ru.team.up.core.repositories.ModeratorsSessionsRepository;

import java.time.LocalDateTime;

public interface ModeratorSessionsService extends ModeratorsSessionsRepository {
    ModeratorsSessions getModeratorsSessionByModerator(Long id);
    ModeratorsSessions createModeratorsSession(Long id, LocalDateTime localDateTime);
    void removeModeratorSession(Long id);
}
