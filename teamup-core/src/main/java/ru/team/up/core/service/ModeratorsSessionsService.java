package ru.team.up.core.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team.up.core.entity.ModeratorsSessions;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ModeratorsSessionsService {
    ModeratorsSessions getModeratorsSession(Long id);
    ModeratorsSessions getModeratorsSessionByModerator(Long id);
    ModeratorsSessions saveModeratorsSession(Long id, LocalDateTime localDateTime, LocalDate localDate);
    void removeModeratorSession(Long id);
}
