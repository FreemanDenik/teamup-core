package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team.up.core.entity.ModeratorsSessions;

public interface ModeratorsSessionsRepository extends JpaRepository<ModeratorsSessions, Long> {
}
